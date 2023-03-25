package io.ticticboom.mods.mm.compat.kube.porttypes;

import dev.latvian.mods.kubejs.event.EventJS;
import dev.latvian.mods.kubejs.event.StartupEventJS;
import net.minecraft.resources.ResourceLocation;

public class PortTypeEventJS extends StartupEventJS {
    public PortTypeBuilderJS create(String id) {
        return new PortTypeBuilderJS(ResourceLocation.tryParse(id));
    }
}
