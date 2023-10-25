package io.ticticboom.mods.mm.compat.kube.port;

import dev.latvian.mods.kubejs.event.EventJS;

public class PortEventHandler extends EventJS {
    public PortBuilderJS create(String id) {
        return new PortBuilderJS().id(id);
    }
}
