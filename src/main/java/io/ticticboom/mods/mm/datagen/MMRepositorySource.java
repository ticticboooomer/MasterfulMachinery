package io.ticticboom.mods.mm.datagen;

import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraft.server.packs.repository.RepositorySource;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraftforge.fml.loading.FMLPaths;

import java.nio.file.Path;
import java.util.function.Consumer;

public class MMRepositorySource implements RepositorySource {

    private final MMRepoType type;
    public static final Path CONFIG_DIR = FMLPaths.CONFIGDIR.get().resolve("mm/pack");

    public MMRepositorySource(MMRepoType type) {
        this.type = type;
    }

    @Override
    public void loadPacks(Consumer<Pack> consumer) {
        var pack = Pack.create(type.getNameId(), type.nameComponent(), true, createPackSupplier(CONFIG_DIR), createPackInfo(), type.type, Pack.Position.TOP, false, PackSource.DEFAULT);
        consumer.accept(pack);
    }

    private Pack.ResourcesSupplier createPackSupplier(Path configDir) {
        return (a) -> {
            DataGenManager.generate();
            return new GeneratedPack(type.getNameId(), false, type.getPath(configDir));
        };
    }

    private Pack.Info createPackInfo() {
        return new Pack.Info(type.nameComponent(), 15, 15, FeatureFlagSet.of(), false);
    }
}
