package io.ticticboom.mods.mm.ports.item;

import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class ItemPortContainer implements Container {

    private final ItemPortHandler handler;

    public ItemPortContainer(ItemPortHandler handler) {
        this.handler = handler;
    }

    @Override
    public int getContainerSize() {
        return handler.getSlots();
    }

    @Override
    public boolean isEmpty() {
        for (int i = 0; i < handler.getSlots(); i++) {
            if (!handler.getStackInSlot(i).isEmpty()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public ItemStack getItem(int i) {
       return handler.getStackInSlot(i);
    }

    @Override
    public ItemStack removeItem(int i, int i1) {
        var res = ContainerHelper.removeItem(handler.getStacks(), i, i1);
        if (!res.isEmpty()) {
            this.setChanged();
        }
        return res;
    }

    @Override
    public ItemStack removeItemNoUpdate(int i) {
        ItemStack stack = handler.getStacks().get(i);
        if (stack.isEmpty()) {
            return ItemStack.EMPTY;
        }
        handler.getStacks().set(i, ItemStack.EMPTY);
        return stack;
    }

    @Override
    public void setItem(int i, ItemStack itemStack) {
        handler.setStackInSlot(i, itemStack);
        if (!itemStack.isEmpty() && itemStack.getCount() > this.getMaxStackSize()) {
            itemStack.setCount(this.getMaxStackSize());
        }

        this.setChanged();
    }

    @Override
    public void setChanged() {

    }

    @Override
    public boolean stillValid(Player player) {
        return true;
    }

    @Override
    public void clearContent() {
        handler.getStacks().clear();
    }
}
