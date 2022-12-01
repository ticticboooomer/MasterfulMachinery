package io.ticticboom.mods.mm.ports.mekanism.heat;

import com.google.gson.JsonObject;
import io.ticticboom.mods.mm.Ref;
import io.ticticboom.mods.mm.ports.base.IConfiguredIngredient;
import io.ticticboom.mods.mm.ports.base.IConfiguredPort;
import io.ticticboom.mods.mm.ports.base.MMPortTypeEntry;
import io.ticticboom.mods.mm.ports.base.PortStorage;
import io.ticticboom.mods.mm.ports.energy.EnergyConfiguredIngredient;
import io.ticticboom.mods.mm.ports.energy.EnergyPortStorage;
import net.minecraft.resources.ResourceLocation;

import java.util.List;

public class MekHeatPortTypeEntry extends MMPortTypeEntry {
    @Override
    public Class<? extends PortStorage> storageClass() {
        return MekHeatPortStorage.class;
    }

    @Override
    public IConfiguredPort parse(JsonObject element) {
        var capacity = element.get("capacity").getAsDouble();
        return new MekHeatConfiguredPort(capacity);
    }

    @Override
    public IConfiguredIngredient parseIngredient(JsonObject json) {
        var amount  = json.get("amount").getAsDouble();
        return new MekHeatConfiguredIngredient(amount);
    }

    @Override
    public ResourceLocation overlay(boolean input) {
        return input ? Ref.res("block/compat_ports/mekanism_heat_input_cutout") : Ref.res("block/compat_ports/mekanism_heat_output_cutout");
    }

    @Override
    public PortStorage createStorage(IConfiguredPort config) {
        return new MekHeatPortStorage(((MekHeatConfiguredPort) config));
    }

    @Override
    public boolean processInputs(IConfiguredIngredient ingredient, List<PortStorage> storage) {
        var conf = (MekHeatConfiguredIngredient) ingredient;
        for (PortStorage portStorage : storage) {
            if (portStorage instanceof MekHeatPortStorage heat) {
                heat.handler.handleHeat(-conf.amount());
            }
        }
        return false;
    }

    @Override
    public boolean processOutputs(IConfiguredIngredient ingredient, List<PortStorage> storage) {
        var conf = (MekHeatConfiguredIngredient) ingredient;
        for (PortStorage portStorage : storage) {
            if (portStorage instanceof MekHeatPortStorage heat) {
                heat.handler.handleHeat(conf.amount());
            }
        }
        return false;
    }
}
