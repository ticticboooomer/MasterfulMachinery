package io.ticticboom.mods.mm.port.mekanism.gas;

import io.ticticboom.mods.mm.port.IPortStorage;
import io.ticticboom.mods.mm.port.common.INotifyChangeFunction;
import io.ticticboom.mods.mm.port.mekanism.chemical.MekanismChemicalPortStorageFactory;
import io.ticticboom.mods.mm.port.mekanism.chemical.MekanismChemicalPortStorageModel;
import mekanism.api.chemical.gas.Gas;
import mekanism.api.chemical.gas.GasStack;

public class MekanismGasPortStorageFactory extends MekanismChemicalPortStorageFactory<Gas, GasStack> {

    public MekanismGasPortStorageFactory(MekanismChemicalPortStorageModel model) {
        super(model);
    }

    @Override
    public IPortStorage createPortStorage(INotifyChangeFunction changed) {
        return new MekanismGasPortStorage(model, changed);
    }
}
