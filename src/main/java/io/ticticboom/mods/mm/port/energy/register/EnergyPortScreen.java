package io.ticticboom.mods.mm.port.energy.register;

import io.ticticboom.mods.mm.Ref;
import io.ticticboom.mods.mm.port.IPortStorage;
import io.ticticboom.mods.mm.port.energy.EnergyPortStorage;
import io.ticticboom.mods.mm.port.energy.EnergyPortStorageModel;
import io.ticticboom.mods.mm.util.WidgetUtils;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.world.entity.player.Inventory;

import java.util.ArrayList;

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
    protected void renderLabels(GuiGraphics gfx, int p_282681_, int p_283686_) {
        gfx.drawWordWrap(this.font, header, 8, 8, 150, 0x404040);
    }

    @Override
    public void render(GuiGraphics gfx, int mouseX, int mouseY, float partialTick) {
        renderBackground(gfx);
        super.render(gfx, mouseX, mouseY, partialTick);
        renderTooltip(gfx, mouseX, mouseY);
        gfx.blit(Ref.Textures.SLOT_PARTS, this.leftPos + 7, 60, 89, 78, 162, 80);
        EnergyPortBlockEntity be = menu.getBlockEntity();
        EnergyPortStorage storage = (EnergyPortStorage) be.getStorage();
        EnergyPortStorageModel storageModel = be.getStorageModel();
        var filledValue = (double)storage.getStoredEnergy() / (double)storageModel.capacity();
        var filledHeight = (int)(filledValue * 78);
        var start = 139 - filledHeight;
        gfx.blit(Ref.Textures.SLOT_PARTS, this.leftPos + 8, start, 90, 0, 160, filledHeight);
        if (WidgetUtils.isPointerWithinSized(mouseX, mouseY, this.leftPos + 7, 60, 162, 80)) {
            var tooltip = new ArrayList<Component>();
            tooltip.add(Component.literal(String.format("Stored Energy: %sFE", storage.getStoredEnergy())));
            gfx.renderComponentTooltip(this.font, tooltip, mouseX - this.leftPos, mouseY - this.topPos);
        }
    }
}
