package io.ticticboom.mods.mm.block;

import com.google.gson.JsonObject;
import io.ticticboom.mods.mm.Ref;
import io.ticticboom.mods.mm.block.circuit.CircuitBlockType;
import io.ticticboom.mods.mm.block.gearbox.GearboxBlockType;
import io.ticticboom.mods.mm.block.vent.VentBlockItem;
import io.ticticboom.mods.mm.block.vent.VentBlockType;
import io.ticticboom.mods.mm.port.IPortIngredient;
import io.ticticboom.mods.mm.port.PortType;
import io.ticticboom.mods.mm.setup.RegistryGroupHolder;
import io.ticticboom.mods.mm.util.ParserUtils;
import net.minecraft.resources.ResourceLocation;

import java.util.*;

public class MMExtraBlockRegistry {
    public static final Map<ResourceLocation, ExtraBlockType> BLOCK_TYPES = new HashMap<>();
    public static final List<RegistryGroupHolder> BLOCKS = new ArrayList<>();

    public static void init(){
        register(Ref.ExtraBlocks.CIRCUIT, new CircuitBlockType());
        register(Ref.ExtraBlocks.GEARBOX, new GearboxBlockType());
        register(Ref.ExtraBlocks.VENT, new VentBlockType());
    }

    public static ExtraBlockType get(ResourceLocation id) {
        return BLOCK_TYPES.get(id);
    }

    public static void register(ResourceLocation id, ExtraBlockType type) {
        BLOCK_TYPES.put(id, type);
    }
}
