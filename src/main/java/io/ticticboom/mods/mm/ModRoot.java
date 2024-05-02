package io.ticticboom.mods.mm;

import io.ticticboom.mods.mm.extra.MMExtraBlockRegistry;
import io.ticticboom.mods.mm.controller.MMControllerRegistry;
import io.ticticboom.mods.mm.datagen.DataGenManager;
import io.ticticboom.mods.mm.datagen.MMRepoType;
import io.ticticboom.mods.mm.datagen.MMRepositorySource;
import io.ticticboom.mods.mm.port.MMPortRegistry;
import io.ticticboom.mods.mm.setup.MMRegisters;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.loading.FMLEnvironment;

@Mod(Ref.ID)
public class ModRoot {

    public ModRoot() {
        MMPortRegistry.init();
        MMControllerRegistry.init();
        MMExtraBlockRegistry.init();
        MMRegisters.register();
        DataGenManager.registerDataProviders();
        registerClientPack();
    }

    private void registerClientPack() {
        try {
            if (FMLEnvironment.dist == Dist.CLIENT) {
                Minecraft.getInstance().getResourcePackRepository()
                        .addPackFinder(new MMRepositorySource(MMRepoType.RESOURCES));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
