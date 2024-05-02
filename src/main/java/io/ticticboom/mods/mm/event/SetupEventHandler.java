package io.ticticboom.mods.mm.event;

import io.ticticboom.mods.mm.Ref;
import io.ticticboom.mods.mm.cap.IScannerSelection;
import io.ticticboom.mods.mm.cap.ScannerSelectionProvider;
import io.ticticboom.mods.mm.controller.MMControllerRegistry;
import io.ticticboom.mods.mm.datagen.PackEventHandler;
import io.ticticboom.mods.mm.foundation.scanner.StructureScannerScreen;
import io.ticticboom.mods.mm.port.MMPortRegistry;
import io.ticticboom.mods.mm.setup.MMRegisters;
import io.ticticboom.mods.mm.setup.RegistryGroupHolder;
import io.ticticboom.mods.mm.setup.loader.ControllerLoader;
import io.ticticboom.mods.mm.setup.loader.ExtraBlockLoader;
import io.ticticboom.mods.mm.setup.loader.PortLoader;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLConstructModEvent;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class SetupEventHandler {

    @SubscribeEvent
    public static void onConstruction(FMLConstructModEvent event) {
        PackEventHandler.ensureConfigPath();
        ControllerLoader.loadAll();
        PortLoader.loadAll();
        ExtraBlockLoader.loadAll();
    }

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            for (RegistryGroupHolder controller : MMControllerRegistry.CONTROLLERS) {
                ResourceLocation type = controller.getRegistryId();
                MMControllerRegistry.get(type).registerScreen(controller);
            }

            for (RegistryGroupHolder port : MMPortRegistry.PORTS) {
                ResourceLocation type = port.getRegistryId();
                MMPortRegistry.get(type).registerScreen(port);
            }

            MenuScreens.register(MMRegisters.SCANNER_BLOCK_MENU.get(), StructureScannerScreen::new);
        });
    }


    @SubscribeEvent
    public static void registerCaps(RegisterCapabilitiesEvent event) {
        event.register(IScannerSelection.class);
    }



}
