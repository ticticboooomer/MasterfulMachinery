package io.ticticboom.mods.mm.port.mekanism.chemical.compat;

import io.ticticboom.mods.mm.compat.kjs.builder.PortConfigBuilderJS;
import io.ticticboom.mods.mm.port.IPortStorageModel;
import io.ticticboom.mods.mm.port.mekanism.chemical.MekanismChemicalPortStorageModel;

public class MekanismChemicalConfigBuilderJS extends PortConfigBuilderJS {

    private long amount;

    public MekanismChemicalConfigBuilderJS amount(long amount) {
        this.amount = amount;
        return this;
    }

    @Override
    public IPortStorageModel build() {
        return new MekanismChemicalPortStorageModel(amount);
    }
}
