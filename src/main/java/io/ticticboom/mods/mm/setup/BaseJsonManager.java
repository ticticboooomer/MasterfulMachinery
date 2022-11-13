package io.ticticboom.mods.mm.setup;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.ticticboom.mods.mm.Ref;
import net.minecraftforge.fml.loading.FMLPaths;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class BaseJsonManager {
    protected static Path getConfigDirectory(String subdir) {
        var path = FMLPaths.CONFIGDIR.get().resolve("mm");
        createIfNotExist(path);
        var result = path.resolve(subdir);
        createIfNotExist(result);
        return result;
    }

    protected static void createIfNotExist(Path path) {
        if (!Files.exists(path)) {
            try {
                Files.createDirectories(path);
            } catch (IOException e) {
                e.printStackTrace();
                Ref.LOG.error("Failed to create path: {}", path.toAbsolutePath());
            }
        }
    }

    protected static List<JsonObject> loadFiles(Path path) {
        var file = path.toFile();
        if (!file.exists()) {
            Ref.LOG.fatal("file non existent upon reading, where TF has it gone between listing and reading hm Hm HMMMMM?!?!?!?!?!");
            return null;
        }
        ArrayList<JsonObject> result = new ArrayList<>();
        File[] list = file.listFiles();

        if (list == null) {
            return result;
        }

        for (File s : list) {
            if (!s.exists()) {
                Ref.LOG.fatal("file non existent upon reading, where TF has it gone between listing and reading hm Hm HMMMMM?!?!?!?!?!");
                continue;
            }
            try {
                FileReader fr = new FileReader(s);
                var json = JsonParser.parseReader(fr);
                result.add(json.getAsJsonObject());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Ref.LOG.fatal("Failed to read or parse file: {}", file.getAbsolutePath());
            }
        }
        return result;
    }
}
