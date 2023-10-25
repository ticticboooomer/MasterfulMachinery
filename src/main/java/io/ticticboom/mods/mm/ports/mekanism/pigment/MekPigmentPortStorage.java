package io.ticticboom.mods.mm.ports.mekanism.pigment;

import io.ticticboom.mods.mm.ports.mekanism.MekChemicalConfiguredPort;
import io.ticticboom.mods.mm.ports.mekanism.MekChemicalPortStorage;
import mekanism.api.MekanismAPI;
import mekanism.api.chemical.ChemicalTankBuilder;
import mekanism.api.chemical.IChemicalTank;
import mekanism.api.chemical.pigment.Pigment;
import mekanism.api.chemical.pigment.PigmentStack;
import mekanism.common.capabilities.Capabilities;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;

public class MekPigmentPortStorage extends MekChemicalPortStorage<Pigment, PigmentStack> {

    public MekPigmentPortStorage(MekChemicalConfiguredPort config) {
        super(config);
    }

    @Override
    protected IChemicalTank<Pigment, PigmentStack> createTank(long capacity) {
        return ChemicalTankBuilder.PIGMENT.create(capacity, null);
    }

    @Override
    protected boolean isCapability(Capability<?> cap) {
        return cap == Capabilities.PIGMENT_HANDLER;
    }

    @Override
    protected PigmentStack createStack(ResourceLocation loc, long amount) {
        Pigment type = MekanismAPI.pigmentRegistry().getValue(loc);
        return new PigmentStack(type, amount);
    }

    @Override
    protected MekChemicalPortStorage<Pigment, PigmentStack> createSelf() {
        return new MekPigmentPortStorage(config);
    }
}
