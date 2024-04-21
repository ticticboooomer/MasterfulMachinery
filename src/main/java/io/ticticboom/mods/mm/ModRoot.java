package io.ticticboom.mods.mm;

import io.ticticboom.mods.mm.controller.MMControllerRegistry;
import io.ticticboom.mods.mm.ports.MMPortRegistry;
import io.ticticboom.mods.mm.setup.MMRegisters;
import net.minecraftforge.fml.common.Mod;

@Mod(Ref.ID)
public class ModRoot {
    public ModRoot() {
        MMPortRegistry.init();
        MMControllerRegistry.init();
        MMRegisters.register();
    }
}
