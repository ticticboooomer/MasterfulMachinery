package io.ticticboom.mods.mm.block.entity;

import io.ticticboom.mods.mm.block.ControllerBlock;
import io.ticticboom.mods.mm.setup.MMRegistries;
import io.ticticboom.mods.mm.setup.model.StructureModel;
import io.ticticboom.mods.mm.setup.reload.StructureManager;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Map;

public class ControllerBlockEntity extends BlockEntity {
    public ControllerBlockEntity(BlockEntityType<?> p_155228_, BlockPos p_155229_, BlockState p_155230_) {
        super(p_155228_, p_155229_, p_155230_);
    }

    public static <T extends BlockEntity> void tick(Level level, BlockPos blockPos, BlockState blockState, T t) {
        if (level.isClientSide) {
            return;
        }
        var be = ((ControllerBlockEntity) t);
        var block = (ControllerBlock)blockState.getBlock();

        for (Map.Entry<ResourceLocation, StructureModel> entry : StructureManager.REGISTRY.entrySet()) {
            var model = entry.getValue();
            if (!model.controllerId().equals(block.model().id())) {
                continue;
            }
            boolean found = true;
            for (StructureModel.PlacedStructurePart placed : model.flattened()) {
                var part = MMRegistries.STRUCTURE_PARTS.getValue(placed.partId());
                assert part != null;
                if (!part.validatePlacement(level, blockPos.offset(placed.pos()), placed.part())) {
                    found = false;
                    break;
                }
            }
            if (found) {
                // structure found
            }
        }
    }
}
