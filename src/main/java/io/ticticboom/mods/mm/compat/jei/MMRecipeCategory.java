package io.ticticboom.mods.mm.compat.jei;

import com.mojang.blaze3d.vertex.PoseStack;
import io.ticticboom.mods.mm.Ref;
import io.ticticboom.mods.mm.compat.MMCompatRegistries;
import io.ticticboom.mods.mm.setup.model.RecipeModel;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IJeiHelpers;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;

public class MMRecipeCategory implements IRecipeCategory<RecipeModel> {
    public static final RecipeType<RecipeModel> RECIPE_TYPE = RecipeType.create("mm", "recipes", RecipeModel.class);
    private final IJeiHelpers helpers;

    public MMRecipeCategory(IJeiHelpers helpers) {
        this.helpers = helpers;
    }

    @Override
    public RecipeType<RecipeModel> getRecipeType() {
        return RECIPE_TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.literal("MM Recipe");
    }

    @Override
    public IDrawable getBackground() {
        return helpers.getGuiHelper().createBlankDrawable(162, 100);
    }

    @Override
    public IDrawable getIcon() {
        return null;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, RecipeModel recipe, IFocusGroup focuses) {
        var inputs = new SlotGrid(20, 20, 3, 5);
        for (RecipeModel.RecipeEntry input : recipe.inputs()) {
            var entry = MMCompatRegistries.JEI_RECIPE_ENTRIES.get().getValue(input.type());
            if (entry == null) {
                continue;
            }
            entry.setRecipe(input.config(), builder, recipe, focuses, helpers, true, 0, 0, inputs);
        }
        var outputs = new SlotGrid(20, 20, 3, 5);
        for (RecipeModel.RecipeEntry output : recipe.outputs()) {
            var entry = MMCompatRegistries.JEI_RECIPE_ENTRIES.get().getValue(output.type());
            if (entry == null) {
                continue;
            }
            entry.setRecipe(output.config(), builder, recipe, focuses, helpers, false, 100, 0, outputs);
        }
    }

    @Override
    public void draw(RecipeModel recipe, IRecipeSlotsView recipeSlotsView, PoseStack stack, double mouseX, double mouseY) {
        var inputs = new SlotGrid(20, 20, 3, 5);
        for (RecipeModel.RecipeEntry input : recipe.inputs()) {
            var entry = MMCompatRegistries.JEI_RECIPE_ENTRIES.get().getValue(input.type());
            if (entry == null) {
                continue;
            }
            entry.renderJei(recipe, recipeSlotsView, stack, mouseX, mouseY, input.config(), helpers, true, 0, 0, inputs);
        }
        var outputs = new SlotGrid(20, 20, 3, 5);
        for (RecipeModel.RecipeEntry output : recipe.outputs()) {
            var entry = MMCompatRegistries.JEI_RECIPE_ENTRIES.get().getValue(output.type());
            if (entry == null) {
                continue;
            }
            entry.renderJei(recipe, recipeSlotsView, stack, mouseX, mouseY, output.config(), helpers, false, 100, 0, outputs);
        }
        helpers.getGuiHelper().createDrawable(Ref.SLOT_PARTS, 26, 0, 24, 17).draw(stack, 70, 5);
        Minecraft.getInstance().font.draw(stack, String.valueOf(recipe.duration()), 75, 25, 0x474747);
        Minecraft.getInstance().font.draw(stack, "Ticks", 70, 35, 0x474747);
    }
}
