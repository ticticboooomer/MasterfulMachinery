package io.ticticboom.mods.mm.port.energy.register;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import io.ticticboom.mods.mm.Ref;
import io.ticticboom.mods.mm.port.energy.EnergyPortStorage;
import io.ticticboom.mods.mm.port.energy.EnergyPortStorageModel;
import io.ticticboom.mods.mm.util.WidgetUtils;
import net.minecraft.client.gui.components.Widget;
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
    protected void renderBg(PoseStack gfx, float partialTicks, int mouseX, int mouseY) {
        RenderSystem.setShaderTexture(0, Ref.Textures.PORT_GUI);
        this.blit(gfx, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight);
    }

    @Override
    protected void renderLabels(PoseStack gfx, int p_282681_, int p_283686_) {
        WidgetUtils.drawWordWrap(gfx, this.font, header, 8, 8, 150, 0x404040);
    }

    @Override
    public void render(PoseStack gfx, int mouseX, int mouseY, float partialTick) {
        renderBackground(gfx);
        super.render(gfx, mouseX, mouseY, partialTick);
        renderTooltip(gfx, mouseX, mouseY);
        RenderSystem.setShaderTexture(0, Ref.Textures.SLOT_PARTS);
        this.blit(gfx, this.leftPos + 7, this.topPos + 50, 89, 78, 162, 80);
        EnergyPortBlockEntity be = menu.getBlockEntity();
        EnergyPortStorage storage = (EnergyPortStorage) be.getStorage();
        EnergyPortStorageModel storageModel = be.getStorageModel();
        var filledValue = (double) storage.getStoredEnergy() / (double) storageModel.capacity();
        var filledHeight = (int) (filledValue * 78);
        var start = 129 - filledHeight;
        this.blit(gfx, this.leftPos + 8, this.topPos + start, 90, 0, 160, filledHeight);
        if (WidgetUtils.isPointerWithinSized(mouseX, mouseY, this.leftPos + 7, this.topPos + 50, 162, 80)) {
            var tooltip = new ArrayList<Component>();
            tooltip.add(Component.literal(String.format("Stored Energy: %sFE", storage.getStoredEnergy())));
            this.renderComponentTooltip(gfx, tooltip, mouseX, mouseY);
        }
    }
}