package io.ticticboom.mods.mm.menu;

import io.ticticboom.mods.mm.setup.RegistryGroupHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public abstract class MMContainerMenu extends AbstractContainerMenu {

    private final ContainerLevelAccess access;
    private final RegistryGroupHolder groupHolder;

    protected MMContainerMenu(RegistryGroupHolder groupHolder, int p_38852_, ContainerLevelAccess access) {
        super(groupHolder.getMenu().get(), p_38852_);
        this.access = access;
        this.groupHolder = groupHolder;
    }

    @Override
    public ItemStack quickMoveStack(Player player, int i) {
        ItemStack quickMovedStack = ItemStack.EMPTY;
        Slot quickMovedSlot = this.slots.get(i);
        if (quickMovedSlot != null && quickMovedSlot.hasItem()) {
            ItemStack rawStack = quickMovedSlot.getItem();
            quickMovedStack = rawStack.copy();

            if (i == 0) {
                if (!this.moveItemStackTo(rawStack, 5, 41, true)) {
                    return ItemStack.EMPTY;
                }

                quickMovedSlot.onQuickCraft(rawStack, quickMovedStack);
            } else if (i >= 5 && i < 41) {
                if (!this.moveItemStackTo(rawStack, 1, 5, false)) {
                    if (i < 32) {
                        if (!this.moveItemStackTo(rawStack, 32, 41, false)) {
                            return ItemStack.EMPTY;
                        }
                    } else if (!this.moveItemStackTo(rawStack, 5, 32, false)) {
                        return ItemStack.EMPTY;
                    }
                }
            } else if (!this.moveItemStackTo(rawStack, 5, 41, false)) {
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
        return stillValid(this.access, player, groupHolder.getBlock().get());
    }
}
