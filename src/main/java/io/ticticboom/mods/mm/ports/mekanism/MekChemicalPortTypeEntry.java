package io.ticticboom.mods.mm.ports.mekanism;

import com.google.gson.JsonObject;
import io.ticticboom.mods.mm.ports.base.IConfiguredIngredient;
import io.ticticboom.mods.mm.ports.base.IConfiguredPort;
import io.ticticboom.mods.mm.ports.base.MMPortTypeEntry;
import io.ticticboom.mods.mm.ports.base.PortStorage;
import mekanism.api.Action;
import mekanism.api.AutomationType;
import mekanism.api.chemical.Chemical;
import mekanism.api.chemical.ChemicalStack;
import net.minecraft.resources.ResourceLocation;

import java.util.List;

public abstract class MekChemicalPortTypeEntry<CHEMICAL extends Chemical<CHEMICAL>, STACK extends ChemicalStack<CHEMICAL>> extends MMPortTypeEntry {

    private String chemicalKey;

    public MekChemicalPortTypeEntry(String chemicalKey) {
        this.chemicalKey = chemicalKey;
    }

    @Override
    public IConfiguredPort parse(JsonObject element) {
        var capacity = element.get("capacity").getAsLong();
        return new MekChemicalConfiguredPort(capacity);
    }

    @Override
    public IConfiguredIngredient parseIngredient(JsonObject json) {
        var gas = ResourceLocation.tryParse(json.get("gas").getAsString());
        var amount = json.get("amount").getAsInt();
        return new MekChemicalConfiguredIngredient(gas, amount);
    }

    @Override
    public boolean processInputs(IConfiguredIngredient ingredient, List<PortStorage> storage) {
        MekChemicalConfiguredIngredient conf = (MekChemicalConfiguredIngredient) ingredient;
        long extracted = 0;
        for (PortStorage portStorage : storage) {
            if (portStorage.getClass() == storageClass()) {
                var gasS = ((MekChemicalPortStorage<CHEMICAL, STACK>) portStorage);
                if (gasS.tank.getStack().getRaw().getRegistryName().toString().equals(conf.chemical().toString())) {
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
        MekChemicalConfiguredIngredient conf = (MekChemicalConfiguredIngredient) ingredient;
        long inserted = 0;
        for (PortStorage portStorage : storage) {
            if (portStorage.getClass() == storageClass()) {
                var gasS = ((MekChemicalPortStorage<CHEMICAL, STACK>) portStorage);
                STACK stack = gasS.createStack(conf.chemical(),conf.amount() - inserted);
                inserted = conf.amount() - gasS.tank.insert(stack, Action.EXECUTE, AutomationType.EXTERNAL).getAmount();
                if (inserted >= conf.amount()) {
                    return true;
                }
            }
        }
        return false;
    }
}
