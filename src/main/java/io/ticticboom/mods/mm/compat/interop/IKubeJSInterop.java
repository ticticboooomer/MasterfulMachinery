package io.ticticboom.mods.mm.compat.interop;

import io.ticticboom.mods.mm.extra.ExtraBlockModel;
import io.ticticboom.mods.mm.model.ControllerModel;
import io.ticticboom.mods.mm.model.PortModel;
import io.ticticboom.mods.mm.recipe.RecipeModel;
import io.ticticboom.mods.mm.structure.StructureModel;

import java.util.List;

public interface IKubeJSInterop {
    List<StructureModel> postCreateStructures();
    List<RecipeModel> postCreateRecipes();
    List<ControllerModel> postRegisterControllers();
    List<PortModel> postRegisterPorts();
    List<ExtraBlockModel> postRegisterExtraBlocks();
}
