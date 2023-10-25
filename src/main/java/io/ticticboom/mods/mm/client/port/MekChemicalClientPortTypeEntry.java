package io.ticticboom.mods.mm.client.port;

import com.mojang.blaze3d.vertex.PoseStack;
import io.ticticboom.mods.mm.Ref;
import io.ticticboom.mods.mm.client.screen.PortScreen;
import io.ticticboom.mods.mm.ports.base.PortStorage;
import io.ticticboom.mods.mm.ports.mekanism.MekChemicalPortStorage;
import io.ticticboom.mods.mm.util.GuiHelper;
import io.ticticboom.mods.mm.util.RenderHelper;
import mekanism.api.chemical.Chemical;
import mekanism.api.chemical.ChemicalStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;

public class MekChemicalClientPortTypeEntry<CHEMICAL extends Chemical<CHEMICAL>, STACK extends ChemicalStack<CHEMICAL>> extends ClientPortTypeEntry {
    @Override
    public void renderScreen(PortStorage storage, PortScreen screen, PoseStack ms, int x, int y) {
        var storg = (MekChemicalPortStorage<CHEMICAL, STACK>) storage;
        RenderHelper.useTexture(Ref.SLOT_PARTS);
        var startX = 175 / 2 - (18 / 2);
        var startY = 252 / 4 - (18 / 2) - 10;
        var height = 50;
        screen.blit(ms, screen.getGuiLeft() + startX, screen.getGuiTop() + startY -10, 50, 14, 18, height);
        var percentage = (float)storg.tank.getStack().getAmount() / storg.config.capacity();
        GuiHelper.renderVerticallyFilledBar(ms, screen, screen.getGuiLeft() + startX, screen.getGuiTop() + startY -10, 72, 14, 18, 50, percentage);
        GuiComponent.drawCenteredString(ms, Minecraft.getInstance().font, percentage * 100 + "%", screen.getGuiLeft() + startX + 9, screen.getGuiTop() + startY + 60, 0xfefefe);
        GuiComponent.drawCenteredString(ms, Minecraft.getInstance().font, storg.tank.getStack().getAmount() + " mb", screen.getGuiLeft() + startX + 9, screen.getGuiTop() + 17, 0xfefefe);

    }
}
