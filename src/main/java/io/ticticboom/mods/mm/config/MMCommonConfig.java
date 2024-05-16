package io.ticticboom.mods.mm.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class MMCommonConfig {
    public final ForgeConfigSpec.BooleanValue debugTool;
    public final ForgeConfigSpec.BooleanValue splitRecipesJei;

    public MMCommonConfig(ForgeConfigSpec.Builder builder) {
        debugTool = builder.comment("Enables the Debug Tool Item's functionality (Disable when on server)")
                .define("debugTool", true);
        splitRecipesJei = builder.comment("Splits JEI recipe viewer categroies by the structure they belong to.")
                .define("splitRecipesJei", true);
    }
}
