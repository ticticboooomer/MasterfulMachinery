package io.ticticboom.mods.mm.ports.item;

import com.google.gson.JsonObject;
import io.ticticboom.mods.mm.Ref;
import io.ticticboom.mods.mm.ports.base.*;
import net.minecraft.resources.ResourceLocation;

public class ItemPortTypeEntry extends MMPortTypeEntry {
    @Override
    public ResourceLocation id() {
        return Ref.res("item");
    }

    @Override
    public Class<? extends PortStorage> storageClass() {
        return ItemPortStorage.class;
    }

    @Override
    public IConfiguredPort parse(JsonObject element) {
        var rows = element.get("slotRows").getAsInt();
        var cols = element.get("slotCols").getAsInt();
        return new ItemConfiguredPort(rows, cols);
    }

    @Override
    public IConfiguredIngredient parseIngredient(JsonObject json) {
        var item = ResourceLocation.tryParse(json.get("item").getAsString());
        var count = json.get("count").getAsInt();
        return new ItemConfiguredIngredient(item, count);
    }

    @Override
    public ResourceLocation overlay(boolean input) {
        return input ? Ref.res("block/base_ports/item_input_cutout") : Ref.res("block/base_ports/item_output_cutout");
    }

    @Override
    public PortStorage createStorage(IConfiguredPort config) {
        return new ItemPortStorage(((ItemConfiguredPort) config));
    }

    @Override
    public void calculateIngredients(IConfiguredIngredient ingredient, PortStorage storage, IIngredientContext context) {
        var itemStorage = (ItemPortStorage) storage;
        var item = (ItemConfiguredIngredient) ingredient;
        var ctx = (ItemIngredientContext) context;
        var itemCounter = 0;
        for (int i = 0; i < itemStorage.items.getSlots(); i++) {
            var slot = itemStorage.items.getStackInSlot(i);
            if (slot.getItem().getRegistryName().equals(item.item())) {
                itemCounter += slot.getCount();
            }
        }
        ctx.counter += itemCounter;
    }

    @Override
    public IIngredientContext createIngredientContext(IConfiguredIngredient ingredient) {
        var ing = (ItemConfiguredIngredient) ingredient;
        return new ItemIngredientContext(ing.item());
    }

    @Override
    public boolean validateIngredientContext(IConfiguredIngredient ingredient, IIngredientContext context) {
        var item = (ItemConfiguredIngredient) ingredient;
        var ctx = (ItemIngredientContext) context;
        return ctx.item.equals(item.item()) && ctx.counter >= item.count();
    }
}
