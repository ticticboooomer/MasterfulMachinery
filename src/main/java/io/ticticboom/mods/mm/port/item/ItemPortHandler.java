package io.ticticboom.mods.mm.port.item;

import java.util.List;

import com.mojang.serialization.Codec;

import io.ticticboom.mods.mm.Ref;
import io.ticticboom.mods.mm.port.common.INotifyChangeFunction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;

public class ItemPortHandler extends ItemStackHandler {

    public static Codec<List<ItemStack>> STACKS_CODEC = Codec.list(ItemStack.CODEC);
    private final INotifyChangeFunction changed;

    public ItemPortHandler(int size, INotifyChangeFunction changed) {
        super(size);
        this.changed = changed;
    }

    public NonNullList<ItemStack> getStacks() {
        return stacks;
    }

    public Tag serializeStacks() {
        var tag = NbtOps.INSTANCE.withEncoder(STACKS_CODEC).apply(stacks);
        return tag.getOrThrow(false, Ref.LOG::error);
    }

    public void deserializeStacks(Tag nbt) {
        var res = NbtOps.INSTANCE.withDecoder(STACKS_CODEC).apply(nbt);
        var pair = res.getOrThrow(false, Ref.LOG::error);
        this.stacks.clear();
        List<ItemStack> list = pair.getFirst();
        for (int i = 0; i < list.size(); i++) {
            stacks.set(i, list.get(i));
        }
    }

    @Override
    protected void onContentsChanged(int slot) {
        changed.call();
    }
}
