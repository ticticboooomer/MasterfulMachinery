package io.ticticboom.mods.mm.compat;

import io.ticticboom.mods.mm.Ref;
import io.ticticboom.mods.mm.compat.jei.base.JeiPortTypeEntry;
import io.ticticboom.mods.mm.compat.jei.base.JeiRecipeEntry;
import io.ticticboom.mods.mm.compat.jei.port.*;
import io.ticticboom.mods.mm.compat.jei.port.mek.*;
import io.ticticboom.mods.mm.compat.jei.recipe.PerTickJeiRecipeEntry;
import io.ticticboom.mods.mm.compat.jei.recipe.SimpleJeiRecipeEntry;
import io.ticticboom.mods.mm.recipedisplay.MMRecipeDisplayElement;
import io.ticticboom.mods.mm.recipedisplay.image.ImageRecipeDisplayElement;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.ModLoader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.NewRegistryEvent;
import net.minecraftforge.registries.RegisterEvent;
import net.minecraftforge.registries.RegistryBuilder;

import java.util.function.Supplier;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class MMCompatRegistries {
    public static Supplier<IForgeRegistry<JeiRecipeEntry>> JEI_RECIPE_ENTRIES;
    public static Supplier<IForgeRegistry<JeiPortTypeEntry>> JEI_PORTS;
    public static Supplier<IForgeRegistry<MMRecipeDisplayElement>> RECIPE_DISPLAY_ELEMENTS;

    public static void on(NewRegistryEvent event) {
        if (ModList.get().isLoaded("jei")) {
            JEI_RECIPE_ENTRIES = event.create(new RegistryBuilder<JeiRecipeEntry>().setName(Ref.CompatRegistries.JEI_RECIPE_ENTRIES));
            JEI_PORTS = event.create(new RegistryBuilder<JeiPortTypeEntry>().setName(Ref.CompatRegistries.JEI_PORT_TYPES));
            RECIPE_DISPLAY_ELEMENTS = event.create(new RegistryBuilder<MMRecipeDisplayElement>().setName(Ref.CompatRegistries.RECIPE_DISPLAY_ELEMENTS));
        }
    }

    public static void registerJeiRecipeEntries(RegisterEvent event) {

        event.register(JEI_RECIPE_ENTRIES.get().getRegistryKey(), Ref.RecipeEntries.SIMPLE, SimpleJeiRecipeEntry::new);
        event.register(JEI_RECIPE_ENTRIES.get().getRegistryKey(), Ref.RecipeEntries.PER_TICK, PerTickJeiRecipeEntry::new);
    }

    public static void registerJeiPorts(RegisterEvent event) {
        event.register(JEI_PORTS.get().getRegistryKey(), Ref.Ports.ENERGY, EnergyJeiPortTypeEntry::new);
        event.register(JEI_PORTS.get().getRegistryKey(), Ref.Ports.FLUID, FluidJeiPortTypeEntry::new);
        event.register(JEI_PORTS.get().getRegistryKey(), Ref.Ports.ITEM, ItemJeiPortTypeEntry::new);
        if (ModList.get().isLoaded("create")) {
            event.register(JEI_PORTS.get().getRegistryKey(), Ref.Ports.CREATE_ROT, RotationJeiPortTypeEntry::new);
        }
        if (ModList.get().isLoaded("mekanism")) {
            event.register(JEI_PORTS.get().getRegistryKey(), Ref.Ports.MEK_GAS, MekGasJeiPortTypeEntry::new);
            event.register(JEI_PORTS.get().getRegistryKey(), Ref.Ports.MEK_INFUSE, MekInfuseJeiPortTypeEntry::new);
            event.register(JEI_PORTS.get().getRegistryKey(), Ref.Ports.MEK_PIGMENT, MekPigmentJeiPortTypeEntry::new);
            event.register(JEI_PORTS.get().getRegistryKey(), Ref.Ports.MEK_SLURRY, MekSlurryJeiPortTypeEntry::new);
        }
    }

    public static void registerJeiRecipeDisplayElements(RegisterEvent event) {
        event.register(RECIPE_DISPLAY_ELEMENTS.get().getRegistryKey(), Ref.RecipeDisplayElements.IMAGE, ImageRecipeDisplayElement::new);
    }

    public static void init() {
        if (ModList.get().isLoaded("jei")) {
            FMLJavaModLoadingContext.get().getModEventBus().addListener(MMCompatRegistries::on);
            FMLJavaModLoadingContext.get().getModEventBus().addListener(MMCompatRegistries::registerJeiPorts);
            FMLJavaModLoadingContext.get().getModEventBus().addListener(MMCompatRegistries::registerJeiRecipeEntries);
            FMLJavaModLoadingContext.get().getModEventBus().addListener(MMCompatRegistries::registerJeiRecipeDisplayElements);
        }
    }
}
