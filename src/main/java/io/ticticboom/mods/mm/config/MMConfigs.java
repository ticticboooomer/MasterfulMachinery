package io.ticticboom.mods.mm.config;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;

public class MMConfigs {
    private static final ForgeConfigSpec commonSpec;
    private static final MMCommonConfig common;

    static {
        final var specPar = new ForgeConfigSpec.Builder().configure(MMCommonConfig::new);
        commonSpec = specPar.getRight();
        common = specPar.getLeft();
    }

    public static void register() {
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, commonSpec, "masterfulmachinery-mm.toml");
    }
}