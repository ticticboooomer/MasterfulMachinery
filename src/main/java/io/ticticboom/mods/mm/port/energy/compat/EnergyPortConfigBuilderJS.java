package io.ticticboom.mods.mm.port.energy.compat;

import io.ticticboom.mods.mm.compat.kjs.builder.PortConfigBuilderJS;
import io.ticticboom.mods.mm.port.IPortStorageModel;
import io.ticticboom.mods.mm.port.energy.EnergyPortStorageModel;

public class EnergyPortConfigBuilderJS extends PortConfigBuilderJS {

    private int capacity;
    private int maxReceive;
    private int maxExtract;

    public EnergyPortConfigBuilderJS() {

    }

    public EnergyPortConfigBuilderJS capacity(int capacity) {
        this.capacity = capacity;
        return this;
    }

    public EnergyPortConfigBuilderJS maxReceive(int maxReceive) {
        this.maxReceive = maxReceive;
        return this;
    }

    public EnergyPortConfigBuilderJS maxExtract(int maxExtract) {
        this.maxExtract = maxExtract;
        return this;
    }

    @Override
    public IPortStorageModel build() {
        return new EnergyPortStorageModel(capacity, maxReceive, maxExtract);
    }
}
