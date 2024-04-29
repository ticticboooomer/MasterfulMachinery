package io.ticticboom.mods.mm.port.fluid;

import io.ticticboom.mods.mm.cap.MMCapabilities;
import io.ticticboom.mods.mm.port.IPortStorage;
import io.ticticboom.mods.mm.port.IPortStorageModel;
import io.ticticboom.mods.mm.port.common.INotifyChangeFunction;
import lombok.Getter;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;

public class FluidPortStorage implements IPortStorage {
    private final FluidPortStorageModel model;

    @Getter
    private final FluidPortHandler handler;
    private final LazyOptional<FluidPortHandler> handlerLazyOptional;

    public FluidPortStorage(FluidPortStorageModel model, INotifyChangeFunction changed) {
        this.model = model;
        handler = new FluidPortHandler(model.rows() * model.columns(), model.slotCapacity(), changed);
        handlerLazyOptional = LazyOptional.of(() -> handler);
    }

    public IFluidHandler getWrappedHandler() {
        return new WrappedFluidPortHandler(handler);
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
        return MMCapabilities.FLUID == capability;
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

    public FluidStack getStackInSlot(int slot) {
        return handler.getFluidInTank(slot);
    }
}
