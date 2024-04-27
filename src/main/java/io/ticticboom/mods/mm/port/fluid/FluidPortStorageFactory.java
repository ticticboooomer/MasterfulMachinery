package io.ticticboom.mods.mm.port.fluid;

import io.ticticboom.mods.mm.port.IPortStorage;
import io.ticticboom.mods.mm.port.IPortStorageFactory;
import io.ticticboom.mods.mm.port.common.INotifyChangeFunction;

public class FluidPortStorageFactory implements IPortStorageFactory {

    private final FluidPortStorageModel model;

    public FluidPortStorageFactory(FluidPortStorageModel model) {

        this.model = model;
    }

    @Override
    public IPortStorage createPortStorage(INotifyChangeFunction changed) {
        return new FluidPortStorage(model, changed);
    }
}
