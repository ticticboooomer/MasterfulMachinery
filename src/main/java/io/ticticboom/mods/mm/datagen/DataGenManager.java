package io.ticticboom.mods.mm.datagen;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import io.ticticboom.mods.mm.Ref;
import io.ticticboom.mods.mm.datagen.provider.MMBlockstateProvider;
import io.ticticboom.mods.mm.datagen.provider.MMLootTableProvider;
import net.minecraft.DetectedVersion;
import net.minecraft.WorldVersion;
import net.minecraft.client.Minecraft;
import net.minecraft.data.DataGenerator;
import net.minecraft.server.packs.repository.PackRepository;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoader;
import net.minecraftforge.fml.loading.FMLPaths;
import net.minecraftforge.resource.ResourcePackLoader;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

public class DataGenManager {

    private static DataGenerator generator;
    private static boolean hasGenerated = false;

    public static void generate() {
        if (!hasGenerated) {
            try {
                if (!ModLoader.isLoadingStateValid()) {
                    return;
                }
                generator.run();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void registerDataProviders() {
        generator = createDataGenerator();
        ExistingFileHelper efh = new ExistingFileHelper(ImmutableList.of(), ImmutableSet.of(), false, null, null);
        generator.addProvider(true, new MMLootTableProvider(generator));
        generator.addProvider(true, new MMBlockstateProvider(generator, efh));
    }

    public static DataGenerator createDataGenerator() {
        generator = new DataGenerator(MMRepositorySource.CONFIG_DIR, DetectedVersion.tryDetectVersion(), true);
        return generator;
    }
}
