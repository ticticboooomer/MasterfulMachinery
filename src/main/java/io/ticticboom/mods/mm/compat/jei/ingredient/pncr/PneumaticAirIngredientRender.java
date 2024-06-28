package io.ticticboom.mods.mm.compat.jei.ingredient.pncr;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import io.ticticboom.mods.mm.Ref;
import mezz.jei.api.ingredients.IIngredientRenderer;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.TooltipFlag;

import java.util.ArrayList;
import java.util.List;

public class PneumaticAirIngredientRender  implements IIngredientRenderer<PneumaticAirStack> {

    @Override
    public void render(PoseStack pose, PneumaticAirStack pneumaticAirStack) {
        RenderSystem.setShaderTexture(0, Ref.Textures.SLOT_PARTS);
        GuiComponent.blit(pose, 0, 0, 1, 62, 16, 16, 256, 256);
    }

    @Override
    public List<Component> getTooltip(PneumaticAirStack pneumaticAirStack, TooltipFlag tooltipFlag) {
        var result = new ArrayList<Component>();
        result.add(Component.literal("pneumaticcraft air:"));
        result.add(Component.literal(pneumaticAirStack.air() + " mB"));
        result.add(Component.literal(pneumaticAirStack.pressure() + " Bar"));
        return result;
    }
}
