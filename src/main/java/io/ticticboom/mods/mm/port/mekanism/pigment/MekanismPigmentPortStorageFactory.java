package io.ticticboom.mods.mm.port.mekanism.pigment;

import io.ticticboom.mods.mm.port.IPortStorage;
import io.ticticboom.mods.mm.port.common.INotifyChangeFunction;
import io.ticticboom.mods.mm.port.mekanism.chemical.MekanismChemicalPortStorageFactory;
import io.ticticboom.mods.mm.port.mekanism.chemical.MekanismChemicalPortStorageModel;
import mekanism.api.chemical.pigment.Pigment;
import mekanism.api.chemical.pigment.PigmentStack;

public class MekanismPigmentPortStorageFactory extends MekanismChemicalPortStorageFactory<Pigment, PigmentStack> {

    public MekanismPigmentPortStorageFactory(MekanismChemicalPortStorageModel model) {
        super(model);
    }

    @Override
    public IPortStorage createPortStorage(INotifyChangeFunction changed) {
        return new MekanismPigmentPortStorage(model, changed);
    }
}
