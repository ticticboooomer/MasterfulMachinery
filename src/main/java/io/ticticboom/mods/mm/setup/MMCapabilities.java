package io.ticticboom.mods.mm.setup;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.items.IItemHandler;

public class MMCapabilities {
    public static Capability<IItemHandler> ITEMS = CapabilityManager.get(new CapabilityToken<>(){});
    public static Capability<IFluidHandler> FLUIDS = CapabilityManager.get(new CapabilityToken<>(){});
    public static Capability<IEnergyStorage> ENERGY = CapabilityManager.get(new CapabilityToken<>(){});
}
