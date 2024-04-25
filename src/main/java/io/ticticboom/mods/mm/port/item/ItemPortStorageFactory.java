package io.ticticboom.mods.mm.port.item;

import io.ticticboom.mods.mm.port.IPortStorage;
import io.ticticboom.mods.mm.port.IPortStorageFactory;

public class ItemPortStorageFactory implements IPortStorageFactory {

    private final ItemPortStorageModel model;

    public ItemPortStorageFactory(ItemPortStorageModel model) {
        this.model = model;
    }

    @Override
    public IPortStorage createPortStorage() {
        return new ItemPortStorage(model);
    }
}
