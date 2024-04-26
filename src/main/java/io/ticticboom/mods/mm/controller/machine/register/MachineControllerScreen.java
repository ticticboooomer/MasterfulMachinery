package io.ticticboom.mods.mm.controller.machine.register;

import io.ticticboom.mods.mm.Ref;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.world.entity.player.Inventory;

public class MachineControllerScreen extends AbstractContainerScreen<MachineControllerMenu> {

    private final MachineControllerMenu menu;
    private final MachineControllerBlockEntity be;
    private final FormattedText header;

    public MachineControllerScreen(MachineControllerMenu menu, Inventory inv, Component p_96550_) {
        super(menu, inv, p_96550_);
        this.menu = menu;
        this.be = (MachineControllerBlockEntity) menu.getBe();
        this.imageHeight = 222;
        this.imageWidth = 174;
        String name = menu.getModel().name();
        int subStrLength = Math.min(55, name.length());
        header = FormattedText.of(name.substring(0, subStrLength) + (subStrLength < 55 ? "" : "..."));
    }

    @Override
    protected void renderBg(GuiGraphics gfx, float partialTick, int mouseX, int mouseY) {
        gfx.blit(Ref.Textures.GUI_LARGE, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight);
    }

    @Override
    protected void renderLabels(GuiGraphics gfx, int mouseX, int mouseY) {
        // controller name
        gfx.drawWordWrap(this.font, header, 10, 10, 150, 0xacacac);

        // structure formation details
        var isFormed = be.getStructure() != null;
        gfx.drawWordWrap(this.font, FormattedText.of(isFormed ? "Formed As:" : "Not Formed"), 10, 40, 150, 0xacacac);
        if (isFormed) {
            gfx.drawWordWrap(this.font, FormattedText.of(be.getStructure().name()), 10, 53, 150, 0xacacac);
        }
        // recipe processing details
        var isProcessing = be.getRecipeState() != null;
        if (isProcessing) {
            gfx.drawWordWrap(this.font, FormattedText.of("Progress: " + String.format("%,2f", be.getRecipeState().getTickPercentage()) + "%"), 10, 110, 150, 0xacacac);
        }
    }

    @Override
    public void render(GuiGraphics gfx, int partialTicks, int mouseX, float mouseY) {
        renderBackground(gfx);
        super.render(gfx, partialTicks, mouseX, mouseY);
    }
}
