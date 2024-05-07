package io.ticticboom.mods.mm.menu;

import io.ticticboom.mods.mm.setup.RegistryGroupHolder;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.jetbrains.annotations.Nullable;

public abstract class MMContainerMenu extends AbstractContainerMenu {

    private final MenuType<?> type;
    private final Block block;
    private final ContainerLevelAccess access;
    private final int storageSlots;

    protected MMContainerMenu(MenuType<?> type, Block block, int p_38852_, ContainerLevelAccess access, int storageSlots) {
        super(type, p_38852_);
        this.type = type;
        this.block = block;
        this.access = access;
        this.storageSlots = storageSlots;
    }

    @Override
    public ItemStack quickMoveStack(Player player, int i) {
        ItemStack quickMovedStack = ItemStack.EMPTY;
        Slot quickMovedSlot = this.slots.get(i);
        if (quickMovedSlot != null && quickMovedSlot.hasItem()) {
            ItemStack rawStack = quickMovedSlot.getItem();
            quickMovedStack = rawStack.copy();

            int capSize = storageSlots;
            int pinvSize = 27;
            int phbSize = 9;
            int hbStart = capSize + pinvSize;
            int totalSize = capSize + pinvSize + phbSize;

            if (i >= capSize && i < totalSize) {
                if (!this.moveItemStackTo(rawStack, 0, capSize, false)) {
                    if (i < hbStart) {
                        if (!this.moveItemStackTo(rawStack, hbStart, totalSize, false)) {
                            return ItemStack.EMPTY;
                        }
                    } else if (!this.moveItemStackTo(rawStack, capSize, hbStart, false)) {
                        return ItemStack.EMPTY;
                    }
                }
            } else if (!this.moveItemStackTo(rawStack, capSize, totalSize, false)) {
                return ItemStack.EMPTY;
            }
            if (rawStack.isEmpty()) {
                quickMovedSlot.set(ItemStack.EMPTY);
            } else {
                quickMovedSlot.setChanged();
            }
            if (rawStack.getCount() == quickMovedStack.getCount()) {
                return ItemStack.EMPTY;
            }
            quickMovedSlot.onTake(player, rawStack);
        }

        return quickMovedStack;
    }

    @Override
    public boolean stillValid(Player player) {
        return stillValid(this.access, player, block);
    }
}
