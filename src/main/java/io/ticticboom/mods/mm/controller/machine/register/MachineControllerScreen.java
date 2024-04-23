package io.ticticboom.mods.mm.controller.machine.register;

import io.ticticboom.mods.mm.Ref;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.world.entity.player.Inventory;

public class MachineControllerScreen extends AbstractContainerScreen<MachineControllerMenu> {

    private final MachineControllerMenu menu;

    public MachineControllerScreen(MachineControllerMenu menu, Inventory inv, Component p_96550_) {
        super(menu, inv, p_96550_);
        this.menu = menu;
        this.imageHeight = 222;
        this.imageWidth = 174;
    }

    @Override
    protected void renderBg(GuiGraphics gfx, float partialTick, int mouseX, int mouseY) {
        gfx.blit(Ref.Textures.GUI_LARGE, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight);
    }

    @Override
    protected void renderLabels(GuiGraphics gfx, int mouseX, int mouseY) {
        gfx.drawWordWrap(this.font, FormattedText.of(menu.getModel().name()), 10, 10, 200, 0xacacac);
    }

    @Override
    public void render(GuiGraphics gfx, int partialTicks, int mouseX, float mouseY) {
        renderBackground(gfx);
        super.render(gfx, partialTicks, mouseX, mouseY);
    }
}
