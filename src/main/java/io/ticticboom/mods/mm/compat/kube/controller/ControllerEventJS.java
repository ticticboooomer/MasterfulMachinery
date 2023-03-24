package io.ticticboom.mods.mm.compat.kube.controller;

import dev.latvian.mods.kubejs.event.EventJS;
import io.ticticboom.mods.mm.setup.ControllerManager;

public class ControllerEventJS extends EventJS {
    public Object create(String id) {
        return new ControllerBuilderJS().id(id);
    }
}
