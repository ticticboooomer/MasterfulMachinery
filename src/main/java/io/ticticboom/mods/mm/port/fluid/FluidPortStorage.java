package io.ticticboom.mods.mm.port.fluid;

import io.ticticboom.mods.mm.port.IPortStorage;
import io.ticticboom.mods.mm.port.IPortStorageModel;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.TagTypes;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;

public class FluidPortStorage implements IPortStorage {
    private final FluidPortStorageModel model;

    private final FluidPortHandler handler;
    private final LazyOptional<FluidPortHandler> handlerLazyOptional;

    public FluidPortStorage(FluidPortStorageModel model) {
        this.model = model;
        handler = new FluidPortHandler(model.rows() * model.columns(), model.slotCapacity());
        handlerLazyOptional = LazyOptional.of(() -> handler);
    }

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> capability) {
        if (hasCapability(capability)) {
            return handlerLazyOptional.cast();
        }
        return LazyOptional.empty();
    }

    @Override
    public <T> boolean hasCapability(Capability<T> capability) {
        return ForgeCapabilities.FLUID_HANDLER == capability;
    }

    @Override
    public CompoundTag save(CompoundTag tag) {
        var compoundTag = handler.serializeNBT();
        tag.put("handler", compoundTag);
        return tag;
    }

    @Override
    public void load(CompoundTag tag) {
        var compoundTag = tag.get("handler");
        handler.deserializeNBT(compoundTag);
    }

    @Override
    public IPortStorageModel getStorageModel() {
        return model;
    }
}
