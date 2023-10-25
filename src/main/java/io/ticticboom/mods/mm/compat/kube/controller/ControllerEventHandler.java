package io.ticticboom.mods.mm.compat.kube.controller;

import dev.latvian.mods.kubejs.event.EventJS;

public class ControllerEventHandler extends EventJS {
    public Object create(String id) {
        return new ControllerBuilderJS().id(id);
    }
}
