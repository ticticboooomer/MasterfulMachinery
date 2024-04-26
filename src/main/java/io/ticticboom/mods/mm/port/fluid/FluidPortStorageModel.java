package io.ticticboom.mods.mm.port.fluid;

import io.ticticboom.mods.mm.port.common.ISlottedPortStorageModel;

public record FluidPortStorageModel(
    int rows,
    int columns,
    int slotCapacity
) implements ISlottedPortStorageModel {
}
