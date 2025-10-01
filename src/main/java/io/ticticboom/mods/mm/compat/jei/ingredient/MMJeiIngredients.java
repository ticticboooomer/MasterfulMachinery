package io.ticticboom.mods.mm.compat.jei.ingredient;

import io.ticticboom.mods.mm.compat.jei.ingredient.create.CreateRotationIngredientType;
import io.ticticboom.mods.mm.compat.jei.ingredient.energy.EnergyIngredientType;
import io.ticticboom.mods.mm.compat.jei.ingredient.mana.BotaniaManaIngredientType;
import io.ticticboom.mods.mm.compat.jei.ingredient.pncr.PneumaticAirIngredientType;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.forge.ForgeTypes;
import mezz.jei.api.ingredients.IIngredientType;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public class MMJeiIngredients {
    public static final EnergyIngredientType ENERGY = new EnergyIngredientType();
    public static final IIngredientType<FluidStack> FLUID = ForgeTypes.FLUID_STACK;
    public static final IIngredientType<ItemStack> ITEM = VanillaTypes.ITEM_STACK;
    public static final PneumaticAirIngredientType PNEUMATIC_AIR = new PneumaticAirIngredientType();
    public static final BotaniaManaIngredientType BOTANIA_MANA = new BotaniaManaIngredientType();
    public static final CreateRotationIngredientType CREATE_ROTATION = new CreateRotationIngredientType();
}
