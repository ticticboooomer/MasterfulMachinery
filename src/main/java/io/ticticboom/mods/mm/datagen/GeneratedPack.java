package io.ticticboom.mods.mm.datagen;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.mojang.datafixers.util.Pair;
import io.ticticboom.mods.mm.ModRoot;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackResources;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.metadata.MetadataSectionSerializer;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.util.GsonHelper;

import javax.annotation.Nullable;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GeneratedPack implements PackResources {
    private final Path path;
    private final String id;

    public GeneratedPack(Path path, String id) {
        this.id = id;
        ModRoot.generate();
        this.path = path;
    }

    private static String getFullPath(PackType type, ResourceLocation location) {
        return String.format("%s/%s", location.getNamespace(), location.getPath());
    }

    @Override
    public Resource.IoSupplier<InputStream> getRootResource(String... fileName) {
        Path resolved = path.resolve(String.join("/", fileName));
        return IoSupplier.create(resolved);
    }

    @Override
    public Resource.IoSupplier<InputStream> getResource(PackType type, ResourceLocation location) {
        Path resolved = path.resolve(getFullPath(type, location));
        return IoSupplier.create(resolved);
    }

    @Override
    public void listResources(PackType p_10289_, String p_251379_, String p_251932_, ResourceOutput p_249347_) {
        var result = new ArrayList<Pair<ResourceLocation, String>>();
        getChildResourceLocations(result, 100, x -> true, path.resolve(p_251379_).resolve(p_251932_), p_251379_, p_251932_);
        for (Pair<ResourceLocation, String> row : result) {
            p_249347_.accept(row.getFirst(), IoSupplier.create(Path.of(row.getSecond())));
        }
    }

    private void getChildResourceLocations(List<Pair<ResourceLocation, String>> result, int depth, Predicate<ResourceLocation> filter, Path current, String currentRLNS, String currentRLPath) {
        try {
            if (!Files.exists(current) || !Files.isDirectory(current)){
                return;
            }
            Stream<Path> list = Files.list(current);
            for (Path child : list.toList()) {
                if (!Files.isDirectory(child)) {
                    result.add(new Pair<>(new ResourceLocation(currentRLNS, currentRLPath + "/" + child.getFileName()), child.toString()));
                    continue;
                }
                getChildResourceLocations(result, depth + 1, filter, child, currentRLNS,  currentRLPath + "/" + child.getFileName());
            }
        } catch (IOException ignored) {
            ignored.printStackTrace();
        }
    }


    @Override
    public Set<String> getNamespaces(PackType type) {
        Set<String> result = new HashSet<>();
        try {
            Stream<Path> list = Files.list(path);
            for (Path resultingPath : list.toList()) {
                result.add(resultingPath.getFileName().toString());
            }

        } catch (IOException e) {
        }
        return result;
    }

    @Nullable
    @Override
    public <T> T getMetadataSection(MetadataSectionSerializer<T> deserializer) throws IOException {
        JsonObject jsonobject = new JsonObject();
        JsonObject packObject = new JsonObject();
        packObject.addProperty("pack_format", 12);
        packObject.addProperty("description", "mconf");
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

    @Override
    public String packId() {
        return id;
    }

    @Override
    public void close() {

    }
}