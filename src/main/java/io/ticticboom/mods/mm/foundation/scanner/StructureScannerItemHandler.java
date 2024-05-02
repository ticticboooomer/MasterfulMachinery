package io.ticticboom.mods.mm.foundation.scanner;

import io.ticticboom.mods.mm.port.common.INotifyChangeFunction;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;

public class StructureScannerItemHandler extends ItemStackHandler {
    private final INotifyChangeFunction changed;

    public StructureScannerItemHandler(int slots, INotifyChangeFunction changed) {
        super(slots);
        this.changed = changed;
    }

    public NonNullList<ItemStack> getStacks() {
        return this.stacks;
    }

    @Override
    protected void onContentsChanged(int slot) {
        changed.call();
    }
}
