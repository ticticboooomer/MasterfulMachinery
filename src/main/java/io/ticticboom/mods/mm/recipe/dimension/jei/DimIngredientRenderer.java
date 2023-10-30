package io.ticticboom.mods.mm.recipe.dimension.jei;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.vertex.PoseStack;
import mezz.jei.api.ingredients.IIngredientRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

public class DimIngredientRenderer implements IIngredientRenderer<DimStack> {
    @Override
    public void render(PoseStack stack, DimStack ingredient) {

    }

    @Override
    public List<Component> getTooltip(DimStack ingredient, TooltipFlag tooltipFlag) {
        return Lists.newArrayList(Component.literal("Can be executed in dimension: "), Component.literal(ingredient.dim().toString()));
    }
}
