package io.ticticboom.mods.mm.port.energy;

import com.google.gson.JsonObject;
import io.ticticboom.mods.mm.cap.MMCapabilities;
import io.ticticboom.mods.mm.port.IPortStorage;
import io.ticticboom.mods.mm.port.IPortStorageModel;
import io.ticticboom.mods.mm.port.common.INotifyChangeFunction;
import lombok.Getter;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.IEnergyStorage;

import java.util.UUID;

public class EnergyPortStorage implements IPortStorage {


    private final EnergyPortStorageModel model;
    @Getter
    private final EnergyPortHandler handler;
    private final LazyOptional<IEnergyStorage> handlerLazyOptional;
    private final UUID uid = UUID.randomUUID();

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

    @Override
    public UUID getStorageUid() {
        return uid;
    }

    @Override
    public JsonObject debugDump() {
        JsonObject dump = new JsonObject();
        dump.addProperty("uid", uid.toString());
        dump.addProperty("stored", handler.getEnergyStored());
        dump.addProperty("maxReceive", model.maxReceive());
        dump.addProperty("maxExtract", model.maxExtract());
        dump.addProperty("capacity", model.capacity());
        return dump;
    }

    public int internalExtract(int amount, boolean simulate) {
        return handler.unboundedExtractEnergy(amount, simulate);
    }

    public int internalInsert(int amount, boolean simulate) {
        return handler.unboundedReceiveEnergy(amount, simulate);
    }

    public int getStoredEnergy()  {
        return handler.getEnergyStored();
    }
}
