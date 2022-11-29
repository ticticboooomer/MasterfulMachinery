package io.ticticboom.mods.mm.client.port;

import com.mojang.blaze3d.vertex.PoseStack;
import io.ticticboom.mods.mm.Ref;
import io.ticticboom.mods.mm.client.port.ClientPortTypeEntry;
import io.ticticboom.mods.mm.client.screen.PortScreen;
import io.ticticboom.mods.mm.ports.base.PortStorage;
import io.ticticboom.mods.mm.ports.fluid.FluidPortStorage;
import io.ticticboom.mods.mm.util.FluidRenderer;
import io.ticticboom.mods.mm.util.RenderHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;

public class FluidClientPortTypeEntry extends ClientPortTypeEntry {
    @Override
    public void renderScreen(PortStorage storage, PortScreen screen, PoseStack ms, int x, int y) {
        var storg = (FluidPortStorage) storage;
        RenderHelper.useTexture(Ref.SLOT_PARTS);
        var startX = 175 / 2 - (18 / 2);
        var startY = 252 / 4 - (18 / 2);
        screen.blit(ms, screen.getGuiLeft() + startX, screen.getGuiTop() + startY, 0, 26, 18, 18);
        if (!storg.handler.stack().isEmpty()) {
            FluidRenderer.INSTANCE.render(ms, screen.getGuiLeft() + startX + 1, screen.getGuiTop() + startY + 1, storg.handler.stack(), 16);
            Gui.drawCenteredString(ms, Minecraft.getInstance().font, storg.handler.stack().getAmount() + " " + storg.handler.stack().getDisplayName().getString(), screen.getGuiLeft() + startX + 9, screen.getGuiTop() + startY + 30, 0xfefefe);
        }
    }
}
