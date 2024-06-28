package io.ticticboom.mods.mm.port.mekanism.pigment;

import io.ticticboom.mods.mm.Ref;
import io.ticticboom.mods.mm.compat.jei.SlotGrid;
import io.ticticboom.mods.mm.port.mekanism.chemical.MekanismChemicalPortIngredient;
import io.ticticboom.mods.mm.port.mekanism.chemical.MekanismChemicalPortStorage;
import io.ticticboom.mods.mm.recipe.RecipeModel;
import mekanism.api.MekanismAPI;
import mekanism.api.chemical.pigment.Pigment;
import mekanism.api.chemical.pigment.PigmentStack;
import mekanism.client.jei.MekanismJEI;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.builder.IRecipeSlotBuilder;
import mezz.jei.api.helpers.IJeiHelpers;
import mezz.jei.api.recipe.IFocusGroup;
import net.minecraft.resources.ResourceLocation;

public class MekanismPigmentPortIngredient extends MekanismChemicalPortIngredient<Pigment, PigmentStack> {

    public MekanismPigmentPortIngredient(ResourceLocation chemical, long amount) {
        super(chemical, amount);
    }

    @Override
    public PigmentStack createStack(Pigment id, long amount) {
        return new PigmentStack(id, amount);
    }

    @Override
    public Pigment findChemical(ResourceLocation id) {
        return MekanismAPI.pigmentRegistry().getValue(id);
    }

    @Override
    public Class<? extends MekanismChemicalPortStorage<Pigment, PigmentStack>> getStorageClass() {
        return MekanismPigmentPortStorage.class;
    }

    @Override
    public ResourceLocation getTypeId() {
        return Ref.Ports.MEK_PIGMENT;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, RecipeModel model, IFocusGroup focus, IJeiHelpers helpers, SlotGrid grid, IRecipeSlotBuilder recipeSlot) {
        var s = createStack(chemical, amount);
        s.setAmount(1000);
        recipeSlot.addIngredient(MekanismJEI.TYPE_PIGMENT, s);
        super.setRecipe(builder, model, focus, helpers, grid, recipeSlot);
    }
}
