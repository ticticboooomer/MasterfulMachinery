package io.ticticboom.mods.mm.compat.kube.controller;

import io.ticticboom.mods.mm.Ref;
import io.ticticboom.mods.mm.setup.ControllerManager;
import io.ticticboom.mods.mm.setup.model.ControllerModel;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class ControllerBuilderJS {
    private String id;
    private String name;

    public void build() {
        ResourceLocation key = Ref.res(id);
        ControllerManager.REGISTRY.put(key, new ControllerModel(key, Component.literal(name), new ResourceLocation(key.getNamespace(), key.getPath() + "_controller")));
    }

    public ControllerBuilderJS name(String name) {
        this.name = name;
        return this;
    }

    public ControllerBuilderJS id(String id) {
        this.id = id;
        return this;
    }
}
