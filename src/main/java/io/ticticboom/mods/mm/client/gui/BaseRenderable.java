package io.ticticboom.mods.mm.client.gui;

import io.ticticboom.mods.mm.util.WidgetUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.narration.NarrationElementOutput;

public abstract class BaseRenderable implements Renderable {

    protected Font font() {
        return Minecraft.getInstance().font;
    }

    protected boolean isHovered(int mX, int mY, int x, int y, int width, int height) {
        return WidgetUtils.isPointerWithinSized(mX, mY, x, y, width, height);
    }
}
