package io.ticticboom.mods.mm.client;

import io.ticticboom.mods.mm.Ref;
import io.ticticboom.mods.mm.structure.StructureModel;
import io.ticticboom.mods.mm.structure.layout.PositionedLayoutPiece;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.protocol.game.ClientboundMoveEntityPacket;
import org.joml.*;
import org.lwjgl.glfw.GLFW;

import java.lang.Math;

@Getter
@Setter
public class AutoTransform {

    private static Minecraft mc = Minecraft.getInstance();

    private double lastX = 0;
    private double lastY = 0;

    private double xRotation = 0;
    private double yRotation = 0;

    private double scrollLastPos = 0;
    private double scaleFactor = 1;

    private Vector3i minBound = new Vector3i(Integer.MAX_VALUE);
    private Vector3i maxBound = new Vector3i(Integer.MIN_VALUE);
    private Vector3f pan;
    private Vector3f offset;

    public AutoTransform(StructureModel model) {
        for (PositionedLayoutPiece piece : model.layout().getPositionedPieces()) {
            BlockPos pos = piece.pos();
            // min
            minBound.x = Math.min(pos.getX(), minBound.x);
            minBound.y = Math.min(pos.getY(), minBound.y);
            minBound.z = Math.min(pos.getZ(), minBound.z);
            // max
            maxBound.x = Math.max(pos.getX(), maxBound.x);
            maxBound.y = Math.max(pos.getY(), maxBound.y);
            maxBound.z = Math.max(pos.getZ(), maxBound.z);
        }
        float tx = 6.75f, ty = -5, tz = 10;
        pan = new Vector3f(tx, ty, tz);
    }

    public void run(int mouseX, int mouseY) {
        if (GLFW.glfwGetMouseButton(mc.getWindow().getWindow(), GLFW.GLFW_MOUSE_BUTTON_RIGHT) == GLFW.GLFW_PRESS) {
            scaleFactor += ((double)mouseY - lastY) * 0.05f;
            scaleFactor = Math.max(0.003f, scaleFactor);
        }

        if (GLFW.glfwGetMouseButton(mc.getWindow().getWindow(), GLFW.GLFW_MOUSE_BUTTON_MIDDLE) == GLFW.GLFW_PRESS) {
            var mx = (double)mouseX - lastX;
            var my = (double)mouseY - lastY;

            xRotation += mx / 0.00005;
            yRotation += my / 0.00005;
        }

        float centerX = ((float) maxBound.x - minBound.x) / 2f;
        float centerY = ((float) maxBound.y - minBound.y) / 2f;
        float centerZ = ((float) maxBound.z - minBound.z) / 2f;

        offset = new Vector3f(minBound.x, minBound.y, minBound.z);
        offset.add(centerX, -centerY, centerZ);
        offset.add(-0.5f, -0.5f, -0.5f);
        lastX = mouseX;
        lastY = mouseY;
    }

    public Matrix4f transform(Matrix4f m, BlockPos pos) {
        m.scale(12, -12, 12);
        m.translate(pan);
        m.rotate(new Quaternionf(new AxisAngle4d(yRotation * (Math.PI / 180d), 1, 0, 0)));
        m.rotate(new Quaternionf(new AxisAngle4d(-xRotation * (Math.PI / 180d), 0, -1, 0)));
        m.scale((float) scaleFactor, (float) -scaleFactor, (float) scaleFactor);
        m.translate(pos.getX() + offset.x, pos.getY() + offset.y, pos.getZ() + offset.z);
        return m;
    }
}
