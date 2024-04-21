package io.ticticboom.mods.mm.ports.item;

import io.ticticboom.mods.mm.ports.IPortStorage;
import io.ticticboom.mods.mm.ports.IPortStorageFactory;

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
