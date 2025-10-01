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

    public int unboundedReceiveEnergy(int maxReceive, boolean simulate) {
        var originalReceive = this.maxReceive;
        this.maxReceive = maxReceive;
        var resp = receiveEnergy(maxReceive, simulate);
        this.maxReceive = originalReceive;
        return resp;
    }

    public int unboundedExtractEnergy(int maxExtract, boolean simulate) {
        var originalReceive = this.maxExtract;
        this.maxExtract = maxExtract;

        var resp = extractEnergy(maxExtract, simulate);

        this.maxExtract = originalReceive;
        return resp;
    }

    @Override
    public int receiveEnergy(int maxReceive, boolean simulate) {
        int result = super.receiveEnergy(maxReceive, simulate);
        if (result > 0 && !simulate) {
            changed.call();
        }
        return result;
    }

    @Override
    public int extractEnergy(int maxExtract, boolean simulate) {
        int result = super.extractEnergy(maxExtract, simulate);
        if (result > 0 && !simulate) {
            changed.call();
        }
        return result;
    }

}
