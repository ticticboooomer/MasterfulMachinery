package io.ticticboom.mods.mm.ports.energy;

import com.google.gson.JsonObject;
import io.ticticboom.mods.mm.Ref;
import io.ticticboom.mods.mm.ports.base.IConfiguredIngredient;
import io.ticticboom.mods.mm.ports.base.IConfiguredPort;
import io.ticticboom.mods.mm.ports.base.MMPortTypeEntry;
import io.ticticboom.mods.mm.ports.base.PortStorage;
import net.minecraft.resources.ResourceLocation;

import java.util.List;

public class EnergyPortTypeEntry extends MMPortTypeEntry {
    @Override
    public ResourceLocation id() {
        return Ref.Ports.ENERGY;
    }

    @Override
    public Class<? extends PortStorage> storageClass() {
        return EnergyPortStorage.class;
    }

    @Override
    public IConfiguredPort parse(JsonObject element) {
        var capacity = element.get("capacity").getAsInt();
        return new EnergyConfiguredPort(capacity);
    }

    @Override
    public IConfiguredIngredient parseIngredient(JsonObject json) {
        var amount = json.get("amount").getAsInt();
        return new EnergyConfiguredIngredient(amount);
    }

    @Override
    public ResourceLocation overlay(boolean input) {
        return input ? Ref.res("block/base_ports/energy_input_cutout") : Ref.res("block/base_ports/energy_output_cutout");

    }

    @Override
    public PortStorage createStorage(IConfiguredPort config) {
        return new EnergyPortStorage((EnergyConfiguredPort) config);
    }

    @Override
    public boolean processInputs(IConfiguredIngredient ingredient, List<PortStorage> storage) {
        var conf = (EnergyConfiguredIngredient) ingredient;
        var itemCounter = 0;
        for (PortStorage portStorage : storage) {
            if (portStorage instanceof EnergyPortStorage eps) {
                itemCounter += eps.handler.extractEnergy(conf.amount(), false);
                if (itemCounter >= conf.amount()) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean processOutputs(IConfiguredIngredient ingredient, List<PortStorage> storage) {
        var conf = (EnergyConfiguredIngredient) ingredient;
        var itemCounter = 0;
        for (PortStorage portStorage : storage) {
            if (portStorage instanceof EnergyPortStorage eps) {
                itemCounter += eps.handler.receiveEnergy(conf.amount(), false);
                if (itemCounter >= conf.amount()) {
                    return true;
                }
            }
        }
        return false;
    }
}
