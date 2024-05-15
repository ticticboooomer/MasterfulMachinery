package io.ticticboom.mods.mm.compat.interop;

import io.ticticboom.mods.mm.compat.kjs.MMKubeEvents;
import io.ticticboom.mods.mm.compat.kjs.builder.ControllerBuilderJS;
import io.ticticboom.mods.mm.compat.kjs.builder.ExtraBlockBuilderJS;
import io.ticticboom.mods.mm.compat.kjs.builder.RecipeBuilderJS;
import io.ticticboom.mods.mm.compat.kjs.builder.StructureBuilderJS;
import io.ticticboom.mods.mm.compat.kjs.event.*;
import io.ticticboom.mods.mm.extra.ExtraBlockModel;
import io.ticticboom.mods.mm.model.ControllerModel;
import io.ticticboom.mods.mm.model.PortModel;
import io.ticticboom.mods.mm.recipe.RecipeModel;
import io.ticticboom.mods.mm.structure.StructureModel;

import java.util.List;

public class MMKubeJSInterop implements IKubeJSInterop {
    @Override
    public List<StructureModel> postCreateStructures() {
        var event = new StructureEventJS();
        MMKubeEvents.STRUCTURES.post(event);
        return event.getBuilders().stream().map(StructureBuilderJS::build).toList();
    }

    @Override
    public List<RecipeModel> postCreateRecipes() {
        var event = new RecipeEventJS();
        MMKubeEvents.RECIPES.post(event);
        return event.getBuilders().stream().map(RecipeBuilderJS::build).toList();
    }

    @Override
    public List<ControllerModel> postRegisterControllers() {
        ControllerEventJS event = new ControllerEventJS();
        MMKubeEvents.CONTROLLERS.post(event);
        return event.getControllers().stream().map(ControllerBuilderJS::build).toList();
    }

    @Override
    public List<PortModel> postRegisterPorts() {
        var event = new PortEventJS();
        MMKubeEvents.PORTS.post(event);
        return event.getPorts().stream().flatMap(a -> a.build().stream()).toList();
    }

    @Override
    public List<ExtraBlockModel> postRegisterExtraBlocks() {
        var event = new ExtraBlockEventJS();
        MMKubeEvents.EXTRA.post(event);
        return event.getBuilder().stream().map(ExtraBlockBuilderJS::build).toList();
    }
}
