package io.ticticboom.mods.mm.util;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;

public class WidgetUtils {
    public static boolean isPointerWithin(int mX, int mY, int minX, int minY, int maxX, int maxY) {
        return mX > minX && mX < maxX && mY > minY && mY < maxY;
    }

    public static boolean isPointerWithinSized(int mX, int mY, int x, int y, int w, int h) {
        return isPointerWithin(mX, mY, x, y, x + w, y + h);
    }

    public static void drawWordWrap(PoseStack pose, Font font, FormattedText text, int x, int y, int width, int color) {
        int line = 0;
        for (FormattedCharSequence txt : font.split(text, width)) {
            font.draw(pose, txt, x, y + (9 * line), color);
            line++;
        }
    }
}
