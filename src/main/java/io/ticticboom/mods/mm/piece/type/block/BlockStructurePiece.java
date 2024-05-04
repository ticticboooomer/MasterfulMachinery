package io.ticticboom.mods.mm.piece.type.block;

import io.ticticboom.mods.mm.piece.StructurePieceSetupMetadata;
import io.ticticboom.mods.mm.piece.type.IStructurePiece;
import io.ticticboom.mods.mm.piece.util.StructurePieceUtils;
import io.ticticboom.mods.mm.structure.StructureModel;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.List;

public class BlockStructurePiece implements IStructurePiece {

    private final ResourceLocation blockId;
    private Block block;

    public BlockStructurePiece(ResourceLocation blockId) {
        this.blockId = blockId;
    }

    @Override
    public List<String> validateSetup(StructurePieceSetupMetadata meta) {
        List<String> errors = new ArrayList<>();
        block = ForgeRegistries.BLOCKS.getValue(blockId);
        if (block == null) {
            StructurePieceUtils.errorMessageFor("'block' field is not a valid block id", meta);
        }
        return errors;
    }

    @Override
    public boolean formed(Level level, BlockPos pos, StructureModel model) {
        return level.getBlockState(pos).is(block);
    }
}
