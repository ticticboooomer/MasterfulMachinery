package io.ticticboom.mods.mm.client;

import io.ticticboom.mods.mm.Ref;
import io.ticticboom.mods.mm.client.port.*;
import mekanism.api.chemical.gas.Gas;
import mekanism.api.chemical.gas.GasStack;
import mekanism.api.chemical.infuse.InfuseType;
import mekanism.api.chemical.infuse.InfusionStack;
import mekanism.api.chemical.pigment.Pigment;
import mekanism.api.chemical.pigment.PigmentStack;
import mekanism.api.chemical.slurry.Slurry;
import mekanism.api.chemical.slurry.SlurryStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.NewRegistryEvent;
import net.minecraftforge.registries.RegistryBuilder;

import java.util.function.Supplier;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class MMClientRegistries {
    public static Supplier<IForgeRegistry<ClientPortTypeEntry>> PORTS;


    @SubscribeEvent
    public static void createRegistries(NewRegistryEvent event) {
        PORTS = event.create(new RegistryBuilder<ClientPortTypeEntry>().setType(ClientPortTypeEntry.class).setName(Ref.CLIENT_PORTS_REGISTRY));
    }

    @SubscribeEvent
    public static void registerPorts(RegistryEvent.Register<ClientPortTypeEntry> event) {
        event.getRegistry().registerAll(
                new EnergyClientPortTypeEntry().setRegistryName(Ref.Ports.ENERGY),
                new FluidClientPortTypeEntry().setRegistryName(Ref.Ports.FLUID),
                new ItemClientPortTypeEntry().setRegistryName(Ref.Ports.ITEM)
                );

        if (ModList.get().isLoaded("mekanism")) {
            event.getRegistry().registerAll(new MekChemicalClientPortTypeEntry<Gas, GasStack>().setRegistryName(Ref.Ports.MEK_GAS),
                    new MekChemicalClientPortTypeEntry<InfuseType, InfusionStack>().setRegistryName(Ref.Ports.MEK_INFUSE),
                    new MekChemicalClientPortTypeEntry<Pigment, PigmentStack>().setRegistryName(Ref.Ports.MEK_PIGMENT),
                    new MekChemicalClientPortTypeEntry<Slurry, SlurryStack>().setRegistryName(Ref.Ports.MEK_SLURRY),
                    new MekHeatClientPortTypeEntry().setRegistryName(Ref.Ports.MEK_HEAT));
        }
    }
}
