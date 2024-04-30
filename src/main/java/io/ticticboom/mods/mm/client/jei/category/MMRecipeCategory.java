package io.ticticboom.mods.mm.client.jei.category;

import io.ticticboom.mods.mm.Ref;
import io.ticticboom.mods.mm.client.jei.SlotGrid;
import io.ticticboom.mods.mm.client.jei.SlotGridEntry;
import io.ticticboom.mods.mm.recipe.RecipeModel;
import io.ticticboom.mods.mm.recipe.input.IRecipeIngredientEntry;
import io.ticticboom.mods.mm.recipe.output.IRecipeOutputEntry;
import io.ticticboom.mods.mm.setup.MMRegisters;
import io.ticticboom.mods.mm.util.WidgetUtils;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableAnimated;
import mezz.jei.api.gui.ingredient.IRecipeSlotView;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IJeiHelpers;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;

import java.util.List;

public class MMRecipeCategory implements IRecipeCategory<RecipeModel> {

    private final IJeiHelpers helpers;
    private final IDrawable bgProgressBar;
    private final IDrawable fgProgressBar;

    public MMRecipeCategory(IJeiHelpers helpers) {
        this.helpers = helpers;
        bgProgressBar = helpers.getGuiHelper().createDrawable(Ref.Textures.SLOT_PARTS, 26, 0, 24, 17);
        var staticProgressBar = helpers.getGuiHelper().createDrawable(Ref.Textures.SLOT_PARTS, 26, 17, 24, 17);
        fgProgressBar = helpers.getGuiHelper().createAnimatedDrawable(staticProgressBar, 16, IDrawableAnimated.StartDirection.LEFT, false);
    }

    public static final RecipeType<RecipeModel> RECIPE_TYPE = RecipeType.create("mm", "recipes", RecipeModel.class);

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

        for (SlotGridEntry inputSlot : recipe.inputSlots()) {
            if (inputSlot.used()) {
                gfx.blit(Ref.Textures.SLOT_PARTS, inputSlot.x, inputSlot.y, 0, 26, 18, 18);
            }
        }
    }
}