package io.ticticboom.mods.mm.port.item.register;

import io.ticticboom.mods.mm.Ref;
import io.ticticboom.mods.mm.datagen.provider.MMBlockstateProvider;
import io.ticticboom.mods.mm.model.PortModel;
import io.ticticboom.mods.mm.port.IPortBlock;
import io.ticticboom.mods.mm.port.item.ItemPortHandler;
import io.ticticboom.mods.mm.port.item.ItemPortStorage;
import io.ticticboom.mods.mm.setup.RegistryGroupHolder;
import io.ticticboom.mods.mm.util.BlockUtils;
import io.ticticboom.mods.mm.util.PortUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.items.ItemHandlerHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ItemPortBlock extends Block implements IPortBlock, EntityBlock {
    private final PortModel model;
    private final RegistryGroupHolder groupHolder;
    private final boolean isInput;

    public ItemPortBlock(PortModel model, RegistryGroupHolder groupHolder, boolean isInput) {
        super(BlockUtils.createBlockProperties());
        this.model = model;
        this.groupHolder = groupHolder;
        this.isInput = isInput;
    }

    @Override
    public @NotNull InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        return BlockUtils.commonUse(state, level, pos, player, hand, hitResult, ItemPortBlockEntity.class, null);
    }

    @Override
    public PortModel getModel() {
        return model;
    }

    @Override
    public void generateModel(MMBlockstateProvider provider) {
        PortUtils.commonGenerateModel(provider, groupHolder, isInput, Ref.Textures.INPUT_ITEM_PORT_OVERLAY, Ref.Textures.OUTPUT_ITEM_PORT_OVERLAY);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return groupHolder.getBe().get().create(blockPos, blockState);
    }

    @Override
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean p_60519_) {
        var be = level.getBlockEntity(pos);
        if (be instanceof ItemPortBlockEntity pbe) {
            var storage = (ItemPortStorage) pbe.getStorage();
            var handler = storage.getHandler();
            Containers.dropContents(level, pos, handler.getStacks());
        }
        super.onRemove(state, level, pos, newState, p_60519_);
    }
}
