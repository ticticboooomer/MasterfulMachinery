package io.ticticboom.mods.mm.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class MMCommonConfig {
    public final ForgeConfigSpec.BooleanValue debugTool;

    public MMCommonConfig(ForgeConfigSpec.Builder builder) {
        debugTool = builder.comment("Enables the Debug Tool Item's functionality (Disable when on server)")
                .define("debugTool", true);
    }
}
