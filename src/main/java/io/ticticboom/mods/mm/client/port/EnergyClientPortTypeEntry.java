package io.ticticboom.mods.mm.client.port;

import com.mojang.blaze3d.vertex.PoseStack;
import io.ticticboom.mods.mm.Ref;
import io.ticticboom.mods.mm.client.port.ClientPortTypeEntry;
import io.ticticboom.mods.mm.client.screen.PortScreen;
import io.ticticboom.mods.mm.ports.base.PortStorage;
import io.ticticboom.mods.mm.ports.energy.EnergyPortStorage;
import io.ticticboom.mods.mm.util.GuiHelper;
import io.ticticboom.mods.mm.util.RenderHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;

public class EnergyClientPortTypeEntry extends ClientPortTypeEntry {
    @Override
    public void renderScreen(PortStorage storage, PortScreen screen, PoseStack ms, int x, int y) {
        var storg = (EnergyPortStorage) storage;
        RenderHelper.useTexture(Ref.SLOT_PARTS);
        var startX = 175 / 2 - (18 / 2);
        var startY = 252 / 4 - (18 / 2) - 10;
        var height = 50;
        screen.blit(ms, screen.getGuiLeft() + startX, screen.getGuiTop() + startY -10, 50, 14, 18, height);
        var percentage = (float)storg.handler.getEnergyStored() / storg.handler.getMaxEnergyStored();
        GuiHelper.renderVerticallyFilledBar(ms, screen, screen.getGuiLeft() + startX, screen.getGuiTop() + startY -10, 72, 14, 18, 50, percentage);
        Gui.drawCenteredString(ms, Minecraft.getInstance().font, percentage * 100 + "%", screen.getGuiLeft() + startX + 9, screen.getGuiTop() + startY + 60, 0xfefefe);

    }
}
