package io.ticticboom.mods.mm.ports.item;

import io.ticticboom.mods.mm.ports.base.IIngredientContext;
import net.minecraft.resources.ResourceLocation;

public class ItemIngredientContext implements IIngredientContext {
    public final ResourceLocation item;
    public int counter = 0;

    public ItemIngredientContext(ResourceLocation item) {
        this.item = item;
    }
}
