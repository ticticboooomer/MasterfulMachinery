package io.ticticboom.mods.mm.structure;

import net.minecraft.world.level.Level;

public record StructureFormParams(
        Level level,
        StructureModel model
) {
}
