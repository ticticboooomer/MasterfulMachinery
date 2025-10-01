package io.ticticboom.mods.mm.port.item;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import io.ticticboom.mods.mm.Ref;
import io.ticticboom.mods.mm.port.IPortIngredient;
import io.ticticboom.mods.mm.recipe.RecipeStateModel;
import io.ticticboom.mods.mm.recipe.RecipeStorages;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.List;
import java.util.function.Predicate;

public abstract class BaseItemPortIngredient implements IPortIngredient {

    protected final int count;
    protected final Predicate<ItemStack> filter;

    public BaseItemPortIngredient(int count, Predicate<ItemStack> filter) {
        this.count = count;
        this.filter = filter;
    }

    @Override
    public boolean canProcess(Level level, RecipeStorages storages, RecipeStateModel state) {
        if(storages == null) return false;
        List<ItemPortStorage> itemStorages = storages.getInputStorages(ItemPortStorage.class);
        int remaining = count;
        for (ItemPortStorage storage : itemStorages) {
            remaining = storage.canExtract(filter, remaining);
        }
        return remaining <= 0;
    }

    @Override
    public void process(Level level, RecipeStorages storages, RecipeStateModel state) {
        List<ItemPortStorage> itemStorages = storages.getInputStorages(ItemPortStorage.class);
        int remaining = count;
        for (ItemPortStorage storage : itemStorages) {
            remaining = storage.extract(filter, remaining);
        }
    }

    @Override
    public JsonObject debugInput(Level level, RecipeStorages storages, JsonObject json) {
        List<ItemPortStorage> itemStorages = storages.getInputStorages(ItemPortStorage.class);
        var searchedStorages = new JsonArray();
        var searchIterations = new JsonArray();
        json.addProperty("ingredientType", Ref.Ports.ITEM.toString());
        json.addProperty("amountToExtract", count);

        int remaining = count;
        for (ItemPortStorage storage : itemStorages) {
            var iterJson = new JsonObject();

            remaining = storage.canExtract(this.filter, remaining);

            iterJson.addProperty("remaining", remaining);
            iterJson.addProperty("storageUid", storage.getStorageUid().toString());
            searchIterations.add(iterJson);
            searchedStorages.add(storage.getStorageUid().toString());
        }
        json.add("extractIterations", searchIterations);
        json.addProperty("canRun", remaining <= 0);
        json.add("searchedStorages", searchedStorages);
        return json;
    }
}
