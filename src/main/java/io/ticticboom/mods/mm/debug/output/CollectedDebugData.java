package io.ticticboom.mods.mm.debug.output;

import com.google.gson.JsonObject;
import io.ticticboom.mods.mm.debug.output.model.DebugPortBlockState;
import io.ticticboom.mods.mm.debug.output.model.DebugRecipeRunState;
import io.ticticboom.mods.mm.model.ControllerModel;
import io.ticticboom.mods.mm.model.PortModel;
import io.ticticboom.mods.mm.recipe.RecipeModel;
import io.ticticboom.mods.mm.structure.StructureModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class CollectedDebugData {
    public List<StructureModel> structureDefs = new ArrayList<>();
    public List<RecipeModel> recipeDefs = new ArrayList<>();
    public List<PortModel> portDefs = new ArrayList<>();
    public ControllerModel controllerDef;

    public List<JsonObject> structureStates = new ArrayList<>();
    public List<JsonObject> recipeStates = new ArrayList<>();
    public JsonObject diags = new JsonObject();

    // ---
    // controller state
    // runtime & system diagnostics
    // ---

    public void addRecipe(final RecipeModel recipe) {
        if (this.recipeDefs == null) {
            this.recipeDefs = new ArrayList<>();
        }
        this.recipeDefs.add(recipe);
    }

    public void addStructure(StructureModel structure) {
        if (structure == null) {
            this.structureDefs = new ArrayList<>();
        }
        structureDefs.add(structure);
    }

    public void addStructureState(final JsonObject state) {
        if (state == null) {
            structureStates = new ArrayList<>();
        }
        structureStates.add(state);
    }

    public void addRecipeState(final JsonObject state) {
        if (state == null) {
            recipeStates = new ArrayList<>();
        }
        recipeStates.add(state);
    }

    public List<ResourceLocation> getStructureIds() {
        return this.structureDefs.stream().map(StructureModel::id).toList();
    }
}
