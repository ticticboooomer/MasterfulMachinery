package io.ticticboom.mods.mm.port.mekanism.slurry;

import io.ticticboom.mods.mm.port.IPortStorage;
import io.ticticboom.mods.mm.port.common.INotifyChangeFunction;
import io.ticticboom.mods.mm.port.mekanism.chemical.MekanismChemicalPortStorageFactory;
import io.ticticboom.mods.mm.port.mekanism.chemical.MekanismChemicalPortStorageModel;
import mekanism.api.chemical.slurry.Slurry;
import mekanism.api.chemical.slurry.SlurryStack;

public class MekanismSlurryPortStorageFactory extends MekanismChemicalPortStorageFactory<Slurry, SlurryStack> {

    public MekanismSlurryPortStorageFactory(MekanismChemicalPortStorageModel model) {
        super(model);
    }

    @Override
    public IPortStorage createPortStorage(INotifyChangeFunction changed) {
        return new MekanismSlurryPortStorage(model, changed);
    }
}
