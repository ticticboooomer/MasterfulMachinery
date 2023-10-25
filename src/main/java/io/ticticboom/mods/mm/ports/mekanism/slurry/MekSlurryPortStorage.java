package io.ticticboom.mods.mm.ports.mekanism.slurry;

import io.ticticboom.mods.mm.ports.mekanism.MekChemicalConfiguredPort;
import io.ticticboom.mods.mm.ports.mekanism.MekChemicalPortStorage;
import mekanism.api.MekanismAPI;
import mekanism.api.chemical.ChemicalTankBuilder;
import mekanism.api.chemical.IChemicalTank;
import mekanism.api.chemical.slurry.Slurry;
import mekanism.api.chemical.slurry.SlurryStack;
import mekanism.common.capabilities.Capabilities;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;

public class MekSlurryPortStorage extends MekChemicalPortStorage<Slurry, SlurryStack> {

    public MekSlurryPortStorage(MekChemicalConfiguredPort config) {
        super(config);
    }

    @Override
    protected IChemicalTank<Slurry, SlurryStack> createTank(long capacity) {
        return ChemicalTankBuilder.SLURRY.create(capacity, null);
    }

    @Override
    protected boolean isCapability(Capability<?> cap) {
        return cap == Capabilities.SLURRY_HANDLER;
    }

    @Override
    protected SlurryStack createStack(ResourceLocation loc, long amount) {
        Slurry type = MekanismAPI.slurryRegistry().getValue(loc);
        return new SlurryStack(type, amount);
    }

    @Override
    protected MekChemicalPortStorage<Slurry, SlurryStack> createSelf() {
        return new MekSlurryPortStorage(config);
    }
}
