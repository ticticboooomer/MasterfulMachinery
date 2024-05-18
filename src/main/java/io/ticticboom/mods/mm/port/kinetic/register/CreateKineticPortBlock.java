package io.ticticboom.mods.mm.port.kinetic.register;

import com.simibubi.create.content.kinetics.base.KineticBlock;
import com.simibubi.create.foundation.block.IBE;
import io.ticticboom.mods.mm.Ref;
import io.ticticboom.mods.mm.datagen.provider.MMBlockstateProvider;
import io.ticticboom.mods.mm.model.PortModel;
import io.ticticboom.mods.mm.port.IPortBlock;
import io.ticticboom.mods.mm.setup.RegistryGroupHolder;
import io.ticticboom.mods.mm.util.BlockUtils;
import io.ticticboom.mods.mm.util.PortUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class CreateKineticPortBlock extends KineticBlock implements IPortBlock, IBE<CreateKineticGenPortBlockEntity> {

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

    @Override
    public Class<CreateKineticGenPortBlockEntity> getBlockEntityClass() {
        return CreateKineticGenPortBlockEntity.class;
    }

    @Override
    public BlockEntityType<? extends CreateKineticGenPortBlockEntity> getBlockEntityType() {
        return (BlockEntityType<? extends CreateKineticGenPortBlockEntity>)groupHolder.getBe().get();
    }
}
