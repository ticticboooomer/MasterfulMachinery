package io.ticticboom.mods.mm.datagen;

import net.minecraft.DetectedVersion;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.fml.loading.FMLPaths;

import java.nio.file.Path;
import java.util.ArrayList;

public class DataGenFactory {

    public static Path ROOT_PATH;

    public static void init() {
        ROOT_PATH = FMLPaths.CONFIGDIR.get().resolve("mm/dont_edit");
    }

    public static DataGenerator create() {
        return new DataGenerator(ROOT_PATH, new ArrayList<>(), DetectedVersion.tryDetectVersion(),true);
    }
}
