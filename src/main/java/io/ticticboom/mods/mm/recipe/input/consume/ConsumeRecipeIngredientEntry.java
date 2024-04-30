package io.ticticboom.mods.mm.recipe.input.consume;

import io.ticticboom.mods.mm.client.jei.SlotGrid;
import io.ticticboom.mods.mm.client.jei.SlotGridEntry;
import io.ticticboom.mods.mm.port.IPortIngredient;
import io.ticticboom.mods.mm.recipe.RecipeModel;
import io.ticticboom.mods.mm.recipe.RecipeStateModel;
import io.ticticboom.mods.mm.recipe.RecipeStorages;
import io.ticticboom.mods.mm.recipe.input.IRecipeIngredientEntry;
import io.ticticboom.mods.mm.util.ChanceUtils;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.helpers.IJeiHelpers;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.Level;

import java.util.Optional;
import java.util.Random;
import java.util.UUID;

public class ConsumeRecipeIngredientEntry implements IRecipeIngredientEntry {

    private final IPortIngredient ingredient;
    private final double chance;
    private final boolean perTick;

    private boolean shouldRun = true;

    public ConsumeRecipeIngredientEntry(IPortIngredient ingredient, double chance, boolean perTick) {
        this.ingredient = ingredient;
        this.chance = chance;
        this.perTick = perTick;
    }

    @Override
    public boolean canProcess(Level level, RecipeStorages storages, RecipeStateModel state) {
        shouldRun = ChanceUtils.shouldProceed(chance);
        return ingredient.canProcess(level, storages);
    }

    @Override
    public void process(Level level, RecipeStorages storages, RecipeStateModel state) {
        if (!perTick && shouldRun) {
            ingredient.process(level, storages);
        }
    }

    @Override
    public void processTick(Level level, RecipeStorages storages, RecipeStateModel state) {
        if (perTick && shouldRun) {
            ingredient.process(level, storages);
        }
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, RecipeModel model, IFocusGroup focus, IJeiHelpers helpers, SlotGrid grid) {
        SlotGridEntry slot = grid.next();
        var rSlot = builder.addSlot(RecipeIngredientRole.INPUT, slot.x, slot.y);
        slot.setUsed();
        ingredient.setRecipe(builder, model, focus, helpers, grid, rSlot);
        var fmtChance = String.format("%.2f", chance * 100) + "% Chance of Consumption";
        rSlot.addTooltipCallback((v, list) -> {
            if (chance < 1) {
                list.add(Component.literal(fmtChance).withStyle(ChatFormatting.ITALIC));
            }
            if (perTick) {
                list.add(Component.literal("Consumed Per Tick").withStyle(ChatFormatting.ITALIC));
            }
        });
    }
}
