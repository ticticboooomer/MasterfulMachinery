package io.ticticboom.mods.mm.port.item;

import io.ticticboom.mods.mm.port.IPortIngredient;
import io.ticticboom.mods.mm.port.IPortStorage;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;

import java.util.List;

public class ItemPortIngredient implements IPortIngredient {

    private final ResourceLocation itemId;
    private final int count;

    public ItemPortIngredient(ResourceLocation itemId, int count) {

        this.itemId = itemId;
        this.count = count;
    }

    @Override
    public boolean canProcess(Level level, List<IPortStorage> inputStorages) {

    }

    @Override
    public void process(Level level, List<IPortStorage> inputStorages) {

    }
}
