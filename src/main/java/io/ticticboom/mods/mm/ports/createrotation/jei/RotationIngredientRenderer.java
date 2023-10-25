package io.ticticboom.mods.mm.ports.createrotation.jei;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.vertex.PoseStack;
import io.ticticboom.mods.mm.Ref;
import io.ticticboom.mods.mm.util.Deferred;
import mezz.jei.api.helpers.IJeiHelpers;
import mezz.jei.api.ingredients.IIngredientRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.TooltipFlag;

import java.text.NumberFormat;
import java.util.List;

public class RotationIngredientRenderer implements IIngredientRenderer<RotationStack> {

    public Deferred<IJeiHelpers> helpers;

    public RotationIngredientRenderer(Deferred<IJeiHelpers> helpers) {

        this.helpers = helpers;
    }

    @Override
    public List<Component> getTooltip(RotationStack ingredient, TooltipFlag tooltipFlag) {
        return Lists.newArrayList(
                Component.literal("Create Rotation"),
                Component.literal("Speed: " + NumberFormat.getInstance().format(ingredient.speed) + " RPM"));
    }

    @Override
    public void render(PoseStack stack, RotationStack ingredient) {
        helpers.data.getGuiHelper().createDrawable(Ref.SLOT_PARTS, 1, 98, 16, 16).draw(stack);
    }
}
