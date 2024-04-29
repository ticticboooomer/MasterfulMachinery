package io.ticticboom.mods.mm.cap;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.items.IItemHandler;

public class MMCapabilities {
    public static final Capability<IItemHandler> ITEM = CapabilityManager.get(new CapabilityToken<>() {});
    public static final Capability<IFluidHandler> FLUID = CapabilityManager.get(new CapabilityToken<>() {});
    public static final Capability<IEnergyStorage> ENERGY = CapabilityManager.get(new CapabilityToken<>() {});
}
