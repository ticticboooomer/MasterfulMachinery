package io.ticticboom.mods.mm.capability;

import io.ticticboom.mods.mm.Ref;
import io.ticticboom.mods.mm.capability.wand.ISelectionWand;
import io.ticticboom.mods.mm.capability.wand.SelectionWandProvider;
import io.ticticboom.mods.mm.setup.MMRegistries;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class CapEvents {
    @SubscribeEvent
    public static void registerCaps(RegisterCapabilitiesEvent event) {
        event.register(ISelectionWand.class);
    }


    @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
    public static class ForgeEvents {
        @SubscribeEvent
        public static void attach(AttachCapabilitiesEvent<ItemStack> event) {
            if (event.getObject().is(MMRegistries.STRUCTURE_GEN_WAND.get())) {
                event.addCapability(Ref.res("selection_wand"), new SelectionWandProvider());
            }
        }
    }
}
