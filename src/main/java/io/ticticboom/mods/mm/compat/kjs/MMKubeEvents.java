package io.ticticboom.mods.mm.compat.kjs;

import dev.latvian.mods.kubejs.bindings.event.ItemEvents;
import dev.latvian.mods.kubejs.event.EventGroup;
import dev.latvian.mods.kubejs.event.EventHandler;
import io.ticticboom.mods.mm.compat.kjs.event.ControllerEventJS;
import io.ticticboom.mods.mm.compat.kjs.event.PortEventJS;
import net.minecraftforge.fml.ModList;

public interface MMKubeEvents {
    EventGroup GROUP = EventGroup.of("MMEvents");

    EventHandler CONTROLLERS = GROUP.startup("registerControllers", () -> ControllerEventJS.class);
    EventHandler PORTS = GROUP.startup("registerPorts", () -> PortEventJS.class);


    static void register() {
        ItemEvents.GROUP.register();
    }

    static boolean isLoaded() {
        return ModList.get().isLoaded("kubejs");
    }
}
