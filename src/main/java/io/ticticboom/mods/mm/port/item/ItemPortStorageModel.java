package io.ticticboom.mods.mm.port.item;

import io.ticticboom.mods.mm.port.IPortStorageModel;

public record ItemPortStorageModel(
        int rows,
        int columns
) implements IPortStorageModel {
}
