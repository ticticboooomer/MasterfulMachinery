package io.ticticboom.mods.mm.util;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.network.NetworkHooks;

public class BlockUtils {
    public static <T extends MenuProvider> InteractionResult commonUse(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult, Class<T> clz) {
        if (!level.isClientSide() && hand == InteractionHand.MAIN_HAND) {
            var be = level.getBlockEntity(pos);
            if (clz.isAssignableFrom(be.getClass())) {
                NetworkHooks.openScreen((ServerPlayer) player, (T)be, pos);
            }
        }
        return InteractionResult.sidedSuccess(level.isClientSide);
    }
}
