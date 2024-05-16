package io.ticticboom.mods.mm.port.mekanism.infuse;

import io.ticticboom.mods.mm.Ref;
import io.ticticboom.mods.mm.compat.jei.SlotGrid;
import io.ticticboom.mods.mm.port.mekanism.chemical.MekanismChemicalPortIngredient;
import io.ticticboom.mods.mm.port.mekanism.chemical.MekanismChemicalPortStorage;
import io.ticticboom.mods.mm.recipe.RecipeModel;
import mekanism.api.MekanismAPI;
import mekanism.api.chemical.infuse.InfuseType;
import mekanism.api.chemical.infuse.InfusionStack;
import mekanism.client.jei.MekanismJEI;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.builder.IRecipeSlotBuilder;
import mezz.jei.api.helpers.IJeiHelpers;
import mezz.jei.api.recipe.IFocusGroup;
import net.minecraft.resources.ResourceLocation;

public class MekanismInfusePortIngredient extends MekanismChemicalPortIngredient<InfuseType, InfusionStack> {

    public MekanismInfusePortIngredient(ResourceLocation chemical, long amount) {
        super(chemical, amount);
    }

    @Override
    public InfusionStack createStack(InfuseType id, long amount) {
        return new InfusionStack(id, amount);
    }

    @Override
    public InfuseType findChemical(ResourceLocation id) {
        return MekanismAPI.infuseTypeRegistry().getValue(id);
    }

    @Override
    public Class<? extends MekanismChemicalPortStorage<InfuseType, InfusionStack>> getStorageClass() {
        return MekanismInfusePortStorage.class;
    }

    @Override
    public ResourceLocation getTypeId() {
        return Ref.Ports.MEK_INFUSE;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, RecipeModel model, IFocusGroup focus, IJeiHelpers helpers, SlotGrid grid, IRecipeSlotBuilder recipeSlot) {
        var s = createStack(chemical, amount);
        s.setAmount(1000);
        recipeSlot.addIngredient(MekanismJEI.TYPE_INFUSION, s);
        super.setRecipe(builder, model, focus, helpers, grid, recipeSlot);
    }
}
