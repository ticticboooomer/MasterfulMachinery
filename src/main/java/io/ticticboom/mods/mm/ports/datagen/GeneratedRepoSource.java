package io.ticticboom.mods.mm.ports.datagen;

import net.minecraft.network.chat.TextComponent;
import net.minecraft.server.packs.metadata.pack.PackMetadataSection;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraft.server.packs.repository.RepositorySource;

import java.nio.file.Path;
import java.util.function.Consumer;

public class GeneratedRepoSource implements RepositorySource {
    @Override
    public void loadPacks(Consumer<Pack> infoConsumer, Pack.PackConstructor infoFactory) {
        Path rootPath = DataGenFactory.ROOT_PATH;
        var pack = infoFactory.create("mm", new TextComponent("MM"), true, () -> new GeneratedPack(rootPath), new PackMetadataSection(new TextComponent("MM"), 9), Pack.Position.BOTTOM, PackSource.DEFAULT, false);
        if (pack != null) {
            infoConsumer.accept(pack);
        }
    }
}
