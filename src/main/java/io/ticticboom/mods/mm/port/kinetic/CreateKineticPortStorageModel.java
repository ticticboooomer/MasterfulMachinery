package io.ticticboom.mods.mm.port.kinetic;

import io.ticticboom.mods.mm.port.IPortStorageModel;

public record CreateKineticPortStorageModel(
        float stress
) implements IPortStorageModel {
}
