package io.ticticboom.mods.mm.port.item.compat;

import io.ticticboom.mods.mm.compat.kjs.builder.port.PortConfigBuilderJS;
import io.ticticboom.mods.mm.port.IPortStorageModel;
import io.ticticboom.mods.mm.port.item.ItemPortStorageModel;

public class ItemPortConfigBuilderJS extends PortConfigBuilderJS {

    private int rows;
    private int columns;

    @Override
    public IPortStorageModel build() {
        return new ItemPortStorageModel(rows, columns);
    }

    public ItemPortConfigBuilderJS rows(int rows) {
        this.rows = rows;
        return this;
    }

    public ItemPortConfigBuilderJS columns(int columns) {
        this.columns = columns;
        return this;
    }
}
