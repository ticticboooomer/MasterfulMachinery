package io.ticticboom.mods.mm.port.mekanism.infuse;

import com.google.gson.JsonObject;
import io.ticticboom.mods.mm.port.IPortIngredient;
import io.ticticboom.mods.mm.port.mekanism.chemical.MekanismChemicalPortParser;
import io.ticticboom.mods.mm.port.mekanism.chemical.MekanismChemicalPortStorageFactory;
import io.ticticboom.mods.mm.port.mekanism.chemical.MekanismChemicalPortStorageModel;
import io.ticticboom.mods.mm.util.ParserUtils;
import mekanism.api.chemical.infuse.InfuseType;
import mekanism.api.chemical.infuse.InfusionStack;

public class MekanismInfusePortParser extends MekanismChemicalPortParser<InfuseType, InfusionStack> {
    @Override
    public MekanismChemicalPortStorageFactory<InfuseType, InfusionStack> createFactory(long amount) {
        return new MekanismInfusePortStorageFactory(new MekanismChemicalPortStorageModel(amount));
    }

    @Override
    public IPortIngredient parseRecipeIngredient(JsonObject json) {
        var infuseType = ParserUtils.parseId(json, "infuse");
        var amount = json.get("amount").getAsLong();
        return new MekanismInfusePortIngredient(infuseType, amount);
    }
}
