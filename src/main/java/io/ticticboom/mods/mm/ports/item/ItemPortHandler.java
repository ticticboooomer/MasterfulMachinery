package io.ticticboom.mods.mm.ports.item;

import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;

public class ItemPortHandler extends ItemStackHandler {

    public ItemPortHandler(int size) {
        super(size);
    }

    public NonNullList<ItemStack> getStacks() {
        return stacks;
    }
}
