package io.ticticboom.mods.mm.client.jei.ingredient;

import io.ticticboom.mods.mm.client.jei.ingredient.energy.EnergyIngredientType;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.forge.ForgeTypes;
import mezz.jei.api.ingredients.IIngredientType;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public class MMJeiIngredients {
    public static final EnergyIngredientType ENERGY = new EnergyIngredientType();
    public static final IIngredientType<FluidStack> FLUID = ForgeTypes.FLUID_STACK;
    public static final IIngredientType<ItemStack> ITEM = VanillaTypes.ITEM_STACK;
}
