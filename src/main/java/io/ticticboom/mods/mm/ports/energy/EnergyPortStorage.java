package io.ticticboom.mods.mm.ports.energy;

import io.ticticboom.mods.mm.ports.base.PortStorage;
import io.ticticboom.mods.mm.capability.MMCapabilities;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;

public class EnergyPortStorage extends PortStorage {
    public EnergyConfiguredPort config;
    public EnergyHandler handler;
    public final LazyOptional<EnergyHandler> handlerLO;

    public EnergyPortStorage(EnergyConfiguredPort config) {
        this.config = config;
        handler = new EnergyHandler(config.capacity());
        handlerLO = LazyOptional.of(() -> handler);
    }

    @Override
    public void read(CompoundTag tag) {
        var stored = tag.getInt("Amount");
        handler.setStored(stored);
    }

    @Override
    public CompoundTag write() {
        var result = new CompoundTag();
        result.putInt("Amount", handler.getEnergyStored());
        return result;
    }

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> cap) {
        if (cap == MMCapabilities.ENERGY) {
            return handlerLO.cast();
        }
        return LazyOptional.empty();
    }

    @Override
    public void onDestroy(Level level, BlockPos pos) {

    }

    @Override
    public PortStorage deepClone() {
        var result = new EnergyPortStorage(config);
        result.handler = new EnergyHandler(config.capacity());
        result.handler.setStored(handler.getEnergyStored());
        return result;
    }
}
