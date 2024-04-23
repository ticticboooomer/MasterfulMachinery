package io.ticticboom.mods.mm.controller;

import io.ticticboom.mods.mm.datagen.provider.MMBlockstateProvider;
import net.minecraft.world.level.block.EntityBlock;

public interface IControllerBlock extends IControllerPart, EntityBlock {
    void generateModel(MMBlockstateProvider provider);
}
