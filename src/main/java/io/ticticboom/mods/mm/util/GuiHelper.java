package io.ticticboom.mods.mm.util;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.screens.Screen;

public class GuiHelper {

    public static void renderVerticallyFilledBar(PoseStack ms, Screen screen, int x, int y, int u, int v, int width, int height, float pct) {
        float invPct = 1 - pct;
        screen.blit(ms, x, y + (int) (height * invPct), u, v + (int) (height * invPct), width, (int) (height * pct));
    }

}
