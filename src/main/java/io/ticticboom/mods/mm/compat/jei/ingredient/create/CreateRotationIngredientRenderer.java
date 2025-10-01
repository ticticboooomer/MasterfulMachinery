package io.ticticboom.mods.mm.compat.jei.ingredient.create;

import io.ticticboom.mods.mm.Ref;
import mezz.jei.api.ingredients.IIngredientRenderer;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

public class CreateRotationIngredientRenderer implements IIngredientRenderer<CreateRotationStack> {

    @Override
    public void render(GuiGraphics guiGraphics, CreateRotationStack createRotationStack) {
        guiGraphics.blit(Ref.Textures.SLOT_PARTS, 0, 0, 1, 98, 16, 16);
    }

    @Override
    public List<Component> getTooltip(CreateRotationStack createRotationStack, TooltipFlag tooltipFlag) {
        return List.of(Component.literal("Speed: " + createRotationStack.speed()));
    }
}
