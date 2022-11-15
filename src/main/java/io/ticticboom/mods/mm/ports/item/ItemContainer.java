package io.ticticboom.mods.mm.ports.item;

import net.minecraft.core.NonNullList;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;

public class ItemContainer implements Container {

    public final ItemStackHandler handler;

    public ItemContainer(ItemStackHandler handler) {
        this.handler = handler;
    }

    @Override
    public int getContainerSize() {
        return handler.getSlots();
    }

    @Override
    public boolean isEmpty() {
        for (int i = 0; i < handler.getSlots(); i++) {
            if (!handler.getStackInSlot(i).isEmpty())  {
                return false;
            }
        }
        return true;
    }

    @Override
    public ItemStack getItem(int p_18941_) {
        return handler.getStackInSlot(p_18941_);
    }

    @Override
    public ItemStack removeItem(int slot, int amount) {
        return handler.extractItem(slot, amount, false);
    }

    @Override
    public ItemStack removeItemNoUpdate(int p_18951_) {
        return handler.extractItem(p_18951_, handler.getStackInSlot(p_18951_).getCount(), true);
    }

    @Override
    public void setItem(int p_18944_, ItemStack p_18945_) {
        handler.setStackInSlot(p_18944_, p_18945_);
    }

    @Override
    public void setChanged() {
    }

    @Override
    public boolean stillValid(Player p_18946_) {
        return true;
    }

    @Override
    public void clearContent() {
        for (int i = 0; i < handler.getSlots(); i++) {
            handler.setStackInSlot(i, ItemStack.EMPTY);
        }
    }
}
