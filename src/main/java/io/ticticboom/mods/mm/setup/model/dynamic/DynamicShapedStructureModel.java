package io.ticticboom.mods.mm.setup.model.dynamic;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public record DynamicShapedStructureModel(
        ResourceLocation id,
        Component name,
        ResourceLocation controllerId
) {

}
