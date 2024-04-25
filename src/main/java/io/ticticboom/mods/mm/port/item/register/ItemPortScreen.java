package io.ticticboom.mods.mm.port.item.register;

import io.ticticboom.mods.mm.Ref;
import io.ticticboom.mods.mm.port.item.ItemPortStorage;
import io.ticticboom.mods.mm.port.item.ItemPortStorageModel;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.phys.Vec2;

import java.util.ArrayList;

public class ItemPortScreen extends AbstractContainerScreen<ItemPortMenu> {

    private final ItemPortMenu menu;
    private final FormattedText header;
    private ArrayList<Vec2> slots = new ArrayList<>();

    public ItemPortScreen(ItemPortMenu menu, Inventory inv, Component title) {
        super(menu, inv, title);
        this.menu = menu;
        this.imageHeight = 222;
        this.imageWidth = 174;
        header = FormattedText.of(menu.getModel().name());
        setupSlots();
    }

    private void setupSlots() {
        ItemPortBlockEntity blockEntity = menu.getBlockEntity();
        var storage = (ItemPortStorage) blockEntity.getStorage();
        var model = (ItemPortStorageModel)storage.getStorageModel();

        var columns = model.columns();
        var rows = model.rows();

        int offsetX = ((162 - (columns * 18)) / 2) + 7;
        int offsetY = ((108 - (rows * 18)) / 2) + 7;
        slots.ensureCapacity(columns * rows);

        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < columns; x++) {
                slots.add(new Vec2 (x * 18 + offsetX, y * 18 + offsetY));
            }
        }
    }

    @Override
    protected void renderBg(GuiGraphics gfx, float v, int i, int i1) {
        gfx.blit(Ref.Textures.PORT_GUI, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight);
        for (Vec2 slot : slots) {
            gfx.blit(Ref.Textures.SLOT_PARTS, this.leftPos + (int)slot.x, this.topPos + (int)slot.y, 0, 26, 18, 18);
        }
    }

    @Override
    protected void renderLabels(GuiGraphics gfx, int mouseX, int mouseY) {
        gfx.drawWordWrap(this.font, header, 8, 8, 150, 0x404040);
    }

    @Override
    public void render(GuiGraphics gfx, int partialTicks, int mouseX, float mouseY) {
        renderBackground(gfx);
        super.render(gfx, partialTicks, mouseX, mouseY);
    }
}
