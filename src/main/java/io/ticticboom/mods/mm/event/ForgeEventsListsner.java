package io.ticticboom.mods.mm.event;

import io.ticticboom.mods.mm.Ref;
import io.ticticboom.mods.mm.cap.ScannerSelectionProvider;
import io.ticticboom.mods.mm.recipe.MachineRecipeManager;
import io.ticticboom.mods.mm.setup.MMRegisters;
import io.ticticboom.mods.mm.structure.StructureManager;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ForgeEventsListsner {

    @SubscribeEvent
    public static void registerReloadListeners(AddReloadListenerEvent event) {
        event.addListener(new StructureManager());
        event.addListener(new MachineRecipeManager());
    }

    @SubscribeEvent
    public static void attachCaps(AttachCapabilitiesEvent<ItemStack> event) {
        if (event.getObject().is(MMRegisters.SCANNER_TOOL.get())) {
            event.addCapability(Ref.SCANNER_CAP, new ScannerSelectionProvider());
        }
    }
}
