package io.ticticboom.mods.mm.structure.layout;

import lombok.Getter;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

public record StructurePiecePredicateParams(Level level, BlockPos pos) {
}
