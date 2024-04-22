package io.ticticboom.mods.mm.ports;

import io.ticticboom.mods.mm.datagen.provider.MMBlockstateProvider;

public interface IPortBlock extends IPortPart {
    void generateModel(MMBlockstateProvider provider);
}
