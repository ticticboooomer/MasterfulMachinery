package io.ticticboom.mods.mm.foundation.scanner;

import io.ticticboom.mods.mm.Ref;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class StructureScannerScreen extends AbstractContainerScreen<StructureScannerMenu> {

    public StructureScannerScreen(StructureScannerMenu menu, Inventory inv, Component displayName) {
        super(menu, inv, displayName);
        this.imageHeight = 222;
        this.imageWidth = 174;
    }

    @Override
    protected void renderBg(GuiGraphics gfx, float partialTick, int mouseX, int mouseY) {
        gfx.blit(Ref.Textures.SCANNER_GUI, this.leftPos,this.topPos, 0, 0, 175, 223);
    }

    @Override
    protected void renderLabels(GuiGraphics gfx, int mouseX, int mouseY) {
        gfx.drawString(this.font, "Structure Scanner", 8, 8, 0x404040, false);
    }

    @Override
    public void render(GuiGraphics gfx, int mouseX, int mouseY, float partialTicks) {
        renderBackground(gfx);
        super.render(gfx, mouseX, mouseY, partialTicks);
        renderTooltip(gfx, mouseX, mouseY);
    }
}
