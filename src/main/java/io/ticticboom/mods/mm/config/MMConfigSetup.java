package io.ticticboom.mods.mm.config;

import io.ticticboom.mods.mm.Ref;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import org.apache.commons.lang3.tuple.Pair;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, modid = Ref.ID)
public class MMConfigSetup {
    public static final MMCommonConfig COMMON;
    private static final ForgeConfigSpec commonSpec;

    static {
        final Pair<MMCommonConfig, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(MMCommonConfig::new);
        COMMON = specPair.getKey();
        commonSpec = specPair.getRight();
    }

    public static void setup() {
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, commonSpec);
    }

    @SubscribeEvent
    public static void on(final ModConfigEvent event) {
        if (event.getConfig().getSpec() == commonSpec) {
            MMConfig.bake(event.getConfig());
        }
    }
}
