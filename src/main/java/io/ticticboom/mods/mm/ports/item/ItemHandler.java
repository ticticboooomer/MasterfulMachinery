package io.ticticboom.mods.mm.ports.item;

import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;

public class ItemHandler extends ItemStackHandler {
    public ItemHandler(int i) {
        super(i);
    }

    public NonNullList<ItemStack> getStacks() {
        return stacks;
    }
}
