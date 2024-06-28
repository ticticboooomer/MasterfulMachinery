package io.ticticboom.mods.mm.port.mekanism.chemical;

import io.ticticboom.mods.mm.port.IPortStorageModel;

public record MekanismChemicalPortStorageModel(
        long amount
) implements IPortStorageModel {
}
