package io.ticticboom.mods.mm.piece.modifier.blockstate;

import com.google.gson.JsonObject;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.JsonOps;
import io.ticticboom.mods.mm.Ref;
import io.ticticboom.mods.mm.piece.StructurePieceSetupMetadata;
import io.ticticboom.mods.mm.piece.modifier.IStructurePieceModifier;
import io.ticticboom.mods.mm.structure.StructureModel;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.Property;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

public class BlockstateStructurePieceModifier implements IStructurePieceModifier {

    private final List<StructureBlockstateProperty> properties;
    private final Map<String, Property.Value<?>> propValues = new HashMap<>();

    public BlockstateStructurePieceModifier(List<StructureBlockstateProperty> properties) {
        this.properties = properties;
    }

    @Override
    public List<String> validateSetup(StructurePieceSetupMetadata meta, List<Block> requiredBlocks) {
        List<String> errors = new ArrayList<>();
        for (Block requiredBlock : requiredBlocks) {
            StateDefinition<Block, BlockState> stateDefinition = requiredBlock.getStateDefinition();
            try {
                for (StructureBlockstateProperty property : properties) {
                    Property<?> prop = stateDefinition.getProperty(property.key());
                    var val = JsonOps.INSTANCE.withDecoder(prop.valueCodec()).apply(property.value()).getOrThrow(false, errors::add).getFirst();
                    propValues.put(prop.getName(), val);
                    modifyBlockstate(requiredBlock);
                }
            } catch (Exception e) {
                errors.add(e.getMessage());
            }
        }
        return errors;
    }

    @Override
    public boolean formed(Level level, BlockPos pos, StructureModel model, Rotation rotation) {
        BlockState blockState = level.getBlockState(pos);
        for (Map.Entry<Property<?>, Comparable<?>> entry : blockState.getValues().entrySet()) {
            if (!doesPropValueMatch(entry)){
                return false;
            }
        }
        return true;
    }

    private boolean doesPropValueMatch(Map.Entry<Property<?>, Comparable<?>> entry) {
        for (StructureBlockstateProperty property : properties) {
            var nameMatch = entry.getKey().getName().equals(property.key());
            if (!nameMatch) {
                return false;
            }
            boolean valMatch = propValues.get(property.key()).equals(entry.getValue());
            if (!valMatch) {
                return false;
            }
        }
        return true;
    }

    private void modifyBlockstate(Block block) {
        BlockState blockState = block.defaultBlockState();
        var encoded = JsonOps.INSTANCE.withEncoder(BlockState.CODEC).apply(blockState).getOrThrow(false,RuntimeException::new);
        Ref.LOG.info(encoded.toString());
    }
}