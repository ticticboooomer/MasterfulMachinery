package io.ticticboom.mods.mm.compat.kube.port;

import dev.latvian.mods.kubejs.event.EventJS;
import dev.latvian.mods.kubejs.event.StartupEventJS;

public class PortEventJS extends StartupEventJS {
    public PortBuilderJS create(String id) {
        return new PortBuilderJS().id(id);
    }
}
