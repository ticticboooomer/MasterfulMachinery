package io.ticticboom.mods.mm.datagen;

import net.minecraft.network.chat.Component;
import net.minecraft.server.packs.PackResources;
import net.minecraft.server.packs.metadata.pack.PackMetadataSection;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraft.server.packs.repository.RepositorySource;
import net.minecraftforge.fml.loading.FMLPaths;

import java.nio.file.Path;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class MMRepositorySource implements RepositorySource {

    private final MMRepoType type;
    public static final Path CONFIG_DIR = FMLPaths.CONFIGDIR.get().resolve("mm/pack");

    public MMRepositorySource(MMRepoType type) {
        this.type = type;
    }

    private Supplier<PackResources> createPackSupplier(Path configDir) {
        return () -> {
            DataGenManager.generate();
            return new GeneratedPack(type.getNameId(), type.getPath(configDir));
        };
    }

    @Override
    public void loadPacks(Consumer<Pack> consumer, Pack.PackConstructor packConstructor) {
        var pack = Pack.create(type.getNameId(), true, createPackSupplier(CONFIG_DIR), packConstructor, Pack.Position.TOP, PackSource.DEFAULT);
        consumer.accept(pack);
    }
}
