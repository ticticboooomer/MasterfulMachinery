package io.ticticboom.mods.mm.ports.createrotation.block;

import com.simibubi.create.content.contraptions.base.KineticBlock;
import com.simibubi.create.content.contraptions.base.KineticTileEntity;
import com.simibubi.create.content.contraptions.relays.encased.AbstractEncasedShaftBlock;
import com.simibubi.create.content.contraptions.relays.gearbox.GearboxBlock;
import io.ticticboom.mods.mm.ports.base.IPortBlock;
import io.ticticboom.mods.mm.setup.model.PortModel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.Nullable;

public class CreateRotationPortBlock extends KineticBlock implements IPortBlock, EntityBlock {
    private final PortModel model;
    private RegistryObject<BlockEntityType<?>> blockEntityType;

    public CreateRotationPortBlock(PortModel model, RegistryObject<MenuType<?>> menuType, RegistryObject<BlockEntityType<?>> blockEntityType) {
        super(Properties.of(Material.METAL));
        this.model = model;
        this.blockEntityType = blockEntityType;
    }

    @Override
    public boolean hasShaftTowards(LevelReader world, BlockPos pos, BlockState state, Direction face) {
        return true;
    }

    @Override
    public PortModel model() {
        return model;
    }

    @Override
    public Direction.Axis getRotationAxis(BlockState state) {
        return Direction.Axis.Y;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos p_153215_, BlockState p_153216_) {
        return blockEntityType.get().create(p_153215_, p_153216_);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return type == blockEntityType.get() ? (a, b, c, d) -> ((KineticTileEntity) d).tick() : null;
    }
}
