package io.ticticboom.mods.mm.port.mekanism.slurry;

import io.ticticboom.mods.mm.Ref;
import io.ticticboom.mods.mm.compat.jei.SlotGrid;
import io.ticticboom.mods.mm.port.mekanism.chemical.MekanismChemicalPortIngredient;
import io.ticticboom.mods.mm.port.mekanism.chemical.MekanismChemicalPortStorage;
import io.ticticboom.mods.mm.recipe.RecipeModel;
import mekanism.api.MekanismAPI;
import mekanism.api.chemical.slurry.Slurry;
import mekanism.api.chemical.slurry.SlurryStack;
import mekanism.client.jei.MekanismJEI;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.builder.IRecipeSlotBuilder;
import mezz.jei.api.helpers.IJeiHelpers;
import mezz.jei.api.recipe.IFocusGroup;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class MekanismSlurryPortIngredient extends MekanismChemicalPortIngredient<Slurry, SlurryStack> {

    public MekanismSlurryPortIngredient(ResourceLocation chemical, long amount) {
        super(chemical, amount);
    }

    @Override
    public SlurryStack createStack(Slurry id, long amount) {
        return new SlurryStack(id, amount);
    }

    @Override
    public Slurry findChemical(ResourceLocation id) {
        return MekanismAPI.slurryRegistry().getValue(id);
    }

    @Override
    public Class<? extends MekanismChemicalPortStorage<Slurry, SlurryStack>> getStorageClass() {
        return MekanismSlurryPortStorage.class;
    }

    @Override
    public ResourceLocation getTypeId() {
        return Ref.Ports.MEK_SLURRY;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, RecipeModel model, IFocusGroup focus, IJeiHelpers helpers, SlotGrid grid, IRecipeSlotBuilder recipeSlot) {
        var s = createStack(chemical, amount);
        s.setAmount(1000);
        recipeSlot.addIngredient(MekanismJEI.TYPE_SLURRY, s);
        super.setRecipe(builder, model, focus, helpers, grid, recipeSlot);
    }
}
