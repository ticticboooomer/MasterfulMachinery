package io.ticticboom.mods.mm.datagen;

import lombok.SneakyThrows;
import net.minecraft.server.packs.PackType;
import net.minecraftforge.event.AddPackFindersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.loading.FMLPaths;

import java.nio.file.Files;
import java.nio.file.Path;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class PackEventHandler {

    @SubscribeEvent
    public static void on(AddPackFindersEvent event) {
        if (event.getPackType() == PackType.SERVER_DATA) {
            event.addRepositorySource(new MMRepositorySource(MMRepoType.DATA));
        } else {
            event.addRepositorySource(new MMRepositorySource(MMRepoType.RESOURCES));
        }
    }

    @SneakyThrows
    public static void ensureConfigPath() {
        if (!Files.exists(MMRepositorySource.CONFIG_DIR)) {
            Files.createDirectories(MMRepositorySource.CONFIG_DIR);
        }
    }
}
