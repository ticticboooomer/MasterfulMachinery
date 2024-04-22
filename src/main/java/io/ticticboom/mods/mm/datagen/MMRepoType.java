package io.ticticboom.mods.mm.datagen;

import lombok.Getter;
import net.minecraft.network.chat.Component;
import net.minecraft.server.packs.PackType;

import java.nio.file.Path;

@Getter
public enum MMRepoType {
    DATA(PackType.SERVER_DATA, "MM Data Pack", "mm_data", "data"),
    RESOURCES(PackType.CLIENT_RESOURCES, "MM Resource Pack", "mm_resources", "assets");

    final PackType type;
    final String displayName;
    private final String nameId;
    final String path;

    MMRepoType(PackType type, String name, String nameId, String path) {
        this.type = type;
        this.displayName = name;
        this.nameId = nameId;
        this.path = path;
    }

    public Component nameComponent() {
        return Component.literal(displayName);
    }

    public Path getPath(Path configDir) {
        return configDir;
    }
}
