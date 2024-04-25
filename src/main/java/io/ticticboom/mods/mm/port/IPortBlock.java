package io.ticticboom.mods.mm.port;

import io.ticticboom.mods.mm.datagen.provider.MMBlockstateProvider;

public interface IPortBlock extends IPortPart {
    void generateModel(MMBlockstateProvider provider);
}
