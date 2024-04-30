package io.ticticboom.mods.mm.port.energy;

import io.ticticboom.mods.mm.port.IPortStorage;
import io.ticticboom.mods.mm.port.IPortStorageFactory;
import io.ticticboom.mods.mm.port.common.INotifyChangeFunction;

public class EnergyPortStorageFactory implements IPortStorageFactory {

    private final EnergyPortStorageModel model;

    public EnergyPortStorageFactory(EnergyPortStorageModel model) {
        this.model = model;
    }

    @Override
    public IPortStorage createPortStorage(INotifyChangeFunction changed) {
        return new EnergyPortStorage(model, changed);
    }
}
