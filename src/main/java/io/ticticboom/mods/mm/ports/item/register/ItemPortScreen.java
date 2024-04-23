package io.ticticboom.mods.mm.ports.item.register;

import io.ticticboom.mods.mm.Ref;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class ItemPortScreen extends AbstractContainerScreen<ItemPortMenu> {

    private final ItemPortMenu menu;

    public ItemPortScreen(ItemPortMenu menu, Inventory inv, Component title) {
        super(menu, inv, title);
        this.menu = menu;
        this.imageHeight = 222;
        this.imageWidth = 174;
    }

    @Override
    protected void renderBg(GuiGraphics gfx, float v, int i, int i1) {
        gfx.blit(Ref.Textures.PORT_GUI, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight);
    }


    @Override
    public void render(GuiGraphics gfx, int partialTicks, int mouseX, float mouseY) {
        renderBackground(gfx);
        super.render(gfx, partialTicks, mouseX, mouseY);
    }
}
