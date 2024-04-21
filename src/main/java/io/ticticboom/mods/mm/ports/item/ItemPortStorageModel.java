package io.ticticboom.mods.mm.ports.item;

import io.ticticboom.mods.mm.ports.IPortStorageModel;

public record ItemPortStorageModel(
        int rows,
        int columns
) implements IPortStorageModel {
}
