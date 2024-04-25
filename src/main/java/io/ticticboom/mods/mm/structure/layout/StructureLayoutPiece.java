package io.ticticboom.mods.mm.structure.layout;

import com.google.gson.JsonObject;
import io.ticticboom.mods.mm.ports.IPortBlockEntity;
import io.ticticboom.mods.mm.structure.StructureModel;
import io.ticticboom.mods.mm.util.ParserUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Predicate;

public class StructureLayoutPiece {
    private Predicate<StructurePiecePredicateParams> predicate;

    public StructureLayoutPiece(final Predicate<StructurePiecePredicateParams> predicate) {
        this.predicate = predicate;
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
            });
        }
        // if tag
        if (json.has("tag")) {
            ResourceLocation requiredTagId = ParserUtils.parseId(json, "tag");
            var requiredTag = BlockTags.create(requiredTagId);
            return new StructureLayoutPiece(x -> {
                var state = x.level().getBlockState(x.pos());
                return state.is(requiredTag);
            });
        }
        // if port type
        if (json.has("portType")) {
            var portTypeId = ParserUtils.parseId(json, "port");
            return new StructureLayoutPiece(x -> {
                var be = x.level().getBlockEntity(x.pos());
                if (be == null) {
                    return false;
                }
                if (be instanceof IPortBlockEntity pbe) {
                    return pbe.getPortModel().type().equals(portTypeId);
                }
                return false;
            });
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
                    var isPort = pbe.getPortModel().id().equals(portId.getPath());
                    if (!isPort) {
                        return false;
                    }
                    if (requiresIO) {
                        return pbe.isInput() == isInput;
                    }
                    return true;
                }
                return false;
            });
        }
        throw new RuntimeException(String.format("Structure failed to parse [%s] no suitable structure key requirement found", structureId));
    }
}
