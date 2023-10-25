package io.ticticboom.mods.mm.compat.kube;

import dev.latvian.mods.kubejs.KubeJSPlugin;
import dev.latvian.mods.kubejs.script.ScriptType;
import dev.latvian.mods.kubejs.util.ClassFilter;
import io.ticticboom.mods.mm.compat.kube.controller.ControllerEventHandler;
import io.ticticboom.mods.mm.compat.kube.port.PortEventHandler;
import io.ticticboom.mods.mm.compat.kube.recipe.RecipeEventHandler;
import io.ticticboom.mods.mm.compat.kube.structure.StructureEventHandler;

public class MMJSPlugin extends KubeJSPlugin {
    @Override
    public void registerClasses(ScriptType type, ClassFilter filter) {
        super.registerClasses(type, filter);
        filter.allow("net.minecraft.world.level.Level");
    }

    @Override
    public void registerEvents() {
        MMEvents.GROUP.register();
    }

}
