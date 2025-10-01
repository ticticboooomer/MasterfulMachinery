package io.ticticboom.mods.mm.piece.type.tag;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import io.ticticboom.mods.mm.piece.StructurePieceSetupMetadata;
import io.ticticboom.mods.mm.piece.type.StructurePiece;
import io.ticticboom.mods.mm.structure.StructureModel;
import io.ticticboom.mods.mm.util.WorldUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
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
        if (tagBlocks.isEmpty()) {
            throw new RuntimeException(String.format("Tag: %s does not contain any blocks or doesnt exist!", tagKey));
        }
    }

    @Override
    public boolean formed(Level level, BlockPos pos, StructureModel model) {
        var state = WorldUtil.getBlockState(pos, (ServerLevel) level);
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

    @Override
    public JsonObject debugExpected(Level level, BlockPos pos, StructureModel model, JsonObject json) {
        json.addProperty("tag", tagId.toString());
        var blocksJson = new JsonArray();
        for (Block tagBlock : tagBlocks) {
            blocksJson.add(ForgeRegistries.BLOCKS.getKey(tagBlock).toString());
        }
        json.add("possibleBlocks", blocksJson);
        return json;
    }

    @Override
    public JsonObject debugFound(Level level, BlockPos pos, StructureModel model, JsonObject json) {
        BlockState foundState = level.getBlockState(pos);
        var found = foundState.getBlock();
        var foundId = ForgeRegistries.BLOCKS.getKey(found);
        json.addProperty("block", foundId.toString());
        var tagsJson = new JsonArray();
        foundState.getTags().map(x -> x.location().toString()).forEach(tagsJson::add);
        json.add("foundTags", tagsJson);
        return json;
    }
}
