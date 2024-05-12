package io.ticticboom.mods.mm.port.fluid.register;

import com.mojang.blaze3d.vertex.PoseStack;
import io.ticticboom.mods.mm.client.FluidRenderer;
import io.ticticboom.mods.mm.port.common.SlottedContainerScreen;
import io.ticticboom.mods.mm.util.WidgetUtils;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.phys.Vec2;
import net.minecraftforge.client.event.ContainerScreenEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;

public class FluidPortScreen extends SlottedContainerScreen<FluidPortMenu> {
    public FluidPortScreen(FluidPortMenu menu, Inventory p_97742_, Component p_97743_) {
        super(menu, p_97742_, p_97743_);
    }

    public void renderFluids(PoseStack gfx, int mouseX, int mouseY) {
        int i = 0;
        for (Vec2 slot : slots) {
            int slotX = (int) slot.x + 1;
            int slotY = (int) slot.y + 1;
            var stack = menu.getStackInSlot(i);
            var color = 0x80ffffff;
            FluidRenderer.INSTANCE.render(gfx, slotX, slotY, stack, 16);
            if (WidgetUtils.isPointerWithinSized(mouseX, mouseY, this.leftPos + slotX, this.topPos + slotY, 16, 16)) {
                GuiComponent.fillGradient(gfx, slotX, slotY, slotX + 16, slotY + 16, color, color, 99);
                if (!stack.isEmpty()) {
                    var tooltip = new ArrayList<Component>();
                    tooltip.add(stack.getDisplayName().copy().append(": " + stack.getAmount() + "mB"));
                    this.renderComponentTooltip(gfx, tooltip, mouseX - this.leftPos, mouseY - this.topPos);
                }
            }
            i++;
        }
    }

    @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
    public static class Handler {

        @SubscribeEvent
        public static void onForeground(ContainerScreenEvent.Render.Foreground event) {
            if (event.getContainerScreen() instanceof FluidPortScreen fps) {
                fps.renderFluids(event.getPoseStack(), event.getMouseX(), event.getMouseY());
            }
        }
    }
}
