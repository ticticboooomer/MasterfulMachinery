package io.ticticboom.mods.mm.port.item;

import io.ticticboom.mods.mm.port.common.ISlottedPortStorageModel;

public record ItemPortStorageModel(
        int rows,
        int columns
) implements ISlottedPortStorageModel {
}
