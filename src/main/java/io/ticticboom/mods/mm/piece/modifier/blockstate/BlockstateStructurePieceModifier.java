package io.ticticboom.mods.mm.piece.modifier.blockstate;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.mojang.serialization.JsonOps;
import io.ticticboom.mods.mm.Ref;
import io.ticticboom.mods.mm.piece.StructurePieceSetupMetadata;
import io.ticticboom.mods.mm.piece.modifier.StructurePieceModifier;
import io.ticticboom.mods.mm.structure.StructureModel;
import io.ticticboom.mods.mm.util.WorldUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.Property;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class BlockstateStructurePieceModifier extends StructurePieceModifier {

    private final String id;
    private final List<StructureBlockstateProperty> properties;
    private final Map<String, Property.Value<?>> propValues = new HashMap<>();

    public BlockstateStructurePieceModifier(String id, List<StructureBlockstateProperty> properties) {
        this.id = id;
        this.properties = properties;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void validateSetup(StructurePieceSetupMetadata meta, List<Block> requiredBlocks) {
        for (Block requiredBlock : requiredBlocks) {
            StateDefinition<Block, BlockState> stateDefinition = requiredBlock.getStateDefinition();
            for (StructureBlockstateProperty property : properties) {
                Property<?> prop = stateDefinition.getProperty(property.key());
                var val = JsonOps.INSTANCE.withDecoder(prop.valueCodec()).apply(property.value()).getOrThrow(false, RuntimeException::new).getFirst();
                propValues.put(prop.getName(), val);
            }
        }
    }

    @Override
    public boolean formed(Level level, BlockPos pos, StructureModel model, Rotation rotation) {
        BlockState blockState = WorldUtil.getBlockState(pos, (ServerLevel) level);
        var matched = new AtomicInteger();
        for (Property.Value<?> value : propValues.values()) {
            var prop = blockState.getOptionalValue(value.property());
            prop.ifPresent(v -> {
                if (v.equals(value.value())) {
                    matched.getAndIncrement();
                }
            });
        }
        return matched.get() >= properties.size();
    }

    @Override
    public BlockState modifyBlockState(BlockState state, BlockEntity be, BlockPos pos) {
        return modifyBlockstate(state);
    }

    @Override
    public BlockEntity modifyBlockEntity(BlockState state, BlockEntity be, BlockPos pos) {
        return be;
    }

    @Override
    public JsonObject debugExpected(Level level, BlockPos pos, StructureModel model, Rotation rotation, JsonObject json) {
        var propsJson = new JsonArray();
        for (StructureBlockstateProperty property : properties) {
            var propertyJson = new JsonObject();
            propertyJson.addProperty("property", property.key());
            propertyJson.add("value", property.value());
            propsJson.add(propertyJson);
        }
        return json;
    }

    @Override
    public JsonObject debugFound(Level level, BlockPos pos, StructureModel model, Rotation rotation, JsonObject json) {
        var blockstate = level.getBlockState(pos);
        var res = JsonOps.INSTANCE.withEncoder(BlockState.CODEC).apply(blockstate).result();
        if (res.isPresent()) {
            json.add("blockstate", res.get());
        } else {
            json.addProperty("failedToSerializeBlockState", true);
        }
        return json;
    }

    private BlockState modifyBlockstate(BlockState state) {
        var encoded = JsonOps.INSTANCE.withEncoder(BlockState.CODEC).apply(state).getOrThrow(false, RuntimeException::new);
        JsonObject jsonProps = encoded.getAsJsonObject().get(BlockState.PROPERTIES_TAG).getAsJsonObject();
        for (StructureBlockstateProperty property : properties) {
            jsonProps.add(property.key(), property.value());
        }
        Ref.LOG.info(encoded.toString());
        var result = JsonOps.INSTANCE.withDecoder(BlockState.CODEC).apply(encoded).getOrThrow(false, RuntimeException::new).getFirst();
        return result;
    }

}