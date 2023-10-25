package io.ticticboom.mods.mm.client.port;

import com.mojang.blaze3d.vertex.PoseStack;
import io.ticticboom.mods.mm.Ref;
import io.ticticboom.mods.mm.client.screen.PortScreen;
import io.ticticboom.mods.mm.ports.base.PortStorage;
import io.ticticboom.mods.mm.ports.mekanism.heat.MekHeatPortStorage;
import io.ticticboom.mods.mm.util.RenderHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;

import java.text.NumberFormat;

public class MekHeatClientPortTypeEntry extends ClientPortTypeEntry {
    @Override
    public void renderScreen(PortStorage storage, PortScreen screen, PoseStack ms, int x, int y) {
        var storg = (MekHeatPortStorage) storage;
        RenderHelper.useTexture(Ref.SLOT_PARTS);
        var startX = 175 / 2 - (18 / 2);
        var startY = 252 / 4 - (18 / 2) - 10;
        var height = 50;
        var temp = storg.handler.getTemperature(0);
        GuiComponent.drawCenteredString(ms, Minecraft.getInstance().font, "Temp: " + NumberFormat.getInstance().format(temp) + "K", screen.getGuiLeft() + startX + 9, screen.getGuiTop() + startY + 30, 0xfefefe);
    }
}
