package io.ticticboom.mods.mm.ports;

import io.ticticboom.mods.mm.Ref;
import io.ticticboom.mods.mm.model.config.PortModel;
import io.ticticboom.mods.mm.ports.item.ItemPortType;
import io.ticticboom.mods.mm.setup.RegistryGroupHolder;
import net.minecraft.resources.ResourceLocation;

import java.util.*;

public class MMPortRegistry {
    private static Map<ResourceLocation, PortType> PORT_TYPES = new HashMap<>();
    public static List<RegistryGroupHolder> PORTS = new ArrayList<>();
    public static void init() {
        register(Ref.Ports.ITEM, new ItemPortType());
    }

    public static PortType get(ResourceLocation id) {
        return PORT_TYPES.get(id);
    }

    public static void register(ResourceLocation id, PortType type) {
        PORT_TYPES.put(id, type);
    }

    public static Collection<PortType> getPorts() {
        return PORT_TYPES.values();
    }


}
