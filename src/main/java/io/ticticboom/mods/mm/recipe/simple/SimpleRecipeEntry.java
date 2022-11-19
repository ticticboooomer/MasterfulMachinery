package io.ticticboom.mods.mm.recipe.simple;

import com.google.gson.JsonObject;
import com.mojang.blaze3d.vertex.PoseStack;
import io.ticticboom.mods.mm.ports.base.MMPortTypeEntry;
import io.ticticboom.mods.mm.recipe.IConfiguredRecipeEntry;
import io.ticticboom.mods.mm.recipe.MMRecipeEntry;
import io.ticticboom.mods.mm.recipe.RecipeContext;
import io.ticticboom.mods.mm.setup.MMRegistries;
import io.ticticboom.mods.mm.setup.model.RecipeModel;
import io.ticticboom.mods.mm.util.RecipeHelper;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IJeiHelpers;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;

import java.util.Optional;
import java.util.Random;

public class SimpleRecipeEntry extends MMRecipeEntry {
    private final Random rand = new Random();

    @Override
    public IConfiguredRecipeEntry parse(JsonObject json) {
        var ingredient = RecipeHelper.parseIngredient(json.get("ingredient").getAsJsonObject());
        Optional<Float> chance = Optional.empty();
        if (json.has("chance")) {
            chance = Optional.of(json.get("chance").getAsFloat());
        }
        return new SimpleConfiguredRecipeEntry(ingredient, chance);
    }

    @Override
    public boolean processInputs(IConfiguredRecipeEntry config, RecipeContext ctx) {
        var conf = (SimpleConfiguredRecipeEntry) config;
        if (!roll(conf.chance())) {
            return true;
        }
        ResourceLocation type = conf.ingredient().type();
        MMPortTypeEntry port = MMRegistries.PORTS.get(type);
        return port.processInputs(conf.ingredient().config(), ctx.inputPorts());
    }

    @Override
    public boolean processOutputs(IConfiguredRecipeEntry config, RecipeContext ctx) {
        var conf = (SimpleConfiguredRecipeEntry) config;
        if (!roll(conf.chance())) {
            return true;
        }
        ResourceLocation type = conf.ingredient().type();
        MMPortTypeEntry port = MMRegistries.PORTS.get(type);
        return port.processOutputs(conf.ingredient().config(), ctx.outputPorts());
    }

    @Override
    public void setRecipe(IConfiguredRecipeEntry entry, IRecipeLayoutBuilder builder, RecipeModel recipe, IFocusGroup focuses, IJeiHelpers helpers, boolean input, int startX, int startY) {
        var sEntry = (SimpleConfiguredRecipeEntry) entry;
        var iType = sEntry.ingredient().type();
        var port = MMRegistries.PORTS.get(iType);
        var slot = builder.addSlot(input ? RecipeIngredientRole.INPUT : RecipeIngredientRole.OUTPUT, startX, startY);
        port.setupRecipeJei(sEntry.ingredient().config(), builder, recipe, focuses, slot, input, startX, startY);
        if (sEntry.chance().isPresent()) {
            slot.addTooltipCallback((a, b) -> {
                var chance = sEntry.chance().get() * 100;
                if (input) {
                    b.add(new TextComponent("Consume Chance: " + chance + "%"));
                } else {
                    b.add(new TextComponent("Output Chance: " + chance + "%"));
                }
            });
        }
    }

    @Override
    public void renderJei(RecipeModel recipe, IRecipeSlotsView recipeSlotsView, PoseStack stack, double mouseX, double mouseY, IConfiguredRecipeEntry entry, IJeiHelpers helpers, boolean input, int x, int y) {
        var conf = (SimpleConfiguredRecipeEntry) entry;
        var port = MMRegistries.PORTS.get(conf.ingredient().type());
        port.renderJei(recipe, recipeSlotsView, stack, mouseX, mouseY, conf.ingredient().config(), helpers, input, x, y);
    }

    protected boolean roll(Optional<Float> chance) {
        if (chance.isPresent()) {
            var random = rand.nextFloat();
            return random <= chance.get();
        }
        return true;
    }
}
