package io.ticticboom.mods.mm.port.kinetic.register;

import com.simibubi.create.content.kinetics.base.KineticBlock;
import io.ticticboom.mods.mm.Ref;
import io.ticticboom.mods.mm.datagen.provider.MMBlockstateProvider;
import io.ticticboom.mods.mm.model.PortModel;
import io.ticticboom.mods.mm.port.IPortBlock;
import io.ticticboom.mods.mm.setup.RegistryGroupHolder;
import io.ticticboom.mods.mm.util.BlockUtils;
import io.ticticboom.mods.mm.util.PortUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class CreateKineticPortBlock extends KineticBlock implements IPortBlock, EntityBlock {

    private final PortModel model;
    private final RegistryGroupHolder groupHolder;

    public CreateKineticPortBlock(PortModel model, RegistryGroupHolder groupHolder) {
        super(BlockUtils.createBlockProperties());
        this.model = model;
        this.groupHolder = groupHolder;
    }

    @Override
    public boolean hasShaftTowards(LevelReader world, BlockPos pos, BlockState state, Direction face) {
        return true;
    }

    @Override
    public Direction.Axis getRotationAxis(BlockState blockState) {
        return Direction.Axis.X;
    }

    @Override
    public void generateModel(MMBlockstateProvider provider) {
        PortUtils.commonGenerateModel(provider, groupHolder, model.input(),
                Ref.Textures.INPUT_KINETIC_PORT_OVERLAY, Ref.Textures.OUTPUT_KINETIC_PORT_OVERLAY);
    }

    @Override
    public PortModel getModel() {
        return model;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return groupHolder.getBe().get().create(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level p_153212_, BlockState p_153213_, BlockEntityType<T> p_153214_) {
        return (Level p_155253_, BlockPos p_155254_, BlockState p_155255_, T be) -> {
            if (be instanceof CreateKineticGenPortBlockEntity kbe) {
                kbe.tick();
            }
        };
    }
}
