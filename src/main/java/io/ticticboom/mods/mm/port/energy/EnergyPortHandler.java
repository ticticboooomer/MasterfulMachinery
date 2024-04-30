package io.ticticboom.mods.mm.port.energy;

import io.ticticboom.mods.mm.port.common.INotifyChangeFunction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.energy.EnergyStorage;

public class EnergyPortHandler extends EnergyStorage {
    private final INotifyChangeFunction changed;

    public EnergyPortHandler(int capacity, int maxReceive, int maxExtract, INotifyChangeFunction changed) {
        super(capacity, maxReceive, maxExtract);
        this.changed = changed;
    }

    @Override
    public int receiveEnergy(int maxReceive, boolean simulate) {
        changed.call();
        return super.receiveEnergy(maxReceive, simulate);
    }

    @Override
    public int extractEnergy(int maxExtract, boolean simulate) {
        changed.call();
        return super.extractEnergy(maxExtract, simulate);
    }

}
