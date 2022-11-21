package io.ticticboom.mods.mm.compat.jei.port.mek;

import mekanism.api.MekanismAPI;
import mekanism.api.chemical.gas.Gas;
import mekanism.api.chemical.gas.GasStack;
import mekanism.client.jei.MekanismJEI;
import mezz.jei.api.ingredients.IIngredientType;
import net.minecraft.resources.ResourceLocation;

public class MekGasJeiPortTypeEntry extends MekChemicalJeiPortTypeEntry<Gas, GasStack> {
    @Override
    protected GasStack createStack(ResourceLocation loc) {
        var gas = MekanismAPI.gasRegistry().getValue(loc);
        return new GasStack(gas, 1000);
    }

    @Override
    protected IIngredientType<GasStack> getIngredient() {
        return MekanismJEI.TYPE_GAS;
    }
}
