package io.ticticboom.mods.mm.compat.jei.ingredient.energy;

import io.ticticboom.mods.mm.Ref;
import mezz.jei.api.ingredients.IIngredientRenderer;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.TooltipFlag;

import java.util.ArrayList;
import java.util.List;

public class EnergyIngredientRenderer implements IIngredientRenderer<EnergyStack> {

    @Override
    public void render(GuiGraphics gfx, EnergyStack ingredient) {
        gfx.blit(Ref.Textures.SLOT_PARTS, 0, 0, 19, 62, 16, 16);
    }

    @Override
    public List<Component> getTooltip(EnergyStack ingredient, TooltipFlag tooltipFlag) {
        var result = new ArrayList<Component>();
        result.add(Component.literal(ingredient.amount() + " FE"));
        return result;
    }
}
