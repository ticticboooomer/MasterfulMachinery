package io.ticticboom.mods.mm.config;

import net.minecraftforge.fml.config.ModConfig;

public class MMConfig {
    public static boolean DEBUG_TOOL = true;

    public static void bake(ModConfig config) {
        DEBUG_TOOL = MMConfigSetup.COMMON.debugTool.get();
    }
}
