package io.ticticboom.mods.mm.structure.layout;

import io.ticticboom.mods.mm.structure.StructureModel;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Rotation;


public record PositionedLayoutPiece(
        BlockPos pos,
        StructureLayoutPiece piece
) {
    public boolean formed(Level level, BlockPos controllerPos, StructureModel model) {
        BlockPos offset = controllerPos.offset(controllerPos);
        return piece.formed(level, offset, model);
    }

    public PositionedLayoutPiece rotate(Rotation rot) {
        return new PositionedLayoutPiece(pos.rotate(rot), piece);
    }
}
