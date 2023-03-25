package io.ticticboom.mods.mm.compat.kube;

import dev.latvian.mods.kubejs.KubeJSPlugin;
import dev.latvian.mods.kubejs.script.ScriptType;
import dev.latvian.mods.kubejs.util.ClassFilter;

public class MMJSPlugin extends KubeJSPlugin {
    @Override
    public void addClasses(ScriptType type, ClassFilter filter) {
        super.addClasses(type, filter);
        filter.allow("net.minecraft.world.level.Level");
    }
}
