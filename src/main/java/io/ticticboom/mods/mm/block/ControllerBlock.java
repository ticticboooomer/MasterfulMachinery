package io.ticticboom.mods.mm.block;

import io.ticticboom.mods.mm.block.entity.ControllerBlockEntity;
import io.ticticboom.mods.mm.client.container.ControllerMenuProvider;
import io.ticticboom.mods.mm.setup.model.ControllerModel;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.TickingBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.Nullable;

public class ControllerBlock extends HorizontalDirectionalBlock implements EntityBlock {
    private final ControllerModel model;
    private RegistryObject<BlockEntityType<?>> blockEntityType;
    private RegistryObject<MenuType<?>> menuType;

    public ControllerBlock(ControllerModel model, RegistryObject<BlockEntityType<?>> blockEntityType, RegistryObject<MenuType<?>> menuType) {
        super(Properties.of(Material.METAL));
        this.model = model;
        this.blockEntityType = blockEntityType;
        this.menuType = menuType;
    }

    public ControllerModel model() {
        return model;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> p_49915_) {
        super.createBlockStateDefinition(p_49915_.add(FACING));
    }


    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        return this.defaultBlockState().setValue(FACING, ctx.getHorizontalDirection().getOpposite());
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return blockEntityType.get().create(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return type == blockEntityType.get() ? ControllerBlockEntity::tick : null;
    }

    @Override
    public MenuProvider getMenuProvider(BlockState state, Level level, BlockPos pos) {
        var be = level.getBlockEntity(pos);
        return new ControllerMenuProvider((ControllerBlockEntity) be, menuType.get());
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult result) {
        if (!level.isClientSide && player instanceof ServerPlayer serverPlayer){
            NetworkHooks.openGui(serverPlayer, this.getMenuProvider(state, level, pos), pos);
        }
        return InteractionResult.sidedSuccess(level.isClientSide);
    }

    @Override
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean p_60519_) {
        var be = (ControllerBlockEntity)level.getBlockEntity(pos);
        be.resetRecipe();
        super.onRemove(state, level, pos, newState, p_60519_);
    }
}
