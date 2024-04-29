package io.ticticboom.mods.mm.port.energy;

import io.ticticboom.mods.mm.cap.MMCapabilities;
import io.ticticboom.mods.mm.port.IPortStorage;
import io.ticticboom.mods.mm.port.IPortStorageModel;
import io.ticticboom.mods.mm.port.common.INotifyChangeFunction;
import lombok.Getter;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.EnergyStorage;
import net.minecraftforge.energy.IEnergyStorage;

public class EnergyPortStorage implements IPortStorage {


    private final EnergyPortStorageModel model;
    @Getter
    private final EnergyPortHandler handler;
    private final LazyOptional<IEnergyStorage> handlerLazyOptional;

    public EnergyPortStorage(EnergyPortStorageModel model, INotifyChangeFunction changed) {
        this.model = model;
        handler = new EnergyPortHandler(model.capacity(), model.maxReceive(), model.maxExtract(), changed);
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
        return MMCapabilities.ENERGY == capability;
    }

    @Override
    public CompoundTag save(CompoundTag tag) {
        tag.put("handler", handler.serializeNBT());
        return tag;
    }

    @Override
    public void load(CompoundTag tag) {
        handler.deserializeNBT(tag.get("handler"));
    }

    @Override
    public IPortStorageModel getStorageModel() {
        return model;
    }
}
