package io.ticticboom.mods.mm.port;

import com.google.gson.JsonObject;
import io.ticticboom.mods.mm.Ref;
import io.ticticboom.mods.mm.port.energy.EnergyPortType;
import io.ticticboom.mods.mm.port.fluid.FluidPortType;
import io.ticticboom.mods.mm.port.item.ItemPortType;
import io.ticticboom.mods.mm.setup.RegistryGroupHolder;
import io.ticticboom.mods.mm.util.ParserUtils;
import net.minecraft.resources.ResourceLocation;

import java.util.*;

public class MMPortRegistry {
    private static Map<ResourceLocation, PortType> PORT_TYPES = new HashMap<>();
    public static List<RegistryGroupHolder> PORTS = new ArrayList<>();
    public static void init() {
        register(Ref.Ports.ITEM, new ItemPortType());
        register(Ref.Ports.FLUID, new FluidPortType());
        register(Ref.Ports.ENERGY, new EnergyPortType());
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

    public static IPortIngredient parseIngredient(JsonObject json) {
        var type = ParserUtils.parseId(json, "type");
        return PORT_TYPES.get(type).getParser().parseRecipeIngredient(json);
    }
}
