package io.ticticboom.mods.mm.event;

import io.ticticboom.mods.mm.port.IPortItem;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ItemTooltipEventHandler {
    @SubscribeEvent
    public static void onItemTooltip(final ItemTooltipEvent event) {
        var stack = event.getItemStack();
        if (stack.getItem() instanceof IPortItem pi) {
            Component typeName = pi.getTypeName();
            event.getToolTip().add(1, typeName);
        }
    }
}
