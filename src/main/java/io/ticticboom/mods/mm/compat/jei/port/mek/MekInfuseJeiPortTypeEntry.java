package io.ticticboom.mods.mm.compat.jei.port.mek;

import mekanism.api.MekanismAPI;
import mekanism.api.chemical.gas.Gas;
import mekanism.api.chemical.gas.GasStack;
import mekanism.api.chemical.infuse.InfuseType;
import mekanism.api.chemical.infuse.InfusionStack;
import mekanism.client.jei.MekanismJEI;
import mezz.jei.api.ingredients.IIngredientType;
import net.minecraft.resources.ResourceLocation;

public class MekInfuseJeiPortTypeEntry extends MekChemicalJeiPortTypeEntry<InfuseType, InfusionStack> {
    @Override
    protected InfusionStack createStack(ResourceLocation loc) {
        var chem = MekanismAPI.infuseTypeRegistry().getValue(loc);
        return new InfusionStack(chem, 1000);
    }

    @Override
    protected IIngredientType<InfusionStack> getIngredient() {
        return MekanismJEI.TYPE_INFUSION;
    }
}
