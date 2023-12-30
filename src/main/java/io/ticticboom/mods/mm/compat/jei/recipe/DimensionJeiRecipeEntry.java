package io.ticticboom.mods.mm.compat.jei.recipe;

import com.mojang.blaze3d.vertex.PoseStack;
import io.ticticboom.mods.mm.Ref;
import io.ticticboom.mods.mm.compat.jei.SlotGrid;
import io.ticticboom.mods.mm.compat.jei.SlotGridEntry;
import io.ticticboom.mods.mm.compat.jei.base.JeiRecipeEntry;
import io.ticticboom.mods.mm.recipe.IConfiguredRecipeEntry;
import io.ticticboom.mods.mm.recipe.dimension.DimensionConfiguredRecipeEntry;
import io.ticticboom.mods.mm.recipe.dimension.jei.DimIngredientType;
import io.ticticboom.mods.mm.recipe.dimension.jei.DimStack;
import io.ticticboom.mods.mm.setup.model.RecipeModel;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IJeiHelpers;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import net.minecraft.network.chat.Component;

public class DimensionJeiRecipeEntry extends JeiRecipeEntry {
    public static final DimIngredientType ING_TYPE = new DimIngredientType();
    @Override
    public void setRecipe(IConfiguredRecipeEntry entry, IRecipeLayoutBuilder builder, RecipeModel recipe, IFocusGroup focuses, IJeiHelpers helpers, boolean input, int startX, int startY, SlotGrid slots) {
        DimensionConfiguredRecipeEntry conf = (DimensionConfiguredRecipeEntry) entry;
        SlotGridEntry next = slots.next();
        var slot = builder.addSlot(input ? RecipeIngredientRole.INPUT : RecipeIngredientRole.OUTPUT, next.x + startX, next.y + startY);
        slot.addIngredient(ING_TYPE, new DimStack(conf.dimension()));
    }

    @Override
    public void renderJei(RecipeModel recipe, IRecipeSlotsView recipeSlotsView, PoseStack stack, double mouseX, double mouseY, IConfiguredRecipeEntry entry, IJeiHelpers helpers, boolean input, int x, int y, SlotGrid slots) {
        helpers.getGuiHelper().getSlotDrawable().draw(stack, x - 1, y - 1);
        helpers.getGuiHelper().createDrawable(Ref.SLOT_PARTS, 19, 98, 16, 16).draw(stack, x, y);
    }
}
