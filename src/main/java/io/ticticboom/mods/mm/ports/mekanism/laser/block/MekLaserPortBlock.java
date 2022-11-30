package io.ticticboom.mods.mm.ports.mekanism.laser.block;

import io.ticticboom.mods.mm.block.PortBlock;
import io.ticticboom.mods.mm.ports.base.IPortBlock;
import io.ticticboom.mods.mm.setup.model.PortModel;
import mekanism.api.math.FloatingLong;
import mekanism.api.text.ILangEntry;
import mekanism.common.block.attribute.Attribute;
import mekanism.common.block.attribute.AttributeEnergy;
import mekanism.common.block.interfaces.IHasTileEntity;
import mekanism.common.block.interfaces.ITypeBlock;
import mekanism.common.content.blocktype.BlockType;
import mekanism.common.content.blocktype.BlockTypeTile;
import mekanism.common.registration.impl.TileEntityTypeRegistryObject;
import mekanism.common.registries.MekanismBlockTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEventListener;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class MekLaserPortBlock extends Block implements IHasTileEntity<BlockEntity>, IPortBlock, ITypeBlock {




    private PortModel model;
    private RegistryObject<MenuType<?>> menuType;
    private RegistryObject<BlockEntityType<BlockEntity>> blockEntityType;
    public BlockTypeTile<MekLaserInputPortBlockEntity> blockTypeTile;

    public MekLaserPortBlock(PortModel model, RegistryObject<MenuType<?>> menuType, RegistryObject<BlockEntityType<BlockEntity>> blockEntityType) {
        super(Properties.of(Material.METAL));
        this.model = model;
        this.menuType = menuType;
        this.blockEntityType = blockEntityType;
        blockTypeTile = new BlockTypeTile<>(() -> (TileEntityTypeRegistryObject<MekLaserInputPortBlockEntity>) getTileType(), () -> "tile.mm.laser_port." + model.id().getPath());
        blockTypeTile.add(new AttributeEnergy(() -> FloatingLong.create(0), () -> FloatingLong.MAX_VALUE));
    }

    @Override
    public TileEntityTypeRegistryObject<? extends BlockEntity> getTileType() {
        return new TileEntityTypeRegistryObject<>(blockEntityType);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos p_153215_, BlockState p_153216_) {
        return blockEntityType.get().create(p_153215_, p_153216_);
    }

    @Override
    public PortModel model() {
        return model;
    }

    @Override
    public BlockType getType() {
        return blockTypeTile;
    }
}
