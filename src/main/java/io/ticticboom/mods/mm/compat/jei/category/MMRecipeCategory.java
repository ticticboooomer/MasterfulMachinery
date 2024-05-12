package io.ticticboom.mods.mm.compat.jei.category;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import io.ticticboom.mods.mm.Ref;
import io.ticticboom.mods.mm.compat.jei.SlotGrid;
import io.ticticboom.mods.mm.compat.jei.SlotGridEntry;
import io.ticticboom.mods.mm.recipe.RecipeModel;
import io.ticticboom.mods.mm.recipe.input.IRecipeIngredientEntry;
import io.ticticboom.mods.mm.recipe.output.IRecipeOutputEntry;
import io.ticticboom.mods.mm.setup.MMRegisters;
import io.ticticboom.mods.mm.util.WidgetUtils;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableAnimated;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IJeiHelpers;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
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
    public void draw(RecipeModel recipe, IRecipeSlotsView recipeSlotsView, PoseStack gfx, double mouseX, double mouseY) {
        bgProgressBar.draw(gfx, 70, 12);
        fgProgressBar.draw(gfx, 70, 12);

        for (SlotGridEntry inputSlot : recipe.inputSlots()) {
            if (inputSlot.used()) {
                helpers.getGuiHelper().getSlotDrawable().draw(gfx, inputSlot.x, inputSlot.y);
            }
        }
    }

    @Override
    public List<Component> getTooltipStrings(RecipeModel recipe, IRecipeSlotsView recipeSlotsView, double mouseX, double mouseY) {
        if (WidgetUtils.isPointerWithinSized((int) mouseX, (int) mouseY, 70, 12, 24, 17)) {
            var seconds = (double) recipe.ticks() / 20;
            var fmt = String.format("%.2f", seconds) + "s";
            return List.of(Component.literal(fmt));
        }
        return List.of();
    }
}