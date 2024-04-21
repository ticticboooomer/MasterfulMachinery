package io.ticticboom.mods.mm.ports;

import io.ticticboom.mods.mm.Ref;
import io.ticticboom.mods.mm.ports.item.ItemPortType;
import net.minecraft.resources.ResourceLocation;

import java.util.*;

public class MMPortRegistry {
    private static Map<ResourceLocation, PortType> PORTS = new HashMap<>();

    public static void init() {
        register(Ref.Ports.ITEM, new ItemPortType());
    }

    public static PortType get(ResourceLocation id) {
        return PORTS.get(id);
    }

    public static void register(ResourceLocation id, PortType type) {
        PORTS.put(id, type);
    }

    public static Collection<PortType> getPorts() {
        return PORTS.values();
    }


}
