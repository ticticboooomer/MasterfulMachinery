package io.ticticboom.mods.mm.compat;

import io.ticticboom.mods.mm.Ref;
import io.ticticboom.mods.mm.compat.jei.base.JeiPortTypeEntry;
import io.ticticboom.mods.mm.compat.jei.base.JeiRecipeEntry;
import io.ticticboom.mods.mm.compat.jei.port.*;
import io.ticticboom.mods.mm.compat.jei.port.mek.*;
import io.ticticboom.mods.mm.compat.jei.recipe.PerTickJeiRecipeEntry;
import io.ticticboom.mods.mm.compat.jei.recipe.SimpleJeiRecipeEntry;
import io.ticticboom.mods.mm.ports.createrotation.RotationPortTypeEntry;
import io.ticticboom.mods.mm.ports.mekanism.gas.MekGasPortTypeEntry;
import io.ticticboom.mods.mm.ports.mekanism.infuse.MekInfusePortTypeEntry;
import io.ticticboom.mods.mm.ports.mekanism.pigment.MekPigmentPortTypeEntry;
import io.ticticboom.mods.mm.ports.mekanism.slurry.MekSlurryPortTypeEntry;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.NewRegistryEvent;
import net.minecraftforge.registries.RegistryBuilder;

import java.util.function.Supplier;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class MMCompatRegistries {
    public static Supplier<IForgeRegistry<JeiRecipeEntry>> JEI_RECIPE_ENTRIES;
    public static Supplier<IForgeRegistry<JeiPortTypeEntry>> JEI_PORTS;

    @SubscribeEvent
    public static void on(NewRegistryEvent event) {
        JEI_RECIPE_ENTRIES = event.create(new RegistryBuilder<JeiRecipeEntry>().setType(JeiRecipeEntry.class).setName(Ref.CompatRegistries.JEI_RECIPE_ENTRIES));
        JEI_PORTS = event.create(new RegistryBuilder<JeiPortTypeEntry>().setType(JeiPortTypeEntry.class).setName(Ref.CompatRegistries.JEI_PORT_TYPES));
    }

    @SubscribeEvent
    public static void registerJeiRecipeEntries(RegistryEvent.Register<JeiRecipeEntry> event) {
        event.getRegistry().registerAll(
                new SimpleJeiRecipeEntry().setRegistryName(Ref.RecipeEntries.SIMPLE),
                new PerTickJeiRecipeEntry().setRegistryName(Ref.RecipeEntries.PER_TICK)
        );
    }

    @SubscribeEvent
    public static void registerJeiPorts(RegistryEvent.Register<JeiPortTypeEntry> event) {
        event.getRegistry().registerAll(
                new EnergyJeiPortTypeEntry().setRegistryName(Ref.Ports.ENERGY),
                new FluidJeiPortTypeEntry().setRegistryName(Ref.Ports.FLUID),
                new ItemJeiPortTypeEntry().setRegistryName(Ref.Ports.ITEM)
                );
        if (ModList.get().isLoaded("create")) {
            event.getRegistry().register(new RotationJeiPortTypeEntry().setRegistryName(Ref.Ports.CREATE_ROT));
        }
        if (ModList.get().isLoaded("mekanism")) {
            event.getRegistry().registerAll(new MekGasJeiPortTypeEntry().setRegistryName(Ref.Ports.MEK_GAS),
                    new MekInfuseJeiPortTypeEntry().setRegistryName(Ref.Ports.MEK_INFUSE),
                    new MekPigmentJeiPortTypeEntry().setRegistryName(Ref.Ports.MEK_PIGMENT),
                    new MekSlurryJeiPortTypeEntry().setRegistryName(Ref.Ports.MEK_SLURRY));
        }

    }
}
