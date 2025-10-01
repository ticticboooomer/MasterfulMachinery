package io.ticticboom.mods.mm.util;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.Nullable;

import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class BlockUtils {
    public static <T extends MenuProvider> InteractionResult commonUse(BlockState state, Level level, BlockPos pos,
                                                                       Player player, InteractionHand hand, BlockHitResult hitResult, Class<T> clz, @Nullable Supplier<Boolean> preScreenCheck) {
        if (preScreenCheck == null) {
            preScreenCheck = () -> true;
        }
        if (!level.isClientSide() && hand == InteractionHand.MAIN_HAND) {
            var be = WorldUtil.getBlockEntity(pos, (ServerLevel) level);
            if (clz.isAssignableFrom(be.getClass())) {
                if (preScreenCheck.get()) {
                    NetworkHooks.openScreen((ServerPlayer) player, (T) be, pos);
                }
            }
        }
        return InteractionResult.sidedSuccess(level.isClientSide);
    }

    public static void setupPlayerInventory(AbstractContainerMenu container, Inventory inv, int xOffset, int yOffset) {
        int playerOffsetX = 8 + xOffset;
        int playerOffsetY = 141 + yOffset;
        for (int j = 0; j < 3; j++) {
            for (int i = 0; i < 9; i++) {
                container.addSlot(new Slot(inv, 9 + (j * 9 + i), i * 18 + playerOffsetX, j * 18 + playerOffsetY));
            }
        }

        for (int i = 0; i < 9; i++) {
            container.addSlot(new Slot(inv, i, 8 + (i * 18) + xOffset, 199 + yOffset));
        }
    }

    public static BlockBehaviour.Properties createBlockProperties() {
        return BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).requiresCorrectToolForDrops().strength(5f, 5f)
                .sound(SoundType.METAL);
    }
}
