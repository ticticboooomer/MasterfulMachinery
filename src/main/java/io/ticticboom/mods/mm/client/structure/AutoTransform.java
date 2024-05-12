package io.ticticboom.mods.mm.client.structure;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;
import io.ticticboom.mods.mm.structure.StructureModel;
import io.ticticboom.mods.mm.structure.layout.PositionedLayoutPiece;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
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

    private Vec3i minBound = new Vec3i(Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE);
    private Vec3i maxBound = new Vec3i(Integer.MIN_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE);
    private Vector3f pan;
    private Vector3f offset;

    public AutoTransform(StructureModel model) {

        for (PositionedLayoutPiece piece : model.layout().getPositionedPieces()) {
            BlockPos pos = piece.pos();
            minBound = new Vec3i(Math.min(pos.getX(), minBound.getX()), Math.min(pos.getY(), minBound.getY()), Math.min(pos.getZ(), minBound.getZ()));
            maxBound = new Vec3i(Math.max(pos.getX(), maxBound.getX()), Math.max(pos.getY(), maxBound.getY()), Math.max(pos.getZ(), maxBound.getZ()));
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

        float centerX = ((float) maxBound.getX() - minBound.getX()) / 2f;
        float centerY = ((float) maxBound.getY() - minBound.getY()) / 2f;
        float centerZ = ((float) maxBound.getZ() - minBound.getZ()) / 2f;

        offset = new Vector3f(minBound.getX(), minBound.getY(), minBound.getZ());
        offset.add(centerX, centerY, centerZ);
        offset.add(-0.5f, -0.5f, -0.5f);
        lastX = mouseX;
        lastY = mouseY;
    }

    public PoseStack transform(PoseStack m, BlockPos pos) {
        m.scale(12, -12, 12);
        m.translate(pan.x(), pan.y(), pan.z());
        m.mulPose(new Quaternion(new Vector3f(1, 0, 0), (float) yRotation, true));
        m.mulPose(new Quaternion(new Vector3f(0, -1, 0), -(float) xRotation, true));
        m.scale((float) scaleFactor, (float) scaleFactor, (float) scaleFactor);
        m.translate(pos.getX() + offset.x(), pos.getY() + offset.y(), pos.getZ() + offset.z());
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
