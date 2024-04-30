package io.ticticboom.mods.mm.block;

import io.ticticboom.mods.mm.datagen.provider.MMBlockstateProvider;

public interface IExtraBlock extends IExtraBlockPart {
    void generateModel(MMBlockstateProvider provider);
}
