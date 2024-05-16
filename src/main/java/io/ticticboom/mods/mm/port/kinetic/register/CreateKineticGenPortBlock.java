package io.ticticboom.mods.mm.port.kinetic.register;

import com.simibubi.create.content.kinetics.base.KineticBlock;
import io.ticticboom.mods.mm.datagen.provider.MMBlockstateProvider;
import io.ticticboom.mods.mm.model.PortModel;
import io.ticticboom.mods.mm.port.IPortBlock;
import io.ticticboom.mods.mm.setup.RegistryGroupHolder;
import io.ticticboom.mods.mm.util.BlockUtils;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;

public class CreateKineticGenPortBlock extends KineticBlock implements IPortBlock {

    private final PortModel model;
    private final RegistryGroupHolder groupHolder;

    public CreateKineticGenPortBlock(PortModel model, RegistryGroupHolder groupHolder) {
        super(BlockUtils.createBlockProperties());
        this.model = model;
        this.groupHolder = groupHolder;
    }

    @Override
    public Direction.Axis getRotationAxis(BlockState blockState) {
        return Direction.Axis.Y;
    }

    @Override
    public void generateModel(MMBlockstateProvider provider) {

    }

    @Override
    public PortModel getModel() {
        return model;
    }
}
