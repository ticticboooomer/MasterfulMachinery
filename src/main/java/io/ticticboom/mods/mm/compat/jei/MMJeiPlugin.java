package io.ticticboom.mods.mm.compat.jei;

import io.ticticboom.mods.mm.Ref;
import io.ticticboom.mods.mm.block.ControllerBlock;
import io.ticticboom.mods.mm.ports.base.MMPortTypeEntry;
import io.ticticboom.mods.mm.setup.MMRegistries;
import io.ticticboom.mods.mm.setup.model.RecipeModel;
import io.ticticboom.mods.mm.setup.reload.RecipeManager;
import io.ticticboom.mods.mm.util.Deferred;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.helpers.IJeiHelpers;
import mezz.jei.api.registration.IModIngredientRegistration;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

@JeiPlugin
public class MMJeiPlugin implements IModPlugin {
    private Deferred<IJeiHelpers> helpers = new Deferred<>();
    @Override
    public ResourceLocation getPluginUid() {
        return Ref.res("jei_plugin");
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        var recipes =  new ArrayList<RecipeModel>();
        for (Map.Entry<ResourceLocation, RecipeModel> entry : RecipeManager.REGISTRY.entrySet()) {
            recipes.add(entry.getValue());
        }
        registration.addRecipes(MMRecipeCategory.RECIPE_TYPE, recipes);
    }

    @Override
    public void registerIngredients(IModIngredientRegistration registration) {
        for (Map.Entry<ResourceLocation, MMPortTypeEntry> entry : MMRegistries.PORTS.entrySet()) {
            entry.getValue().registerJeiIngredient(registration, this.helpers);
        }
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(new MMRecipeCategory(registration.getJeiHelpers()));
        helpers.set(registration.getJeiHelpers());
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        List<RegistryObject<Block>> controllers = MMRegistries.BLOCKS.getEntries().stream().filter(x -> x.get() instanceof ControllerBlock).toList();
        for (RegistryObject<Block> controller : controllers) {
            registration.addRecipeCatalyst(new ItemStack(controller.get().asItem()), MMRecipeCategory.RECIPE_TYPE);
        }
    }
}
