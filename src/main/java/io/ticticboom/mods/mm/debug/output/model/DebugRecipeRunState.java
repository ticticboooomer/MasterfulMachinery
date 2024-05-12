package io.ticticboom.mods.mm.debug.output.model;

import net.minecraft.resources.ResourceLocation;

import java.util.List;

public class DebugRecipeRunState {
    private final ResourceLocation recipeId;
    private final List<DebugRecipeRunEntry> inputs;
    private final List<DebugRecipeRunEntry> outputs;

    public DebugRecipeRunState(ResourceLocation recipeId, List<DebugRecipeRunEntry> inputs, List<DebugRecipeRunEntry> outputs) {
        this.recipeId = recipeId;
        this.inputs = inputs;
        this.outputs = outputs;
    }
}
