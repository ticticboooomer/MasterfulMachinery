package io.ticticboom.mods.mm.compat.kube.porttypes;

import dev.latvian.mods.kubejs.event.EventJS;
import net.minecraft.resources.ResourceLocation;

public class PortTypeEventHandler extends EventJS {
    public PortTypeBuilderJS create(String id) {
        return new PortTypeBuilderJS(ResourceLocation.tryParse(id));
    }
}
