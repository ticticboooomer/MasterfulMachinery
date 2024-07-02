package io.ticticboom.mods.mm.compat.jei.ingredient.mana;

import io.ticticboom.mods.mm.Ref;
import mezz.jei.api.ingredients.IIngredientRenderer;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

public class BotaniaManaIngredientRenderer implements IIngredientRenderer<BotaniaManaStack> {


    @Override
    public void render(GuiGraphics guiGraphics, BotaniaManaStack stack) {
        guiGraphics.blit(Ref.Textures.SLOT_PARTS, 0, 0, 18, 79, 18, 18);
    }

    @Override
    public List<Component> getTooltip(BotaniaManaStack stack, TooltipFlag tooltipFlag) {
        return List.of(
                Component.literal("Botania Mana:"),
                Component.literal(stack.mana() + " Mana")
        );
    }
}
