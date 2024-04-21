package io.ticticboom.mods.mm.controller;

import io.ticticboom.mods.mm.Ref;
import io.ticticboom.mods.mm.controller.machine.MachineControllerType;
import net.minecraft.resources.ResourceLocation;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class MMControllerRegistry {
    private static Map<ResourceLocation, ControllerType> CONTROLLERS = new HashMap<>();

    public static void init() {
        register(Ref.Controller.MACHINE, new MachineControllerType());
    }

    public static ControllerType get(ResourceLocation id) {
        return CONTROLLERS.get(id);
    }

    public static void register(ResourceLocation id, ControllerType type) {
        CONTROLLERS.put(id, type);
    }

    public static Collection<ControllerType> getPorts() {
        return CONTROLLERS.values();
    }
}
