package io.ticticboom.mods.mm.client.structure;

import lombok.Getter;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;

import java.util.List;

@Getter
public class GuiCountedItemStack {

    private int count;
    private final List<ItemStack> stacks;
    private final Component detail;
    private final String valueId;

    public GuiCountedItemStack(int count, List<ItemStack> stacks, Component detail, String valueId) {

        this.count = count;
        this.stacks = stacks;
        this.detail = detail;
        this.valueId = valueId;
    }

    public void addCount(int toAdd) {
        count += toAdd;
    }
}
