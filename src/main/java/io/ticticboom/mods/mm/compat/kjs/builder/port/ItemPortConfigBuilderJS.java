package io.ticticboom.mods.mm.compat.kjs.builder.port;

import dev.latvian.mods.rhino.util.HideFromJS;
import io.ticticboom.mods.mm.port.IPortStorageModel;
import io.ticticboom.mods.mm.port.item.ItemPortStorageModel;
import lombok.Getter;

@Getter
public class ItemPortConfigBuilderJS extends PortConfigBuilderJS {
    private int rows;
    private int columns;


    public ItemPortConfigBuilderJS rows(int rows) {
        this.rows = rows;
        return this;
    }

    public ItemPortConfigBuilderJS columns(int columns) {
        this.columns = columns;
        return this;
    }

    @HideFromJS
    @Override
    public IPortStorageModel build() {
        return new ItemPortStorageModel(rows, columns);
    }
}
