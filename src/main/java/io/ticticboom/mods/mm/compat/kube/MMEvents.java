package io.ticticboom.mods.mm.compat.kube;

import dev.latvian.mods.kubejs.event.EventGroup;
import dev.latvian.mods.kubejs.event.EventHandler;
import io.ticticboom.mods.mm.compat.kube.controller.ControllerEventHandler;
import io.ticticboom.mods.mm.compat.kube.port.PortEventHandler;
import io.ticticboom.mods.mm.compat.kube.porttypes.PortTypeEventHandler;
import io.ticticboom.mods.mm.compat.kube.recipe.RecipeEventHandler;
import io.ticticboom.mods.mm.compat.kube.recipeentry.RecipeEntryEventHandler;
import io.ticticboom.mods.mm.compat.kube.structure.StructureEventHandler;

public class MMEvents {

    static EventGroup GROUP = EventGroup.of("MMEvents");

    public static EventHandler STRUCTURE = MMEvents.GROUP.server("structureHandler", () -> StructureEventHandler.class);
    public static EventHandler CONTROLLER = MMEvents.GROUP.startup("controllerHandler", () -> ControllerEventHandler.class);
    public static EventHandler PORT = MMEvents.GROUP.startup("portHandler", () -> PortEventHandler.class);
    public static EventHandler RECIPE = MMEvents.GROUP.server("recipeHandler", () -> RecipeEventHandler.class);
    public static EventHandler RECIPE_ENTRY = MMEvents.GROUP.server("receipeEntryHandler", () -> RecipeEntryEventHandler.class);
    public static EventHandler PORT_TYPE = MMEvents.GROUP.startup("portTypeHandler", () -> PortTypeEventHandler.class);


}
