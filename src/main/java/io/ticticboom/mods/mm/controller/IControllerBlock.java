package io.ticticboom.mods.mm.controller;

import io.ticticboom.mods.mm.datagen.provider.MMBlockstateProvider;

public interface IControllerBlock extends IControllerPart {
    void generateModel(MMBlockstateProvider provider);
}
