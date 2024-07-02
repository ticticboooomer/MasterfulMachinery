package io.ticticboom.mods.mm.compat.jei.ingredient.pncr;

import io.ticticboom.mods.mm.Ref;
import mezz.jei.api.ingredients.IIngredientRenderer;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.TooltipFlag;

import java.util.ArrayList;
import java.util.List;

public class PneumaticAirIngredientRender  implements IIngredientRenderer<PneumaticAirStack> {

    @Override
    public void render(GuiGraphics guiGraphics, PneumaticAirStack pneumaticAirStack) {
        guiGraphics.blit(Ref.Textures.SLOT_PARTS, 0, 0, 1, 62, 16, 16);
    }

    @Override
    public List<Component> getTooltip(PneumaticAirStack pneumaticAirStack, TooltipFlag tooltipFlag) {
        var result = new ArrayList<Component>();
        result.add(Component.literal("PneumaticCraft Air:"));
        result.add(Component.literal(pneumaticAirStack.air() + " mB"));
        result.add(Component.literal(pneumaticAirStack.pressure() + " Bar"));
        return result;
    }
}
