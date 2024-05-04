package io.ticticboom.mods.mm.piece;

import io.ticticboom.mods.mm.recipe.RecipeStorages;

public record StructurePieceSetupMetadata(
        RecipeStorages structureId,
        char keyChar
) {
}
