package io.ticticboom.mods.mm.port.common;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import io.ticticboom.mods.mm.Ref;
import io.ticticboom.mods.mm.port.IPortBlockEntity;
import io.ticticboom.mods.mm.port.IPortMenu;
import io.ticticboom.mods.mm.util.WidgetUtils;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.phys.Vec2;

import java.util.ArrayList;

public class SlottedContainerScreen<T extends AbstractContainerMenu & IPortMenu> extends AbstractContainerScreen<T> {

    protected final T menu;
    protected final FormattedText header;
    protected ArrayList<Vec2> slots = new ArrayList<>();

    public SlottedContainerScreen(T menu, Inventory inv, Component displayName) {
        super(menu, inv, displayName);
        this.menu = menu;
        this.imageHeight = 222;
        this.imageWidth = 174;
        String name = menu.getModel().name();
        int subStrLength = Math.min(55, name.length());
        header = FormattedText.of(name.substring(0, subStrLength) + (subStrLength < 55 ? "" : "..."));
        setupSlots();
    }

    private void setupSlots() {
        IPortBlockEntity blockEntity = menu.getBlockEntity();
        var storage = blockEntity.getStorage();
        var model = (ISlottedPortStorageModel) storage.getStorageModel();

        var columns = model.columns();
        var rows = model.rows();

        int offsetX = ((162 - (columns * 18)) / 2) + 7;
        int offsetY = ((108 - (rows * 18)) / 2) + 7;
        slots.ensureCapacity(columns * rows);

        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < columns; x++) {
                slots.add(new Vec2(x * 18 + offsetX, y * 18 + offsetY));
            }
        }
    }

    @Override
    protected void renderBg(PoseStack gfx, float partialTicks, int mouseX, int mouseY) {
        RenderSystem.setShaderTexture(0, Ref.Textures.PORT_GUI);
        this.blit(gfx, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight);
        RenderSystem.setShaderTexture(0, Ref.Textures.SLOT_PARTS);
        for (Vec2 slot : slots) {
            this.blit(gfx, this.leftPos + (int) slot.x, this.topPos + (int) slot.y, 0, 26, 18, 18);
        }
    }

    @Override
    protected void renderLabels(PoseStack gfx, int mouseX, int mouseY) {
        WidgetUtils.drawWordWrap(gfx, this.font, header, 8, 8, 150, 0x404040);
    }

    @Override
    public void render(PoseStack gfx, int mouseX, int mouseY, float partialTicks) {
        renderBackground(gfx);
        super.render(gfx, mouseX, mouseY, partialTicks);
        renderTooltip(gfx, mouseX, mouseY);
    }
}
