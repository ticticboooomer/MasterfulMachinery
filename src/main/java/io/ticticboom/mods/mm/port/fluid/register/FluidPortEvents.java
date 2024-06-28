package io.ticticboom.mods.mm.port.fluid.register;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ContainerScreenEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class FluidPortEvents {

    @SubscribeEvent
    public static void onForeground(ContainerScreenEvent.Render.Foreground event) {
        if (event.getContainerScreen() instanceof FluidPortScreen fps) {
            fps.renderFluids(event.getPoseStack(), event.getMouseX(), event.getMouseY());
        }
    }
}
