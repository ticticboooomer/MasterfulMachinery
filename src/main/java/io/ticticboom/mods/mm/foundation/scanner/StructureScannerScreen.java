package io.ticticboom.mods.mm.foundation.scanner;

import io.ticticboom.mods.mm.Ref;
import io.ticticboom.mods.mm.util.MenuUtils;
import io.ticticboom.mods.mm.util.WidgetUtils;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.world.entity.player.Inventory;

public class StructureScannerScreen extends AbstractContainerScreen<StructureScannerMenu> {

    public StructureScannerScreen(StructureScannerMenu menu, Inventory inv, Component displayName) {
        super(menu, inv, displayName);
        this.imageHeight = 222;
        this.imageWidth = 174;
    }

    @Override
    protected void renderBg(GuiGraphics gfx, float partialTick, int mouseX, int mouseY) {
        gfx.blit(Ref.Textures.SCANNER_GUI, this.leftPos, this.topPos, 0, 0, 175, 223);
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
        StructureScannerStatus status = menu.getBe().getStatus();
        gfx.drawString(this.font, status.getMessage(), this.leftPos + 8, this.topPos + 30, 0x404040, false);
        if (status == StructureScannerStatus.INVALID_SELECTION) {
            gfx.drawWordWrap(this.font, FormattedText.of("Select two opposite corners of your structure."), this.leftPos + 8, this.topPos + 50, 160, 0x404040);
        }

        if (status == StructureScannerStatus.READY) {
            if (WidgetUtils.isPointerWithinSized(mouseX, mouseY, this.leftPos + 48, this.topPos + 82, 80, 18)) {
                gfx.blit(Ref.Textures.SCANNER_GUI, this.leftPos + 47, this.topPos + 81, 0, 223, 82, 20);
            } else {
                gfx.blit(Ref.Textures.SCANNER_GUI, this.leftPos + 48, this.topPos + 82, 1, 224, 80, 18);
            }

            String generateText = "Generate";
            gfx.drawString(this.font, generateText, this.leftPos + 88 - (font.width(generateText) / 2), this.topPos + 87, 0xFFFFFF, false);
        }
    }
}