package io.ticticboom.mods.mm.port.fluid.compat;

import io.ticticboom.mods.mm.compat.kjs.builder.port.PortConfigBuilderJS;
import io.ticticboom.mods.mm.port.IPortStorageModel;
import io.ticticboom.mods.mm.port.fluid.FluidPortStorage;
import io.ticticboom.mods.mm.port.fluid.FluidPortStorageModel;

public class FluidPortConfigBuilderJS extends PortConfigBuilderJS {

    private int rows;
    private int columns;
    private int slotCapacity;

    @Override
    public IPortStorageModel build() {
        return new FluidPortStorageModel(rows, columns, slotCapacity);
    }

    public FluidPortConfigBuilderJS rows(int rows) {
        this.rows = rows;
        return this;
    }

    public FluidPortConfigBuilderJS columns(int columns) {
        this.columns = columns;
        return this;
    }

    public FluidPortConfigBuilderJS slotCapacity(int slotCapacity) {
        this.slotCapacity = slotCapacity;
        return this;
    }
}
