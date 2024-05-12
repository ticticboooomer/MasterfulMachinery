package io.ticticboom.mods.mm.datagen;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import net.minecraft.server.packs.metadata.MetadataSectionSerializer;
import net.minecraft.util.GsonHelper;
import net.minecraftforge.resource.PathPackResources;

import java.nio.file.Path;

public class GeneratedPack extends PathPackResources {

    public GeneratedPack(String packId, Path source) {
        super(packId, source);
    }

    @Override
    public <T> T getMetadataSection(MetadataSectionSerializer<T> deserializer) {
        JsonObject jsonobject = new JsonObject();
        JsonObject packObject = new JsonObject();
        packObject.addProperty("pack_format", 15);
        packObject.addProperty("description", "mm");
        jsonobject.add("pack", packObject);
        if (!jsonobject.has(deserializer.getMetadataSectionName())) {
            return null;
        } else {
            try {
                return deserializer.fromJson(GsonHelper.getAsJsonObject(jsonobject, deserializer.getMetadataSectionName()));
            } catch (JsonParseException jsonparseexception) {
                return null;
            }
        }
    }
}