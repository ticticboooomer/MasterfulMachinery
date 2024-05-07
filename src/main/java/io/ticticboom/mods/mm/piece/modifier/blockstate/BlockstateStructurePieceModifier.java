package io.ticticboom.mods.mm.piece.modifier.blockstate;

import com.google.gson.JsonObject;
import com.mojang.serialization.JsonOps;
import io.ticticboom.mods.mm.Ref;
import io.ticticboom.mods.mm.piece.StructurePieceSetupMetadata;
import io.ticticboom.mods.mm.piece.modifier.StructurePieceModifier;
import io.ticticboom.mods.mm.structure.StructureModel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.Property;

import java.util.*;

public class BlockstateStructurePieceModifier extends StructurePieceModifier {

    private final List<StructureBlockstateProperty> properties;
    private final Map<String, Property.Value<?>> propValues = new HashMap<>();

    public BlockstateStructurePieceModifier(List<StructureBlockstateProperty> properties) {
        this.properties = properties;
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
        BlockState blockState = level.getBlockState(pos);
        int matched = 0;
        for (Map.Entry<Property<?>, Comparable<?>> entry : blockState.getValues().entrySet()) {
            if (doesPropValueMatch(entry, rotation)) {
                matched++;
            }
        }
        return matched >= properties.size();
    }

    @Override
    public BlockState modifyBlockState(BlockState state, BlockEntity be, BlockPos pos) {
        return modifyBlockstate(state);
    }

    @Override
    public BlockEntity modifyBlockEntity(BlockState state, BlockEntity be, BlockPos pos) {
        return be;
    }

    private boolean doesPropValueMatch(Map.Entry<Property<?>, Comparable<?>> entry, Rotation rot) {
        for (StructureBlockstateProperty property : properties) {
            var nameMatch = entry.getKey().getName().equals(property.key());
            if (!nameMatch) {
                continue;
            }
            // make cleaner ffs
            var propVal = propValues.get(property.key());
            if (entry.getKey() instanceof DirectionProperty dp) {
                Optional<Direction> value = dp.getValue(property.value().getAsString());
                if (value.isPresent()) {
                    var rotDir = rot.rotate(value.get());
                    propVal = dp.value(rotDir);
                }
            }
            if (entry.getKey().getName().toLowerCase(Locale.ROOT).equals("axis")) {
                String axis = property.value().getAsString();
                if (rot == Rotation.CLOCKWISE_90 || rot == Rotation.COUNTERCLOCKWISE_90) {
                    if (axis.equals("z")) {
                        propVal = ((EnumProperty<Direction.Axis>) entry.getKey()).value(Direction.Axis.X);
                    } else if (axis.equals("x")) {
                        propVal = ((EnumProperty<Direction.Axis>) entry.getKey()).value(Direction.Axis.Z);
                    }
                }
            }
            boolean valMatch = propVal.value().equals(entry.getValue());
            if (valMatch) {
                return true;
            }
        }
        return false;
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