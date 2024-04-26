package io.ticticboom.mods.mm.port.fluid;

import io.ticticboom.mods.mm.port.IPortStorage;
import io.ticticboom.mods.mm.port.IPortStorageFactory;

public class FluidPortStorageFactory implements IPortStorageFactory {

    private final FluidPortStorageModel model;

    public FluidPortStorageFactory(FluidPortStorageModel model) {

        this.model = model;
    }

    @Override
    public IPortStorage createPortStorage() {
        return new FluidPortStorage(model);
    }
}
