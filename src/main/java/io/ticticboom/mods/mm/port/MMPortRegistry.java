package io.ticticboom.mods.mm.port;

import com.google.gson.JsonObject;
import io.ticticboom.mods.mm.Ref;
import io.ticticboom.mods.mm.model.PortModel;
import io.ticticboom.mods.mm.port.energy.EnergyPortType;
import io.ticticboom.mods.mm.port.fluid.FluidPortType;
import io.ticticboom.mods.mm.port.item.ItemPortType;
import io.ticticboom.mods.mm.port.kinetic.CreateKineticPortType;
import io.ticticboom.mods.mm.port.mekanism.gas.MekanismGasPortType;
import io.ticticboom.mods.mm.port.mekanism.infuse.MekanismInfusePortType;
import io.ticticboom.mods.mm.port.mekanism.pigment.MekanismPigmentPortType;
import io.ticticboom.mods.mm.port.mekanism.slurry.MekanismSlurryPortType;
import io.ticticboom.mods.mm.setup.RegistryGroupHolder;
import io.ticticboom.mods.mm.util.ParserUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fml.ModList;

import java.util.*;

public class MMPortRegistry {
    private static Map<ResourceLocation, PortType> PORT_TYPES = new HashMap<>();
    public static List<RegistryGroupHolder> PORTS = new ArrayList<>();

    public static void init() {
        register(Ref.Ports.ITEM, new ItemPortType());
        register(Ref.Ports.FLUID, new FluidPortType());
        register(Ref.Ports.ENERGY, new EnergyPortType());

        if (ModList.get().isLoaded("mekanism")) {
            register(Ref.Ports.MEK_GAS, new MekanismGasPortType());
            register(Ref.Ports.MEK_SLURRY, new MekanismSlurryPortType());
            register(Ref.Ports.MEK_PIGMENT, new MekanismPigmentPortType());
            register(Ref.Ports.MEK_INFUSE, new MekanismInfusePortType());
        }
        if (ModList.get().isLoaded("create")) {
            register(Ref.Ports.CREATE_KINETIC, new CreateKineticPortType());
        }
    }

    public static PortType get(ResourceLocation id) {
        return PORT_TYPES.get(id);
    }

    public static void register(ResourceLocation id, PortType type) {
        PORT_TYPES.put(id, type);
    }

    public static Collection<PortType> getPortBlocks() {
        return PORT_TYPES.values();
    }

    public static IPortIngredient parseIngredient(JsonObject json) {
        var type = ParserUtils.parseId(json, "type");
        return PORT_TYPES.get(type).getParser().parseRecipeIngredient(json);
    }

    public static List<PortModel> getPortModelsForControllerId(ResourceLocation id) {
        return PORTS.stream().filter(x -> {
            if (x.getBlock().get() instanceof IPortBlock bp) {
                return bp.getModel().controllerIds().contains(id);
            }
            return false;
        }).map(x -> ((IPortBlock)x.getBlock().get()).getModel()).toList();
    }
}
