package io.ticticboom.mods.mm.compat.kube;

import dev.latvian.mods.kubejs.event.EventGroup;
import dev.latvian.mods.kubejs.event.EventHandler;
import io.ticticboom.mods.mm.compat.kube.controller.ControllerEventHandler;
import io.ticticboom.mods.mm.compat.kube.machine.MachineRecipeCompleteHandler;
import io.ticticboom.mods.mm.compat.kube.port.PortEventHandler;
import io.ticticboom.mods.mm.compat.kube.porttypes.PortTypeEventHandler;
import io.ticticboom.mods.mm.compat.kube.recipe.RecipeEventHandler;
import io.ticticboom.mods.mm.compat.kube.recipeentry.RecipeEntryEventHandler;
import io.ticticboom.mods.mm.compat.kube.structure.StructureEventHandler;

import java.util.function.Consumer;

public class MMEvents {

    static EventGroup GROUP = EventGroup.of("MMEvents");

    public static EventHandler STRUCTURE = MMEvents.GROUP.server("structureRegistry", () -> StructureEventHandler.class);
    public static EventHandler CONTROLLER = MMEvents.GROUP.startup("controllerRegistry", () -> ControllerEventHandler.class);
    public static EventHandler PORT = MMEvents.GROUP.startup("portRegistry", () -> PortEventHandler.class);
    public static EventHandler RECIPE = MMEvents.GROUP.server("recipes", () -> RecipeEventHandler.class);
    public static EventHandler RECIPE_ENTRY = MMEvents.GROUP.startup("recipeEntries", () -> RecipeEntryEventHandler.class);
    public static EventHandler PORT_TYPE = MMEvents.GROUP.startup("portTypeRegistry", () -> PortTypeEventHandler.class);
    public static EventHandler RECIPE_COMPLETE = MMEvents.GROUP.server("recipeComplete", () -> MachineRecipeCompleteHandler.class);
}
