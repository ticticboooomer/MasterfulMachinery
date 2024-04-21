package io.ticticboom.mods.mm.util;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;

public class MenuUtils {

    public static ContainerLevelAccess createAccessFromBlockEntity(BlockEntity be) {
        return ContainerLevelAccess.create(be.getLevel(), be.getBlockPos());
    }
}
