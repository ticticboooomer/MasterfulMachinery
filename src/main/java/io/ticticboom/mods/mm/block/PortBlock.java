package io.ticticboom.mods.mm.block;

import io.ticticboom.mods.mm.block.entity.PortBlockEntity;
import io.ticticboom.mods.mm.client.container.PortMenuProvider;
import io.ticticboom.mods.mm.ports.base.IPortBE;
import io.ticticboom.mods.mm.ports.base.IPortBlock;
import io.ticticboom.mods.mm.setup.model.PortModel;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.Nullable;

public class PortBlock extends Block implements EntityBlock, IPortBlock {

    private final PortModel model;
    private RegistryObject<MenuType<?>> menuType;
    private RegistryObject<BlockEntityType<BlockEntity>> blockEntityType;

    public PortBlock(PortModel model, RegistryObject<MenuType<?>> menuType, RegistryObject<BlockEntityType<BlockEntity>> blockEntityType) {
        super(Properties.of(Material.METAL));
        this.model = model;
        this.menuType = menuType;
        this.blockEntityType = blockEntityType;
    }

    public PortModel model() {
        return model;
    }

    @Nullable
    @Override
    public MenuProvider getMenuProvider(BlockState state, Level level, BlockPos pos) {
        var be = (PortBlockEntity)level.getBlockEntity(pos);
        return new PortMenuProvider(be, menuType.get(), model);
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult result) {
        PortBlockEntity be = (PortBlockEntity)level.getBlockEntity(pos);
        var ires = be.storage.playerInteractWithItem(player, level, pos, hand);
        if (!ires.equals(InteractionResult.PASS)) {
            return ires;
        }
        if (!level.isClientSide && player instanceof ServerPlayer serverPlayer) {
            NetworkHooks.openScreen(serverPlayer, this.getMenuProvider(state, level, pos), pos);
        }
        return InteractionResult.sidedSuccess(level.isClientSide);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return blockEntityType.get().create(pos, state);
    }


    @Override
    public void onRemove(BlockState p_60515_, Level level, BlockPos pos, BlockState p_60518_, boolean p_60519_) {
        var port = ((IPortBE) level.getBlockEntity(pos));
        if (port == null) {
            return;
        }
        port.storage().onDestroy(level, pos);
        super.onRemove(p_60515_, level, pos, p_60518_, p_60519_);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return type == blockEntityType.get() ? PortBlockEntity::tick : null;
    }
}
