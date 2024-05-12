package io.ticticboom.mods.mm.compat.jei.ingredient.energy;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import io.ticticboom.mods.mm.Ref;
import mezz.jei.api.ingredients.IIngredientRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.TooltipFlag;

import java.util.ArrayList;
import java.util.List;

public class EnergyIngredientRenderer implements IIngredientRenderer<EnergyStack> {

    @Override
    public void render(PoseStack gfx, EnergyStack ingredient) {
        RenderSystem.setShaderTexture(0, Ref.Textures.SLOT_PARTS);
        GuiComponent.blit(gfx, 0, 0, 19, 62, 16, 16, 256, 256);
    }

    @Override
    public List<Component> getTooltip(EnergyStack ingredient, TooltipFlag tooltipFlag) {
        var result = new ArrayList<Component>();
        result.add(Component.literal(ingredient.amount() + " FE"));
        return result;
    }
}
