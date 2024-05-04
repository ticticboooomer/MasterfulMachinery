package io.ticticboom.mods.mm.piece.type;

import io.ticticboom.mods.mm.piece.StructurePieceSetupMetadata;
import io.ticticboom.mods.mm.structure.StructureModel;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

import java.util.List;
import java.util.function.Supplier;

public abstract class StructurePiece {

    @Setter
    @Getter
    protected boolean setup = false;

    public abstract void validateSetup(StructurePieceSetupMetadata meta);
    public abstract boolean formed(Level level, BlockPos pos, StructureModel model);
    public abstract Supplier<List<Block>> createBlocksSupplier();
    public abstract Component createDisplayComponent();
}
