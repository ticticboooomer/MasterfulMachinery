package io.ticticboom.mods.mm.controller;

import io.ticticboom.mods.mm.Ref;
import io.ticticboom.mods.mm.controller.machine.MachineControllerType;
import io.ticticboom.mods.mm.setup.RegistryGroupHolder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

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
    public static Item getControllerItem(ResourceLocation id) {
        for (RegistryGroupHolder holder : CONTROLLERS) {
            Item item = holder.getItem().get();
            if (item instanceof IControllerPart part) {
                if (part.getModel().id().equals(id.getPath())) {
                    return item;
                }
            }
        }
        return null;
    }
    public static Block getControllerBlock(ResourceLocation id) {
        for (RegistryGroupHolder holder : CONTROLLERS) {
            var block = holder.getBlock().get();
            if (block instanceof IControllerPart part) {
                if (part.getModel().id().equals(id.getPath())) {
                    return block;
                }
            }
        }
        return null;
    }
}
