package io.ticticboom.mods.mm.compat.jei.port.mek;

import mekanism.api.MekanismAPI;
import mekanism.api.chemical.pigment.Pigment;
import mekanism.api.chemical.pigment.PigmentStack;
import mekanism.client.jei.MekanismJEI;
import mezz.jei.api.ingredients.IIngredientType;
import net.minecraft.resources.ResourceLocation;

public class MekPigmentJeiPortTypeEntry extends MekChemicalJeiPortTypeEntry<Pigment, PigmentStack> {
    @Override
    protected PigmentStack createStack(ResourceLocation loc) {
        var chem = MekanismAPI.pigmentRegistry().getValue(loc);
        return new PigmentStack(chem, 1000);
    }

    @Override
    protected IIngredientType<PigmentStack> getIngredient() {
        return MekanismJEI.TYPE_PIGMENT;
    }
}
