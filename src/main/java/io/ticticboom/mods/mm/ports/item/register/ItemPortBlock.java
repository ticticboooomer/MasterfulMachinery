package io.ticticboom.mods.mm.ports.item.register;

import io.ticticboom.mods.mm.Ref;
import io.ticticboom.mods.mm.datagen.provider.MMBlockstateProvider;
import io.ticticboom.mods.mm.model.config.PortModel;
import io.ticticboom.mods.mm.ports.IPortBlock;
import io.ticticboom.mods.mm.setup.RegistryGroupHolder;
import io.ticticboom.mods.mm.util.BlockUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ItemPortBlock extends Block implements IPortBlock, EntityBlock {
    private final PortModel model;
    private final RegistryGroupHolder groupHolder;
    private final boolean isInput;

    public ItemPortBlock(PortModel model, RegistryGroupHolder groupHolder, boolean isInput) {
        super(Properties.of().requiresCorrectToolForDrops().destroyTime(5f).explosionResistance(6f).sound(SoundType.METAL));
        this.model = model;
        this.groupHolder = groupHolder;
        this.isInput = isInput;
    }

    @Override
    public @NotNull InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        return BlockUtils.commonUse(state, level, pos, player, hand, hitResult, ItemPortBlockEntity.class);
    }

    @Override
    public PortModel getModel() {
        return model;
    }

    @Override
    public void generateModel(MMBlockstateProvider provider) {
        if (isInput) {
            provider.dynamicBlock(groupHolder.getBlock().getId(), Ref.Textures.BASE_BLOCK, Ref.Textures.INPUT_ITEM_PORT_OVERLAY);
        } else {
            provider.dynamicBlock(groupHolder.getBlock().getId(), Ref.Textures.BASE_BLOCK, Ref.Textures.OUTPUT_ITEM_PORT_OVERLAY);
        }
        provider.simpleBlock(groupHolder.getBlock().get());
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return groupHolder.getBe().get().create(blockPos, blockState);
    }
}
