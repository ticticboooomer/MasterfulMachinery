package io.ticticboom.mods.mm.port.mekanism.pigment;

import com.google.gson.JsonObject;
import io.ticticboom.mods.mm.port.IPortIngredient;
import io.ticticboom.mods.mm.port.mekanism.chemical.MekanismChemicalPortParser;
import io.ticticboom.mods.mm.port.mekanism.chemical.MekanismChemicalPortStorageFactory;
import io.ticticboom.mods.mm.port.mekanism.chemical.MekanismChemicalPortStorageModel;
import io.ticticboom.mods.mm.util.ParserUtils;
import mekanism.api.chemical.pigment.Pigment;
import mekanism.api.chemical.pigment.PigmentStack;

public class MekanismPigmentPortParser extends MekanismChemicalPortParser<Pigment, PigmentStack>
{
    @Override
    public MekanismChemicalPortStorageFactory<Pigment, PigmentStack> createFactory(long amount) {
        return new MekanismPigmentPortStorageFactory(new MekanismChemicalPortStorageModel(amount));
    }

    @Override
    public IPortIngredient parseRecipeIngredient(JsonObject json) {
        var chemical = ParserUtils.parseId(json, "pigment");
        var amount = json.get("amount").getAsLong();
        return new MekanismPigmentPortIngredient(chemical, amount);
    }
}
