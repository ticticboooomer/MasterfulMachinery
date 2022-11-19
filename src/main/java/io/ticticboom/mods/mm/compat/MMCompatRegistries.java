package io.ticticboom.mods.mm.compat;

import io.ticticboom.mods.mm.Ref;
import io.ticticboom.mods.mm.compat.jei.base.JeiPortTypeEntry;
import io.ticticboom.mods.mm.compat.jei.base.JeiRecipeEntry;
import io.ticticboom.mods.mm.compat.jei.port.EnergyJeiPortTypeEntry;
import io.ticticboom.mods.mm.compat.jei.port.FluidJeiPortTypeEntry;
import io.ticticboom.mods.mm.compat.jei.port.ItemJeiPortTypeEntry;
import io.ticticboom.mods.mm.compat.jei.recipe.SimpleJeiRecipeEntry;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
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
                new SimpleJeiRecipeEntry().setRegistryName(Ref.RecipeEntries.SIMPLE)
        );
    }

    @SubscribeEvent
    public static void registerJeiPorts(RegistryEvent.Register<JeiPortTypeEntry> event) {
        event.getRegistry().registerAll(
                new EnergyJeiPortTypeEntry().setRegistryName(Ref.Ports.ENERGY),
                new FluidJeiPortTypeEntry().setRegistryName(Ref.Ports.FLUID),
                new ItemJeiPortTypeEntry().setRegistryName(Ref.Ports.ITEM)
        );
    }
}
