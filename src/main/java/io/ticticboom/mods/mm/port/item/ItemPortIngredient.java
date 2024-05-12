package io.ticticboom.mods.mm.port.item;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import io.ticticboom.mods.mm.Ref;
import io.ticticboom.mods.mm.compat.jei.SlotGrid;
import io.ticticboom.mods.mm.compat.jei.ingredient.MMJeiIngredients;
import io.ticticboom.mods.mm.port.IPortIngredient;
import io.ticticboom.mods.mm.recipe.RecipeModel;
import io.ticticboom.mods.mm.recipe.RecipeStorages;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.builder.IRecipeSlotBuilder;
import mezz.jei.api.helpers.IJeiHelpers;
import mezz.jei.api.recipe.IFocusGroup;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;

public class ItemPortIngredient implements IPortIngredient {

    private final ResourceLocation itemId;
    private final int count;
    private final Item item;
    private final ItemStack stack;

    public ItemPortIngredient(ResourceLocation itemId, int count) {

        this.itemId = itemId;
        this.count = count;
        item = ForgeRegistries.ITEMS.getValue(itemId);
        if (item == null){
            throw new RuntimeException(String.format("Could not find item [%s] which is required by an MM recipe", itemId));
        }
        stack = new ItemStack(item, count);
    }

    @Override
    public boolean canProcess(Level level, RecipeStorages storages) {
        List<ItemPortStorage> itemStorages = storages.getInputStorages(ItemPortStorage.class);
        int remaining = count;
        for (ItemPortStorage storage : itemStorages) {
            remaining = storage.canExtract(item, remaining);
        }
        return remaining <= 0;
    }

    @Override
    public void process(Level level, RecipeStorages storages) {
        List<ItemPortStorage> itemStorages = storages.getInputStorages(ItemPortStorage.class);
        int remaining = count;
        for (ItemPortStorage storage : itemStorages) {
            remaining = storage.extract(item, remaining);
        }
    }

    @Override
    public boolean canOutput(Level level, RecipeStorages storages) {
        List<ItemPortStorage> itemStorages = storages.getOutputStorages(ItemPortStorage.class);
        int remainingToInsert = count;
        for (ItemPortStorage itemStorage : itemStorages) {
            remainingToInsert = itemStorage.canInsert(item, remainingToInsert);
        }
        return remainingToInsert <= 0;
    }

    @Override
    public void output(Level level, RecipeStorages storages) {
        List<ItemPortStorage> itemStorages = storages.getOutputStorages(ItemPortStorage.class);
        int remainingToInsert = count;
        for (ItemPortStorage itemStorage : itemStorages) {
            remainingToInsert = itemStorage.insert(item, remainingToInsert);
        }
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, RecipeModel model, IFocusGroup focus, IJeiHelpers helpers, SlotGrid grid, IRecipeSlotBuilder recipeSlot) {
        recipeSlot.addIngredient(MMJeiIngredients.ITEM, this.stack);
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

            remaining = storage.canExtract(item, remaining);

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

    @Override
    public JsonObject debugOutput(Level level, RecipeStorages storages, JsonObject json) {
        List<ItemPortStorage> itemStorages = storages.getOutputStorages(ItemPortStorage.class);
        var searchedStorages = new JsonArray();
        var searchIterations = new JsonArray();
        json.addProperty("ingredientType", Ref.Ports.ITEM.toString());
        json.addProperty("amountToInsert", count);

        int remainingToInsert = count;
        for (ItemPortStorage storage : itemStorages) {
            var iterJson = new JsonObject();

            remainingToInsert = storage.canInsert(item, remainingToInsert);

            iterJson.addProperty("remaining", remainingToInsert);
            iterJson.addProperty("storageUid", storage.getStorageUid().toString());
            searchIterations.add(iterJson);
            searchedStorages.add(storage.getStorageUid().toString());
        }

        json.add("insertIterations", searchIterations);
        json.addProperty("canRun", remainingToInsert <= 0);
        json.add("searchedStorages", searchedStorages);
        return json;
    }
}
