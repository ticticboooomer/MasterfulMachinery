package io.ticticboom.mods.mm.ports.energy;

import net.minecraftforge.energy.EnergyStorage;

public class EnergyHandler extends EnergyStorage {
    public EnergyHandler(int capacity) {
        super(capacity);
    }

    public void setStored(int stored) {
        this.energy = stored;
    }
}
