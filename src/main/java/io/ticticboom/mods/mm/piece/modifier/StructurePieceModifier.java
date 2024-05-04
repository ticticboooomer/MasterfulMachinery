package io.ticticboom.mods.mm.piece.modifier;

import io.ticticboom.mods.mm.piece.StructurePieceSetupMetadata;
import io.ticticboom.mods.mm.structure.StructureModel;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;

public abstract class StructurePieceModifier {
    @Getter
    @Setter
    private boolean setup = false;
    public abstract void validateSetup(StructurePieceSetupMetadata meta, List<Block> requiredBlocks);
    public abstract boolean formed(Level level, BlockPos pos, StructureModel model, Rotation rotation);
    public abstract BlockState modifyBlockState(BlockState state, BlockEntity be, BlockPos pos);
    public abstract BlockEntity modifyBlockEntity(BlockState state, BlockEntity be, BlockPos pos);
}
