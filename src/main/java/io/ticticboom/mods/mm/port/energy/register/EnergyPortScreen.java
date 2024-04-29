package io.ticticboom.mods.mm.port.energy.register;

import io.ticticboom.mods.mm.Ref;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.world.entity.player.Inventory;

public class EnergyPortScreen extends AbstractContainerScreen<EnergyPortMenu> {

    private final FormattedText header;

    public EnergyPortScreen(EnergyPortMenu menu, Inventory inv, Component displayName) {
        super(menu, inv, displayName);
        this.imageHeight = 222;
        this.imageWidth = 174;
        String name = menu.getModel().name();
        int subStrLength = Math.min(55, name.length());
        header = FormattedText.of(name.substring(0, subStrLength) + (subStrLength < 55 ? "" : "..."));
    }

    @Override
    protected void renderBg(GuiGraphics gfx, float partialTicks, int mouseX, int mouseY) {
        gfx.blit(Ref.Textures.PORT_GUI, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight);
    }

    @Override
    public void render(GuiGraphics gfx, int mouseX, int mouseY, float partialTick) {
        renderBackground(gfx);
        super.render(gfx, mouseX, mouseY, partialTick);
        renderTooltip(gfx, mouseX, mouseY);
        gfx.blit(Ref.Textures.SLOT_PARTS, 8, 45, 89, 78, 162, 80);
        gfx.blit(Ref.Textures.SLOT_PARTS, 9, 41, 90, 0, 160, 78);
    }
}
