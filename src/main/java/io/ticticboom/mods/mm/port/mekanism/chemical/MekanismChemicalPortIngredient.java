package io.ticticboom.mods.mm.port.mekanism.chemical;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import io.ticticboom.mods.mm.compat.jei.SlotGrid;
import io.ticticboom.mods.mm.port.IPortIngredient;
import io.ticticboom.mods.mm.recipe.RecipeModel;
import io.ticticboom.mods.mm.recipe.RecipeStateModel;
import io.ticticboom.mods.mm.recipe.RecipeStorages;
import mekanism.api.Action;
import mekanism.api.chemical.Chemical;
import mekanism.api.chemical.ChemicalStack;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.builder.IRecipeSlotBuilder;
import mezz.jei.api.helpers.IJeiHelpers;
import mezz.jei.api.recipe.IFocusGroup;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;

public abstract class MekanismChemicalPortIngredient<CHEMICAL extends Chemical<CHEMICAL>, STACK extends ChemicalStack<CHEMICAL>> implements IPortIngredient {

    protected final ResourceLocation id;
    protected final long amount;

    protected final STACK stack;
    protected final CHEMICAL chemical;

    public abstract STACK createStack(CHEMICAL id, long amount);
    public abstract CHEMICAL findChemical(ResourceLocation id);
    public abstract Class<? extends MekanismChemicalPortStorage<CHEMICAL, STACK>> getStorageClass();
    public abstract ResourceLocation getTypeId();

    public MekanismChemicalPortIngredient(ResourceLocation chemical, long amount) {
        this.id = chemical;
        this.amount = amount;
        this.chemical = findChemical(id);
        stack = createStack(this.chemical, amount);
    }

    @Override
    public boolean canProcess(Level level, RecipeStorages storages, RecipeStateModel state) {
        if(storages == null) return false;
        var inputStorages = storages.getInputStorages(getStorageClass());
        long remaining = amount;
        for (MekanismChemicalPortStorage<CHEMICAL, STACK> storage : inputStorages) {
            var extracted = storage.extract(remaining, Action.SIMULATE);
            remaining -= extracted.getAmount();
        }
        return remaining <= 0;
    }

    @Override
    public void process(Level level, RecipeStorages storages, RecipeStateModel state) {
        var inputStorages = storages.getInputStorages(getStorageClass());
        long remaining = amount;
        for (MekanismChemicalPortStorage<CHEMICAL, STACK> storage : inputStorages) {
            var extracted = storage.extract(remaining, Action.EXECUTE);
            remaining -= extracted.getAmount();
        }
    }

    @Override
    public boolean canOutput(Level level, RecipeStorages storages, RecipeStateModel state) {
        var outputStorages = storages.getOutputStorages(getStorageClass());
        long remaining = amount;
        for (MekanismChemicalPortStorage<CHEMICAL, STACK> storage : outputStorages) {
            var inserted = storage.insert(createStack(this.chemical, remaining), Action.SIMULATE);
            remaining -= inserted.getAmount();
        }
        return remaining <= 0;
    }

    @Override
    public void output(Level level, RecipeStorages storages, RecipeStateModel state) {
        var outputStorages = storages.getOutputStorages(getStorageClass());
        long remaining = amount;
        for (MekanismChemicalPortStorage<CHEMICAL, STACK> storage : outputStorages) {
            var inserted = storage.insert(createStack(this.chemical, remaining), Action.EXECUTE);
            remaining -= inserted.getAmount();
        }
    }

    @Override
    public JsonObject debugInput(Level level, RecipeStorages storages, JsonObject json) {
        var inputStorages = storages.getInputStorages(getStorageClass());
        var searchedStoragesJson = new JsonArray();

        json.addProperty("ingredientType", getTypeId().toString());
        json.addProperty("amountToInsert", amount);

        long remaining = amount;
        for (MekanismChemicalPortStorage<CHEMICAL, STACK> storage : inputStorages) {
            var extracted = storage.extract(remaining, Action.SIMULATE);
            remaining -= extracted.getAmount();
            searchedStoragesJson.add(storage.getStorageUid().toString());
        }
        json.addProperty("canRun", remaining <= 0);
        json.add("searchedStorages", searchedStoragesJson);
        return json;
    }

    @Override
    public JsonObject debugOutput(Level level, RecipeStorages storages, JsonObject json) {
        var outputStorages = storages.getOutputStorages(getStorageClass());
        var searchedStoragesJson = new JsonArray();

        json.addProperty("ingredientType", getTypeId().toString());
        json.addProperty("amountToInsert", amount);

        long remaining = amount;
        for (MekanismChemicalPortStorage<CHEMICAL, STACK> storage : outputStorages) {
            var inserted = storage.insert(createStack(this.chemical, remaining), Action.SIMULATE);
            remaining -= inserted.getAmount();
            searchedStoragesJson.add(storage.getStorageUid().toString());
        }
        json.addProperty("canRun", remaining <= 0);
        json.add("searchedStorages", searchedStoragesJson);
        return json;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, RecipeModel model, IFocusGroup focus, IJeiHelpers helpers, SlotGrid grid, IRecipeSlotBuilder recipeSlot) {
        recipeSlot.addTooltipCallback((a, c) -> {
            c.add(1, Component.literal(amount + " mB"));
        });
    }
}
