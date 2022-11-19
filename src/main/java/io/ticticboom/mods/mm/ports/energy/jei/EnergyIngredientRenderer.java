package io.ticticboom.mods.mm.ports.energy.jei;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.vertex.PoseStack;
import io.ticticboom.mods.mm.Ref;
import io.ticticboom.mods.mm.util.Deferred;
import mezz.jei.api.gui.drawable.IDrawableStatic;
import mezz.jei.api.helpers.IJeiHelpers;
import mezz.jei.api.ingredients.IIngredientRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.item.TooltipFlag;

import java.text.NumberFormat;
import java.util.List;

public class EnergyIngredientRenderer implements IIngredientRenderer<EnergyStack> {

    public Deferred<IJeiHelpers> helpers;

    public EnergyIngredientRenderer(Deferred<IJeiHelpers> helpers) {
        this.helpers = helpers;
    }

    @Override
    public List<Component> getTooltip(EnergyStack ingredient, TooltipFlag tooltipFlag) {
        return Lists.newArrayList(
                new TextComponent("Forge Energy"),
                new TextComponent(NumberFormat.getInstance().format(ingredient.amount) + " FE"));
    }


    @Override
    public void render(PoseStack stack, EnergyStack ingredient) {
        helpers.data.getGuiHelper().createDrawable(Ref.SLOT_PARTS, 19, 62, 16, 16).draw(stack);
    }
}
