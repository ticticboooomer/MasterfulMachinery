package io.ticticboom.mods.mm.port.mekanism.chemical;

import com.google.gson.JsonObject;
import io.ticticboom.mods.mm.port.IPortParser;
import io.ticticboom.mods.mm.port.IPortStorageFactory;
import mekanism.api.chemical.Chemical;
import mekanism.api.chemical.ChemicalStack;

public abstract class MekanismChemicalPortParser<CHEMICAL extends Chemical<CHEMICAL>, STACK extends ChemicalStack<CHEMICAL>> implements IPortParser {

    public abstract MekanismChemicalPortStorageFactory<CHEMICAL, STACK> createFactory(long amount);

    @Override
    public IPortStorageFactory parseStorage(JsonObject json) {
        var amount = json.get("capacity").getAsLong();
        return createFactory(amount);
    }
}
