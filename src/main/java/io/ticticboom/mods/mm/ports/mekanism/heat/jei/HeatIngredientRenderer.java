package io.ticticboom.mods.mm.ports.mekanism.heat.jei;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.vertex.PoseStack;
import io.ticticboom.mods.mm.Ref;
import io.ticticboom.mods.mm.util.Deferred;
import mezz.jei.api.helpers.IJeiHelpers;
import mezz.jei.api.ingredients.IIngredientRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

public class HeatIngredientRenderer implements IIngredientRenderer<HeatStack> {
    public Deferred<IJeiHelpers> helpers;

    public HeatIngredientRenderer(Deferred<IJeiHelpers> helpers) {
        this.helpers = helpers;
    }

    @Override
    public void render(PoseStack stack, HeatStack ingredient) {
        helpers.data.getGuiHelper().createDrawable(Ref.SLOT_PARTS, 0, 79, 16, 16).draw(stack);
    }

    @Override
    public List<Component> getTooltip(HeatStack ingredient, TooltipFlag tooltipFlag) {
        return Lists.newArrayList(
                Component.literal(ingredient.amount() + " Heat Units"),
                Component.literal("Mekanism Heat"));
    }
}
