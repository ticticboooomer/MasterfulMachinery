package io.ticticboom.mods.mm.recipe.structure;

import io.ticticboom.mods.mm.recipe.IConfiguredRecipeEntry;
import io.ticticboom.mods.mm.structure.ConfiguredStructurePart;
import net.minecraft.core.BlockPos;

public record StructurePartConfiguredRecipeEntry(
        ConfiguredStructurePart part,
        BlockPos relativePos
) implements IConfiguredRecipeEntry {

}
