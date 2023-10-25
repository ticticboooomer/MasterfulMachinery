package io.ticticboom.mods.mm.compat.kube.recipeentry;

import io.ticticboom.mods.mm.recipe.js.RecipeEntryJS;
import io.ticticboom.mods.mm.setup.MMRegistries;
import net.minecraft.resources.ResourceLocation;

public class RecipeEntryBuilderJS {
    private  RecipeEntryJS.ProcessRecipeIOCallback processInputs;
    private  RecipeEntryJS.ProcessRecipeIOCallback processOutputs;
    private String id;


    public RecipeEntryBuilderJS processInputs(RecipeEntryJS.ProcessRecipeIOCallback processInputs) {
        this.processInputs = processInputs;
        return this;
    }

    public RecipeEntryBuilderJS processOutputs(RecipeEntryJS.ProcessRecipeIOCallback processOutputs) {
        this.processOutputs = processOutputs;
        return this;
    }


    public RecipeEntryBuilderJS(String id) {
        this.id = id;
    }

    public void build() {
        MMRegistries.RECIPE_ENTRIES.get().register(ResourceLocation.tryParse(id),new RecipeEntryJS(processInputs, processOutputs));
    }
}
