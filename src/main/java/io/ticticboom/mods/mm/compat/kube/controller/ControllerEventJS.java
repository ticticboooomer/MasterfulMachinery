package io.ticticboom.mods.mm.compat.kube.controller;

import dev.latvian.mods.kubejs.event.EventJS;
import dev.latvian.mods.kubejs.event.StartupEventJS;
import io.ticticboom.mods.mm.setup.ControllerManager;

public class ControllerEventJS extends StartupEventJS {
    public Object create(String id) {
        return new ControllerBuilderJS().id(id);
    }
}
