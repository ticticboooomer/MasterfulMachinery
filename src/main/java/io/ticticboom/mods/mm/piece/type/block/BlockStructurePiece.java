package io.ticticboom.mods.mm.piece.type.block;

import com.google.gson.JsonObject;
import io.ticticboom.mods.mm.piece.StructurePieceSetupMetadata;
import io.ticticboom.mods.mm.piece.type.StructurePiece;
import io.ticticboom.mods.mm.util.StructurePieceUtils;
import io.ticticboom.mods.mm.structure.StructureModel;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;
import java.util.function.Supplier;

public class BlockStructurePiece extends StructurePiece {

    private final ResourceLocation blockId;
    private Block block;

    public BlockStructurePiece(ResourceLocation blockId) {
        this.blockId = blockId;
    }

    @Override
    public void validateSetup(StructurePieceSetupMetadata meta) {
        block = ForgeRegistries.BLOCKS.getValue(blockId);
        if (block == null) {
            throw new RuntimeException(StructurePieceUtils.errorMessageFor("'block' field is not a valid block id", meta));
        }
        setup = true;
    }

    @Override
    public boolean formed(Level level, BlockPos pos, StructureModel model) {
        return level.getBlockState(pos).is(block);
    }

    @Override
    public Supplier<List<Block>> createBlocksSupplier() {
        return () -> List.of(block);
    }

    @Override
    public Component createDisplayComponent() {
        return Component.literal("Block Id: ").append(Component.literal(blockId.toString()).withStyle(ChatFormatting.DARK_AQUA));
    }

    @Override
    public JsonObject debugExpected(Level level, BlockPos pos, StructureModel model, JsonObject json) {
        json.addProperty("block", blockId.toString());
        return json;
    }

    @Override
    public JsonObject debugFound(Level level, BlockPos pos, StructureModel model, JsonObject json) {
        var foundId = ForgeRegistries.BLOCKS.getKey(level.getBlockState(pos).getBlock());
        json.addProperty("block", foundId.toString());
        return json;
    }
}