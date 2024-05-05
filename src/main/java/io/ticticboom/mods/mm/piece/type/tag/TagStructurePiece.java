package io.ticticboom.mods.mm.piece.type.tag;

import io.ticticboom.mods.mm.piece.StructurePieceSetupMetadata;
import io.ticticboom.mods.mm.piece.type.StructurePiece;
import io.ticticboom.mods.mm.structure.StructureModel;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;
import java.util.function.Supplier;

public class TagStructurePiece extends StructurePiece {

    private final ResourceLocation tagId;
    private final TagKey<Block> tagKey;
    private List<Block> tagBlocks;


    public TagStructurePiece(ResourceLocation tagId) {
        this.tagId = tagId;
        tagKey = TagKey.create(ForgeRegistries.BLOCKS.getRegistryKey(), tagId);
    }

    @Override
    public void validateSetup(StructurePieceSetupMetadata meta) {
        tagBlocks = ForgeRegistries.BLOCKS.tags().getTag(tagKey).stream().toList();
    }

    @Override
    public boolean formed(Level level, BlockPos pos, StructureModel model) {
        var state = level.getBlockState(pos);
        return state.is(tagKey);
    }

    @Override
    public Supplier<List<Block>> createBlocksSupplier() {
        return () -> tagBlocks;
    }

    @Override
    public Component createDisplayComponent() {
        return Component.literal("Block Tag: ").append(Component.literal(tagId.toString()).withStyle(ChatFormatting.DARK_AQUA));
    }
}
