package io.ticticboom.mods.mm.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class MMCommonConfig {
    public final ForgeConfigSpec.BooleanValue debugTool;
    public final ForgeConfigSpec.BooleanValue splitRecipesJei;
    public final ForgeConfigSpec.BooleanValue portsAutoExtractByDefault;
    public final ForgeConfigSpec.BooleanValue asyncStructureValidation;
    public final ForgeConfigSpec.IntValue structureValidationRate;

    public MMCommonConfig(ForgeConfigSpec.Builder builder) {
        asyncStructureValidation = builder.comment("Enables async structure validation to improve TPS. Disable in case of issues.")
                .define("asyncValidation", true);
        structureValidationRate = builder.comment("How often controller will check structure. 1 means every tick, 20 means every second.")
                .defineInRange("structureValidationRate", 10, 1, 100);
        debugTool = builder.comment("Enables the Debug Tool Item's functionality (Disable when on server)")
                .define("debugTool", true);
        splitRecipesJei = builder.comment("Splits JEI recipe viewer categroies by the structure they belong to.")
                .define("splitRecipesJei", true);
        portsAutoExtractByDefault = builder.comment("The default value of 'autoPush' (when not set) on ports that support automatic extract to nearby storages")
                .define("portsAutoExtractByDefault", false);

    }
}
