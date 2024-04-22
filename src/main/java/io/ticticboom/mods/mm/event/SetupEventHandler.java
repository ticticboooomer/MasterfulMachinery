package io.ticticboom.mods.mm.event;

import io.ticticboom.mods.mm.datagen.PackEventHandler;
import io.ticticboom.mods.mm.setup.loader.ControllerLoader;
import io.ticticboom.mods.mm.setup.loader.PortLoader;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLConstructModEvent;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class SetupEventHandler {

    @SubscribeEvent
    public static void onConstruction(FMLConstructModEvent event) {
        PackEventHandler.ensureConfigPath();
        ControllerLoader.loadAll();
        PortLoader.loadAll();
    }

}
