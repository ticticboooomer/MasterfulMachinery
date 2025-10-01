package io.ticticboom.mods.mm.compat.jei.category;

import io.ticticboom.mods.mm.Ref;
import io.ticticboom.mods.mm.compat.jei.SlotGrid;
import io.ticticboom.mods.mm.compat.jei.SlotGridEntry;
import io.ticticboom.mods.mm.recipe.RecipeModel;
import io.ticticboom.mods.mm.recipe.input.IRecipeIngredientEntry;
import io.ticticboom.mods.mm.recipe.output.IRecipeOutputEntry;
import io.ticticboom.mods.mm.setup.MMRegisters;
import io.ticticboom.mods.mm.structure.StructureModel;
import io.ticticboom.mods.mm.util.WidgetUtils;
import lombok.Getter;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableAnimated;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IJeiHelpers;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class MMRecipeCategory implements IRecipeCategory<RecipeModel> {

    public static final RecipeType<RecipeModel> RECIPE_TYPE = RecipeType.create(Ref.ID, "recipes", RecipeModel.class);
    private final IJeiHelpers helpers;
    private final IDrawable bgProgressBar;
    @Getter
    private final StructureModel structureModel;
    private final IDrawable fgProgressBar;
    private final RecipeType<RecipeModel> recipeType;

    @Override
    public ResourceLocation getRegistryName(RecipeModel recipe) {
        return recipe.id();
    }

    public MMRecipeCategory(IJeiHelpers helpers, StructureModel parent) {
        this.helpers = helpers;
        bgProgressBar = helpers.getGuiHelper().createDrawable(Ref.Textures.SLOT_PARTS, 26, 0, 24, 17);
        this.structureModel = parent;
        var staticProgressBar = helpers.getGuiHelper().createDrawable(Ref.Textures.SLOT_PARTS, 26, 17, 24, 17);
        fgProgressBar = helpers.getGuiHelper().createAnimatedDrawable(staticProgressBar, 16, IDrawableAnimated.StartDirection.LEFT, false);
        if (structureModel != null) {
            recipeType = RecipeType.create("mm", parent.id().getPath() + "_recipe", RecipeModel.class);
        } else {
            recipeType = RECIPE_TYPE;
        }
    }

    @Override
    public RecipeType<RecipeModel> getRecipeType() {
        return recipeType;
    }

    @Override
    public Component getTitle() {
        if (structureModel != null) {
            return Component.literal(this.structureModel.name()).append(Component.literal(" (Recipes)"));
        } else {
            return Component.literal("MM Recipes");
        }
    }

    @Override
    public IDrawable getBackground() {
        return helpers.getGuiHelper().createBlankDrawable(162, 50);
    }

    @Override
    public IDrawable getIcon() {
        return helpers.getGuiHelper().createDrawableItemStack(MMRegisters.BLUEPRINT.get().getDefaultInstance());
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, RecipeModel recipe, IFocusGroup focuses) {
        var inGrid = new SlotGrid(20, 20, 3, 5, 0, 0);
        var outGrid = new SlotGrid(20, 20, 3, 5, 100, 0);
        for (IRecipeIngredientEntry input : recipe.inputs().inputs()) {
            input.setRecipe(builder, recipe, focuses, helpers, inGrid);
        }
        for (IRecipeOutputEntry output : recipe.outputs().outputs()) {
            output.setRecipe(builder, recipe, focuses, helpers, outGrid);
        }

        recipe.inputSlots().addAll(inGrid.getSlots());
        recipe.inputSlots().addAll(outGrid.getSlots());
    }

    @Override
    public void draw(RecipeModel recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics gfx, double mouseX, double mouseY) {
        bgProgressBar.draw(gfx, 70, 12);
        fgProgressBar.draw(gfx, 70, 12);
        var seconds = (double) recipe.ticks() / 20;
        var fmt = String.format("%.2f", seconds) + "s";

        if (WidgetUtils.isPointerWithinSized((int) mouseX, (int) mouseY, 70, 12, 24, 17)) {
            gfx.renderTooltip(Minecraft.getInstance().font, Component.literal(fmt), (int) mouseX, (int) mouseY);
        }

        if (structureModel == null) {
            gfx.blit(Ref.Textures.SLOT_PARTS, 75, 28, 19, 26, 7, 9);
            if (WidgetUtils.isPointerWithinSized((int) mouseX, (int) mouseY, 75, 28, 7, 9)) {
                gfx.renderTooltip(Minecraft.getInstance().font, Component.literal("Structure: " + recipe.structureId().toString()), (int) mouseX, (int) mouseY);
            }
        }

        for (SlotGridEntry inputSlot : recipe.inputSlots()) {
            if (inputSlot.used()) {
                gfx.blit(Ref.Textures.SLOT_PARTS, inputSlot.x, inputSlot.y, 0, 26, 18, 18);
            }
        }
    }
}