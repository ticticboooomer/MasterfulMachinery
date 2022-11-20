package io.ticticboom.mods.mm.ports.item;

import com.mojang.blaze3d.vertex.PoseStack;
import io.ticticboom.mods.mm.Ref;
import io.ticticboom.mods.mm.block.entity.PortBlockEntity;
import io.ticticboom.mods.mm.client.container.PortContainer;
import io.ticticboom.mods.mm.client.screen.PortScreen;
import io.ticticboom.mods.mm.ports.base.PortStorage;
import io.ticticboom.mods.mm.setup.MMCapabilities;
import io.ticticboom.mods.mm.util.RenderHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.Containers;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.Vec2;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.ItemStackHandler;

public class ItemPortStorage extends PortStorage {

    private ItemConfiguredPort config;
    public final ItemHandler items;
    public final LazyOptional<ItemStackHandler> handlerLO;
    public final ItemContainer inv;

    public ItemPortStorage(ItemConfiguredPort config) {
        this.config = config;
        items = new ItemHandler(config.slotCols() * config.slotRows());
        handlerLO = LazyOptional.of(() -> items);
        inv = new ItemContainer(items);
    }

    @Override
    public void read(CompoundTag tag) {
        ContainerHelper.loadAllItems(tag, items.getStacks());
    }

    @Override
    public CompoundTag write() {
        var result = new CompoundTag();
        ContainerHelper.saveAllItems(result, items.getStacks());
        return result;
    }

    private Vec2 getSlotStart() {
        final var slot = 18;

        var colP = config.slotCols() / 9f;
        var rowP = config.slotRows() / 6f;

        var defaultWidth = slot * 9;
        var defaultHeight = slot * 6;

        var actualWidth = defaultWidth * colP;
        var actualHeight = defaultHeight * rowP;

        var actualX = (defaultWidth / 2) - (actualWidth / 2);
        var actualY = (defaultHeight / 2) - (actualHeight / 2);
        return new Vec2(actualX + 8, actualY + 8);
    }

    @Override
    public void setupContainer(PortContainer container, Inventory pinv, BlockEntity be) {
        super.setupContainer(container, pinv, be);
        Vec2 start = getSlotStart();
        for (var x = 0; x < config.slotCols(); x++) {
            for (var y = 0; y < config.slotRows(); y++) {
                container.addSlot(new Slot(inv, x + y * config.slotCols(), (int) start.x + (x * 18), (int) start.y + (y * 18)));
            }
        }
    }

    @Override
    public void renderScreen(PortScreen screen, PoseStack ms, int mx, int my) {
        RenderHelper.useTexture(Ref.SLOT_PARTS);
        Vec2 start = getSlotStart();
        for (var x = 0; x < config.slotCols(); x++) {
            for (var y = 0; y < config.slotRows(); y++) {
                screen.blit(ms, screen.getGuiLeft() + (int) start.x + (x * 18) - 1, screen.getGuiTop() + (int) start.y + (y * 18) - 1, 0, 26, 18, 18);
            }
        }
    }

    @Override
    public void onDestroy(Level level, BlockPos pos) {
        Containers.dropContents(level, pos, inv);
    }

    @Override
    public PortStorage deepClone() {
        var copy = new ItemPortStorage(config);
        for (int i = 0; i < copy.items.getSlots(); i++) {
            var itemClone = this.items.getStackInSlot(i).copy();
            copy.items.setStackInSlot(i, itemClone);
        }
        return copy;
    }

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> cap) {
        if (cap == MMCapabilities.ITEMS) {
            return handlerLO.cast();
        }
        return LazyOptional.empty();
    }
}
