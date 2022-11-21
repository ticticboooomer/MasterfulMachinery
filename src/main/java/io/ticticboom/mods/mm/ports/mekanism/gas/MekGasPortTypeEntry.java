package io.ticticboom.mods.mm.ports.mekanism.gas;

import com.google.gson.JsonObject;
import io.ticticboom.mods.mm.Ref;
import io.ticticboom.mods.mm.ports.base.IConfiguredIngredient;
import io.ticticboom.mods.mm.ports.base.IConfiguredPort;
import io.ticticboom.mods.mm.ports.base.MMPortTypeEntry;
import io.ticticboom.mods.mm.ports.base.PortStorage;
import mekanism.api.Action;
import mekanism.api.AutomationType;
import mekanism.api.MekanismAPI;
import mekanism.api.chemical.gas.GasStack;
import net.minecraft.resources.ResourceLocation;

import java.util.List;

public class MekGasPortTypeEntry extends MMPortTypeEntry {
    @Override
    public Class<? extends PortStorage> storageClass() {
        return MekGasPortStorage.class;
    }

    @Override
    public IConfiguredPort parse(JsonObject element) {
        var capacity = element.get("capacity").getAsLong();
        return new MekGasConfiguredPort(capacity);
    }

    @Override
    public IConfiguredIngredient parseIngredient(JsonObject json) {
        var gas = ResourceLocation.tryParse(json.get("gas").getAsString());
        var amount = json.get("amount").getAsInt();
        return new MekGasConfiguredIngredient(gas, amount);
    }

    @Override
    public ResourceLocation overlay(boolean input) {
        return input ? Ref.res("block/compat_ports/mekanism_gas_input_cutout") : Ref.res("block/compat_ports/mekanism_gas_output_cutout");
    }

    @Override
    public PortStorage createStorage(IConfiguredPort config) {
        return new MekGasPortStorage((MekGasConfiguredPort) config);
    }

    @Override
    public boolean processInputs(IConfiguredIngredient ingredient, List<PortStorage> storage) {
        MekGasConfiguredIngredient conf = (MekGasConfiguredIngredient) ingredient;
        long extracted = 0;
        for (PortStorage portStorage : storage) {
            if (portStorage instanceof MekGasPortStorage gasS) {
                if (gasS.tank.getStack().getRaw().getRegistryName().toString().equals(conf.gas().toString())) {
                    var extractAmount = conf.amount() - extracted;
                    extracted = gasS.tank.extract(extractAmount, Action.EXECUTE, AutomationType.EXTERNAL).getAmount();
                    if (extracted >= conf.amount()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public boolean processOutputs(IConfiguredIngredient ingredient, List<PortStorage> storage) {
        MekGasConfiguredIngredient conf = (MekGasConfiguredIngredient) ingredient;
        long inserted = 0;
        for (PortStorage portStorage : storage) {
            if (portStorage instanceof MekGasPortStorage gasS) {
                var gasStack = new GasStack(MekanismAPI.gasRegistry().getValue(conf.gas()), conf.amount() - inserted);
                inserted = conf.amount() - gasS.tank.insert(gasStack, Action.EXECUTE, AutomationType.EXTERNAL).getAmount();
                if (inserted >= conf.amount()) {
                    return true;
                }
            }
        }
        return false;
    }
}
