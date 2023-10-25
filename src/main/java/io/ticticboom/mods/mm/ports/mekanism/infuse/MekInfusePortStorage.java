package io.ticticboom.mods.mm.ports.mekanism.infuse;

import io.ticticboom.mods.mm.ports.mekanism.MekChemicalConfiguredPort;
import io.ticticboom.mods.mm.ports.mekanism.MekChemicalPortStorage;
import mekanism.api.MekanismAPI;
import mekanism.api.chemical.ChemicalTankBuilder;
import mekanism.api.chemical.IChemicalTank;
import mekanism.api.chemical.infuse.InfuseType;
import mekanism.api.chemical.infuse.InfusionStack;
import mekanism.common.capabilities.Capabilities;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;

public class MekInfusePortStorage extends MekChemicalPortStorage<InfuseType, InfusionStack> {

    public MekInfusePortStorage(MekChemicalConfiguredPort config) {
        super(config);
    }

    @Override
    protected IChemicalTank<InfuseType, InfusionStack> createTank(long capacity) {
        return ChemicalTankBuilder.INFUSION.create(capacity, null);
    }

    @Override
    protected boolean isCapability(Capability<?> cap) {
        return cap == Capabilities.INFUSION_HANDLER;
    }

    @Override
    protected InfusionStack createStack(ResourceLocation loc, long amount) {
        InfuseType type = MekanismAPI.infuseTypeRegistry().getValue(loc);
        return new InfusionStack(type, amount);
    }

    @Override
    protected MekChemicalPortStorage<InfuseType, InfusionStack> createSelf() {
        return new MekInfusePortStorage(config);
    }
}
