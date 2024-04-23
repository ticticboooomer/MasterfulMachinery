package io.ticticboom.mods.mm.controller;

import io.ticticboom.mods.mm.Ref;
import io.ticticboom.mods.mm.controller.machine.MachineControllerType;
import io.ticticboom.mods.mm.setup.RegistryGroupHolder;
import net.minecraft.resources.ResourceLocation;

import java.util.*;

public class MMControllerRegistry {
    private static Map<ResourceLocation, ControllerType> CONTROLLER_TYPES = new HashMap<>();
    public static List<RegistryGroupHolder> CONTROLLERS = new ArrayList<>();

    public static void init() {
        register(Ref.Controller.MACHINE, new MachineControllerType());
    }

    public static ControllerType get(ResourceLocation id) {
        return CONTROLLER_TYPES.get(id);
    }

    public static void register(ResourceLocation id, ControllerType type) {
        CONTROLLER_TYPES.put(id, type);
    }

    public static Collection<ControllerType> getControllers() {
        return CONTROLLER_TYPES.values();
    }
}
