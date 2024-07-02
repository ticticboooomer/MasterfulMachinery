package io.ticticboom.mods.mm.port.botania.mana.compat;

import io.ticticboom.mods.mm.compat.kjs.builder.PortConfigBuilderJS;
import io.ticticboom.mods.mm.port.IPortStorageModel;
import io.ticticboom.mods.mm.port.botania.mana.BotaniaManaPortStorageModel;

public class BotaniaManaPortBuilderJS extends PortConfigBuilderJS {

    private int capacity;

    public BotaniaManaPortBuilderJS capacity(int capacity) {
        this.capacity = capacity;
        return this;
    }

    @Override
    public IPortStorageModel build() {
        return new BotaniaManaPortStorageModel(capacity);
    }
}
