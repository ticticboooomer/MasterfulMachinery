package io.ticticboom.mods.mm.ports.item;

import io.ticticboom.mods.mm.model.config.PortModel;
import io.ticticboom.mods.mm.ports.IPortStorage;
import io.ticticboom.mods.mm.ports.IPortStorageModel;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;

public class ItemPortStorage implements IPortStorage {

    private final ItemPortHandler handler;
    private final LazyOptional<ItemPortHandler> handlerLazyOptional;
    private final ItemPortStorageModel model;

    public ItemPortStorage(ItemPortStorageModel model) {
        this.model = model;
        handler = new ItemPortHandler(model.rows() * model.columns());
        handlerLazyOptional = LazyOptional.of(() -> handler);
    }

    @Override
    public <T> LazyOptional<T> getCapability() {
        return handlerLazyOptional.cast();
    }

    @Override
    public <T> boolean hasCapability(Capability<T> capability) {
        return capability == ForgeCapabilities.ITEM_HANDLER;
    }

    @Override
    public CompoundTag save(CompoundTag tag) {
        CompoundTag compoundTag = handler.serializeNBT();
        tag.put("handler", compoundTag);
        return tag;
    }

    @Override
    public void load(CompoundTag tag) {
        CompoundTag compoundTag = tag.getCompound("handler");
        handler.deserializeNBT(compoundTag);
    }

    @Override
    public IPortStorageModel getStorageModel() {
        return model;
    }

    @Override
    public void setupContainer(AbstractContainerMenu container, Inventory inv, PortModel portModel) {
        var columns = model.columns();
        var rows = model.rows();

        int offsetX = ((162 - (columns * 18)) / 2) + 8;
        int offsetY = ((108 - (rows * 18)) / 2) + 8;

        var portInv = new ItemPortContainer(this.handler);

        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < columns; x++) {
                container.addSlot(new Slot(portInv,  (y * columns) + x, x * 18 + offsetX, y * 18 + offsetY));
            }
        }

        IPortStorage.super.setupContainer(container, inv, portModel);
    }
}
