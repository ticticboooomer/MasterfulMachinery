package io.ticticboom.mods.mm.piece.type;

import io.ticticboom.mods.mm.piece.StructurePieceSetupMetadata;
import io.ticticboom.mods.mm.structure.StructureModel;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

import java.util.List;

public interface IStructurePiece {
    List<String> validateSetup(StructurePieceSetupMetadata meta);
    boolean formed(Level level, BlockPos pos, StructureModel model);
}
