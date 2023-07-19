package io.ticticboom.mods.mm.compat.jei.recipe;

import com.mojang.blaze3d.vertex.PoseStack;
import io.ticticboom.mods.mm.compat.MMCompatRegistries;
import io.ticticboom.mods.mm.compat.jei.SlotGrid;
import io.ticticboom.mods.mm.compat.jei.SlotGridEntry;
import io.ticticboom.mods.mm.compat.jei.base.JeiRecipeEntry;
import io.ticticboom.mods.mm.recipe.IConfiguredRecipeEntry;
import io.ticticboom.mods.mm.recipe.simple.SimpleConfiguredRecipeEntry;
import io.ticticboom.mods.mm.setup.model.RecipeModel;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IJeiHelpers;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import net.minecraft.network.chat.Component;

public class SimpleJeiRecipeEntry extends JeiRecipeEntry {
    @Override
    public void setRecipe(IConfiguredRecipeEntry entry, IRecipeLayoutBuilder builder, RecipeModel recipe, IFocusGroup focuses, IJeiHelpers helpers, boolean input, int startX, int startY, SlotGrid slots) {
        var sEntry = (SimpleConfiguredRecipeEntry) entry;
        var iType = sEntry.ingredient().type();
        var port = MMCompatRegistries.JEI_PORTS.get().getValue(iType);
        SlotGridEntry next = slots.next();
        var slot = builder.addSlot(input ? RecipeIngredientRole.INPUT : RecipeIngredientRole.OUTPUT, startX + next.x, startY + next.y);
        next.setUsed();
        port.setupRecipeJei(sEntry.ingredient().config(), builder, recipe, focuses, slot, input, startX + next.x, startY + next.y);
        if (sEntry.chance().isPresent()) {
            slot.addTooltipCallback((a, b) -> {
                var chance = sEntry.chance().get() * 100;
                if (input) {
                    b.add(Component.literal("Consume Chance: " + chance + "%"));
                } else {
                    b.add(Component.literal("Output Chance: " + chance + "%"));
                }
            });
        }
    }

    @Override
    public void renderJei(RecipeModel recipe, IRecipeSlotsView recipeSlotsView, PoseStack stack, double mouseX, double mouseY, IConfiguredRecipeEntry entry, IJeiHelpers helpers, boolean input, int x, int y, SlotGrid slots) {
        var conf = (SimpleConfiguredRecipeEntry) entry;
        var port = MMCompatRegistries.JEI_PORTS.get().getValue(conf.ingredient().type());
        var next = slots.next();
        port.renderJei(recipe, recipeSlotsView, stack, mouseX, mouseY, conf.ingredient().config(), helpers, input, x + next.x, y + next.y);
        next.setUsed();
    }
}
