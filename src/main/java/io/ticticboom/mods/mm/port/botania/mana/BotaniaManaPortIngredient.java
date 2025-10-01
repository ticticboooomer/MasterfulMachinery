package io.ticticboom.mods.mm.port.botania.mana;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import io.ticticboom.mods.mm.Ref;
import io.ticticboom.mods.mm.compat.jei.SlotGrid;
import io.ticticboom.mods.mm.compat.jei.ingredient.MMJeiIngredients;
import io.ticticboom.mods.mm.compat.jei.ingredient.mana.BotaniaManaStack;
import io.ticticboom.mods.mm.port.IPortIngredient;
import io.ticticboom.mods.mm.recipe.RecipeModel;
import io.ticticboom.mods.mm.recipe.RecipeStateModel;
import io.ticticboom.mods.mm.recipe.RecipeStorages;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.builder.IRecipeSlotBuilder;
import mezz.jei.api.helpers.IJeiHelpers;
import mezz.jei.api.recipe.IFocusGroup;
import net.minecraft.world.level.Level;

public class BotaniaManaPortIngredient implements IPortIngredient {

    private final int mana;

    public BotaniaManaPortIngredient(int mana) {
        this.mana = mana;
    }

    @Override
    public boolean canProcess(Level level, RecipeStorages storages, RecipeStateModel state) {
        if(storages == null) return false;
        var inputs = storages.getInputStorages(BotaniaManaPortStorage.class);
        var remains = mana;
        for (BotaniaManaPortStorage input : inputs) {
            remains -= input.extractMana(remains, true);
        }
        return remains <= 0;
    }

    @Override
    public void process(Level level, RecipeStorages storages, RecipeStateModel state) {
        var inputs = storages.getInputStorages(BotaniaManaPortStorage.class);
        var remains = mana;
        for (BotaniaManaPortStorage input : inputs) {
            remains -= input.extractMana(remains, false);
        }
    }

    @Override
    public boolean canOutput(Level level, RecipeStorages storages, RecipeStateModel state) {
        var outputs = storages.getOutputStorages(BotaniaManaPortStorage.class);
        var remains = mana;
        for (BotaniaManaPortStorage output : outputs) {
            remains -= output.receiveMana(remains, true);
        }
        return remains <= 0;
    }

    @Override
    public void output(Level level, RecipeStorages storages, RecipeStateModel state) {
        var outputs = storages.getOutputStorages(BotaniaManaPortStorage.class);
        var remains = mana;
        for (BotaniaManaPortStorage output : outputs) {
            remains -= output.receiveMana(remains, false);
        }
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, RecipeModel model, IFocusGroup focus, IJeiHelpers helpers, SlotGrid grid, IRecipeSlotBuilder recipeSlot) {
        recipeSlot.addIngredient(MMJeiIngredients.BOTANIA_MANA, new BotaniaManaStack(mana));
    }

    @Override
    public JsonObject debugInput(Level level, RecipeStorages storages, JsonObject json) {
        var inputStorages = storages.getInputStorages(BotaniaManaPortStorage.class);
        var searchedStoragesJson = new JsonArray();
        var searchIterationsJson = new JsonArray();
        json.addProperty("ingredientType", Ref.Ports.BOTANIA_MANA.toString());
        json.addProperty("amountToExtract", mana);

        int remaining = mana;
        for (BotaniaManaPortStorage storage : inputStorages) {
            var iterJson = new JsonObject();

            var extracted = storage.extractMana(remaining, true);
            remaining -= extracted;

            iterJson.addProperty("extracted", extracted);
            iterJson.addProperty("remainingToExtract", remaining);
            iterJson.addProperty("storageUid", storage.getStorageUid().toString());

            searchIterationsJson.add(iterJson);
            searchedStoragesJson.add(storage.getStorageUid().toString());
        }
        json.add("extractIterations", searchIterationsJson);
        json.addProperty("canRun", remaining <= 0);
        json.add("searchedStorages", searchedStoragesJson);
        return json;
    }

    @Override
    public JsonObject debugOutput(Level level, RecipeStorages storages, JsonObject json) {
        var outputStorages = storages.getOutputStorages(BotaniaManaPortStorage.class);
        var searchedStoragesJson = new JsonArray();
        var searchIterationsJson = new JsonArray();
        json.addProperty("ingredientType", Ref.Ports.BOTANIA_MANA.toString());
        json.addProperty("amountToInsert", mana);

        int remaining = mana;
        for (BotaniaManaPortStorage storage : outputStorages) {
            var iterJson = new JsonObject();

            var inserted = storage.receiveMana(remaining, true);
            remaining -= inserted;

            iterJson.addProperty("inserted", inserted);
            iterJson.addProperty("remainingToInsert", remaining);

            searchIterationsJson.add(iterJson);
            searchedStoragesJson.add(storage.getStorageUid().toString());
        }
        json.add("insertIterations", searchIterationsJson);
        json.addProperty("canRun", remaining <= 0);
        json.add("searchedStorages", searchedStoragesJson);
        return json;
    }
}
