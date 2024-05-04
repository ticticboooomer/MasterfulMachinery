package io.ticticboom.mods.mm.piece.modifier;

import io.ticticboom.mods.mm.piece.StructurePieceSetupMetadata;
import io.ticticboom.mods.mm.structure.StructureModel;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Rotation;

import java.util.List;

public interface IStructurePieceModifier {
    List<String> validateSetup(StructurePieceSetupMetadata meta, List<Block> requiredBlocks);
    boolean formed(Level level, BlockPos pos, StructureModel model, Rotation rotation);
}
