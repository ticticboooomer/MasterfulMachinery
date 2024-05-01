package io.ticticboom.mods.mm.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class MMCommonConfig {
    public final ForgeConfigSpec.BooleanValue enableStructureScanner;

    public MMCommonConfig(ForgeConfigSpec.Builder builder) {
        enableStructureScanner = builder.comment("Whether to enable or disable the Structure Scanner Block & functionality. This block should only be used when creating Structures in MM.")
                .define("enableStructureScanner", true);
    }
}
