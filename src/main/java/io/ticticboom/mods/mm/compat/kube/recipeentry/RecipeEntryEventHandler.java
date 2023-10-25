package io.ticticboom.mods.mm.compat.kube.recipeentry;

import dev.latvian.mods.kubejs.event.EventJS;

public class RecipeEntryEventHandler extends EventJS {

    public RecipeEntryBuilderJS create(String id) {
        return new RecipeEntryBuilderJS(id);
    }
}
