package io.ticticboom.mods.mm.ports.item;

import io.ticticboom.mods.mm.ports.IPortStorage;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.ItemStackHandler;

public class ItemPortStorage implements IPortStorage {

    private final ItemStackHandler handler;
    private final LazyOptional<ItemStackHandler> handlerLazyOptional;
    private final ItemPortStorageModel model;

    public ItemPortStorage(ItemPortStorageModel model) {
        this.model = model;
        handler = new ItemStackHandler(model.rows() * model.columns());
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
}
