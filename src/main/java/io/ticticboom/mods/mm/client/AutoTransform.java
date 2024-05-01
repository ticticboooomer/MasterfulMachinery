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

    private int lastX = 0;
    private int lastY = 0;

    private double xRotation;
    private double yRotation;

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

        reset();
    }

    public void run(int mouseX, int mouseY) {
        if (lastX == 0 && lastY == 0) {
            lastX = mouseX;
            lastY = mouseY;
        }
        if (GLFW.glfwGetMouseButton(mc.getWindow().getWindow(), GLFW.GLFW_MOUSE_BUTTON_RIGHT) == GLFW.GLFW_PRESS) {
            scaleFactor += ((double) mouseY - lastY) * 0.05f;
            scaleFactor = Math.max(0.003f, scaleFactor);
        }

        if (GLFW.glfwGetMouseButton(mc.getWindow().getWindow(), GLFW.GLFW_MOUSE_BUTTON_MIDDLE) == GLFW.GLFW_PRESS) {
            var mx = (double) mouseX - lastX;
            var my = (double) mouseY - lastY;

            xRotation += mx;
            yRotation += my;
        }

        if (GLFW.glfwGetMouseButton(mc.getWindow().getWindow(), GLFW.GLFW_MOUSE_BUTTON_LEFT) == GLFW.GLFW_PRESS && GLFW.glfwGetKey(mc.getWindow().getWindow(), GLFW.GLFW_KEY_LEFT_SHIFT) == GLFW.GLFW_PRESS) {
            double relMoveX = mouseX - lastX;
            double relMoveY = mouseY - lastY;
            pan.add((float) relMoveX * 0.08f, (float) -relMoveY * 0.08f, 0);
        }

        float centerX = ((float) maxBound.x - minBound.x) / 2f;
        float centerY = ((float) maxBound.y - minBound.y) / 2f;
        float centerZ = ((float) maxBound.z - minBound.z) / 2f;

        offset = new Vector3f(minBound.x, minBound.y, minBound.z);
        offset.add(centerX, centerY, centerZ);
        offset.add(-0.5f, -0.5f, -0.5f);
        lastX = mouseX;
        lastY = mouseY;
    }

    public Matrix4f transform(Matrix4f m, BlockPos pos) {
        m.scale(12, -12, 12);
        m.translate(pan);
        m.rotate(new Quaternionf(new AxisAngle4f((float) yRotation * ((float) Math.PI / 180f), 1, 0, 0)));
        m.rotate(new Quaternionf(new AxisAngle4f(-(float) xRotation * ((float) Math.PI / 180f), 0, -1, 0)));
        m.scale((float) scaleFactor, (float) scaleFactor, (float) scaleFactor);
        m.translate(pos.getX() + offset.x, pos.getY() + offset.y, pos.getZ() + offset.z);
        return m;
    }

    public void reset() {
        xRotation = -35;
        yRotation = 15;
        lastX = 0;
        lastY = 0;
        scaleFactor = 1.5;
        float tx = 6.75f, ty = -6, tz = 10;
        pan = new Vector3f(tx, ty, tz);
    }
}
