package io.ticticboom.mods.mm.structure.layout;

import com.google.gson.JsonObject;
import io.ticticboom.mods.mm.client.structure.GuiStructureLayoutPiece;
import io.ticticboom.mods.mm.port.IPortBlockEntity;
import io.ticticboom.mods.mm.port.MMPortRegistry;
import io.ticticboom.mods.mm.structure.StructureModel;
import io.ticticboom.mods.mm.util.ParserUtils;
import lombok.Getter;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryManager;
import net.minecraftforge.registries.tags.ITag;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class StructureLayoutPiece {
    private Predicate<StructurePiecePredicateParams> predicate;
    @Getter
    private GuiStructureLayoutPiece guiPiece;
    @Getter
    private final String valueId;

    public StructureLayoutPiece(final Predicate<StructurePiecePredicateParams> predicate, GuiStructureLayoutPiece guiPiece, String valueId) {
        this.predicate = predicate;
        this.guiPiece = guiPiece;
        this.valueId = valueId;
    }

    public boolean formed(Level level, BlockPos pos, StructureModel model) {
        return predicate.test(new StructurePiecePredicateParams(level, pos));
    }

    public static StructureLayoutPiece parse(JsonObject json, ResourceLocation structureId) {
        // if block
        if (json.has("block")) {
            var requiredBlockId = ParserUtils.parseId(json, "block");
            var requiredBlock = ForgeRegistries.BLOCKS.getValue(requiredBlockId);
            if (requiredBlock == null) {
                throw new RuntimeException(String.format("Structure failed to parse [%s] Block not found [%s]", structureId, requiredBlockId));
            }
            return new StructureLayoutPiece(x -> {
                var state = x.level().getBlockState(x.pos());
                return state.is(requiredBlock);
            }, new GuiStructureLayoutPiece(() -> List.of(requiredBlock), Component.literal("Block: ").append(Component.literal(requiredBlockId.toString()).withStyle(ChatFormatting.DARK_AQUA))), "block:" + requiredBlockId);
        }
        // if tag
        if (json.has("tag")) {
            ResourceLocation requiredTagId = ParserUtils.parseId(json, "tag");
            var requiredTag = BlockTags.create(requiredTagId);
            return new StructureLayoutPiece(x -> {
                var state = x.level().getBlockState(x.pos());
                return state.is(requiredTag);
            }, new GuiStructureLayoutPiece(() -> ForgeRegistries.BLOCKS.tags().getTag(requiredTag).stream().toList(), Component.literal("Block Tag: ").append(Component.literal(requiredTagId.toString()).withStyle(ChatFormatting.DARK_AQUA))), "tag:" + requiredTagId);
        }
        if (json.has("portType")) {
            var portTypeId = ParserUtils.parseId(json, "portType");
            return new StructureLayoutPiece(x -> {
                var be = x.level().getBlockEntity(x.pos());
                if (be == null) {
                    return false;
                }
                if (be instanceof IPortBlockEntity pbe) {
                    return pbe.getModel().type().equals(portTypeId);
                }
                return false;
            }, new GuiStructureLayoutPiece(() -> MMPortRegistry.PORTS.stream().filter(x -> x.getRegistryId().equals(portTypeId))
                        .map(x -> x.getBlock().get()).toList(), Component.literal("Port Type: ").append(Component.literal(portTypeId.toString()).withStyle(ChatFormatting.DARK_AQUA))), "portType:" + portTypeId);
        }
        // if specific port
        if (json.has("port")) {
            final boolean requiresIO;
            final boolean isInput;
            if (json.has("input")) {
                requiresIO = true;
                isInput = json.get("input").getAsBoolean();
            } else {
                requiresIO = false;
                isInput = false;
            }
            final var portId = ParserUtils.parseId(json, "port");
            return new StructureLayoutPiece(x -> {
                var be = x.level().getBlockEntity(x.pos());
                if (be == null) {
                    return false;
                }
                if (be instanceof IPortBlockEntity pbe) {
                    var isPort = pbe.getModel().id().equals(portId.getPath());
                    if (!isPort) {
                        return false;
                    }
                    if (requiresIO) {
                        return pbe.isInput() == isInput;
                    }
                    return true;
                }
                return false;
            }, new GuiStructureLayoutPiece(() -> MMPortRegistry.getPortBlocks(portId),
                    Component.literal("Block Tag: ").append(Component.literal(portId.toString()).withStyle(ChatFormatting.DARK_AQUA))), "port:" + portId);
        }
        throw new RuntimeException(String.format("Structure failed to parse [%s] no suitable structure key requirement found", structureId));
    }
}
