package io.ticticboom.mods.mm.compat.kube.recipeentry;

import dev.latvian.mods.kubejs.event.StartupEventJS;

public class RecipeEntryEventJS extends StartupEventJS {

    public RecipeEntryBuilderJS create(String id) {
        return new RecipeEntryBuilderJS(id);
    }
}
