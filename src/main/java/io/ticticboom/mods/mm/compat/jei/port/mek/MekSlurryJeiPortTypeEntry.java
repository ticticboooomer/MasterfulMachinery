package io.ticticboom.mods.mm.compat.jei.port.mek;

import mekanism.api.MekanismAPI;
import mekanism.api.chemical.slurry.Slurry;
import mekanism.api.chemical.slurry.SlurryStack;
import mekanism.client.jei.MekanismJEI;
import mezz.jei.api.ingredients.IIngredientType;
import net.minecraft.resources.ResourceLocation;

public class MekSlurryJeiPortTypeEntry extends MekChemicalJeiPortTypeEntry<Slurry, SlurryStack>{
    @Override
    protected SlurryStack createStack(ResourceLocation loc) {
        var chem = MekanismAPI.slurryRegistry().getValue(loc);
        return new SlurryStack(chem, 1000);
    }

    @Override
    protected IIngredientType<SlurryStack> getIngredient() {
        return MekanismJEI.TYPE_SLURRY;
    }
}
