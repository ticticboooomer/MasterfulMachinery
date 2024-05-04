package io.ticticboom.mods.mm.structure.layout;

import io.ticticboom.mods.mm.structure.StructureModel;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Rotation;


public record PositionedLayoutPiece(
        BlockPos pos,
        StructureLayoutPiece piece
) {
    public boolean formed(Level level, BlockPos controllerPos, StructureModel model, Rotation rot) {
        return piece.formed(level, findAbsolutePos(controllerPos), model, rot);
    }

    public BlockPos findAbsolutePos(BlockPos controllerPos) {
        return controllerPos.offset(pos);
    }

    public PositionedLayoutPiece rotate(Rotation rot) {
        return new PositionedLayoutPiece(pos.rotate(rot), piece);
    }
}
