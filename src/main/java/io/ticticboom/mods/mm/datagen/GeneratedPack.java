package io.ticticboom.mods.mm.datagen;

import com.google.common.base.Joiner;
import com.google.common.collect.Sets;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.mojang.logging.LogUtils;
import net.minecraft.FileUtil;
import net.minecraft.Util;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackResources;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.metadata.MetadataSectionSerializer;
import net.minecraft.server.packs.resources.IoSupplier;
import net.minecraft.util.GsonHelper;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Stream;

public class GeneratedPack implements PackResources {
    private static final Logger LOGGER = LogUtils.getLogger();
    private static final Joiner PATH_JOINER = Joiner.on("/");
    private final String packId;
    private final boolean isBuiltin;
    private final Path root;

    /**
     * Constructs a java.nio.Path-based resource pack.
     *
     * @param packId    the identifier of the pack.
     *                  This identifier should be unique within the pack finder, preferably the name of the file or folder containing the resources.
     * @param isBuiltin whether this pack resources should be considered builtin
     * @param source    the root path of the pack. This needs to point to the folder that contains "assets" and/or "data", not the asset folder itself!
     */
    public GeneratedPack(String packId, boolean isBuiltin, Path source) {
        this.packId = packId;
        this.isBuiltin = isBuiltin;
        this.root = source;
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

    @Override
    public String packId() {
        return packId;
    }

    @Nullable
    public IoSupplier<InputStream> getRootResource(String... p_249041_) {
        FileUtil.validatePath(p_249041_);
        Path path = FileUtil.resolvePath(this.root, List.of(p_249041_));
        return Files.exists(path) ? IoSupplier.create(path) : null;
    }

    public static boolean validatePath(Path p_249579_) {
        return true;
    }

    @Nullable
    public IoSupplier<InputStream> getResource(PackType p_249352_, ResourceLocation p_251715_) {
        Path path = this.root.resolve(p_249352_.getDirectory()).resolve(p_251715_.getNamespace());
        return getResource(p_251715_, path);
    }

    public static IoSupplier<InputStream> getResource(ResourceLocation p_250145_, Path p_251046_) {
        return FileUtil.decomposePath(p_250145_.getPath()).get().map((p_251647_) -> {
            Path path = FileUtil.resolvePath(p_251046_, p_251647_);
            return returnFileIfExists(path);
        }, (p_248714_) -> {
            LOGGER.error("Invalid path {}: {}", p_250145_, p_248714_.message());
            return null;
        });
    }

    @Nullable
    private static IoSupplier<InputStream> returnFileIfExists(Path p_250506_) {
        return Files.exists(p_250506_) && validatePath(p_250506_) ? IoSupplier.create(p_250506_) : null;
    }

    public void listResources(PackType p_251452_, String p_249854_, String p_248650_, PackResources.ResourceOutput p_248572_) {
        FileUtil.decomposePath(p_248650_).get().ifLeft((p_250225_) -> {
            Path path = this.root.resolve(p_251452_.getDirectory()).resolve(p_249854_);
            listPath(p_249854_, path, p_250225_, p_248572_);
        }).ifRight((p_252338_) -> {
            LOGGER.error("Invalid path {}: {}", p_248650_, p_252338_.message());
        });
    }

    public static void listPath(String p_249455_, Path p_249514_, List<String> p_251918_, PackResources.ResourceOutput p_249964_) {
        Path path = FileUtil.resolvePath(p_249514_, p_251918_);

        try (Stream<Path> stream = Files.find(path, Integer.MAX_VALUE, (p_250060_, p_250796_) -> {
            return p_250796_.isRegularFile();
        })) {
            stream.forEach((p_249092_) -> {
                String s = PATH_JOINER.join(p_249514_.relativize(p_249092_));
                ResourceLocation resourcelocation = ResourceLocation.tryBuild(p_249455_, s);
                if (resourcelocation == null) {
                    Util.logAndPauseIfInIde(String.format(Locale.ROOT, "Invalid path in pack: %s:%s, ignoring", p_249455_, s));
                } else {
                    p_249964_.accept(resourcelocation, IoSupplier.create(p_249092_));
                }

            });
        } catch (NoSuchFileException nosuchfileexception) {
        } catch (IOException ioexception) {
            LOGGER.error("Failed to list path {}", path, ioexception);
        }

    }

    public Set<String> getNamespaces(PackType p_251896_) {
        Set<String> set = Sets.newHashSet();
        Path path = this.root.resolve(p_251896_.getDirectory());

        try (DirectoryStream<Path> directorystream = Files.newDirectoryStream(path)) {
            for(Path path1 : directorystream) {
                String s = path1.getFileName().toString();
                if (s.equals(s.toLowerCase(Locale.ROOT))) {
                    set.add(s);
                } else {
                    LOGGER.warn("Ignored non-lowercase namespace: {} in {}", s, this.root);
                }
            }
        } catch (NoSuchFileException nosuchfileexception) {
        } catch (IOException ioexception) {
            LOGGER.error("Failed to list path {}", path, ioexception);
        }

        return set;
    }

    public void close() {
    }
}