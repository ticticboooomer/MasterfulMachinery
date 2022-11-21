package io.ticticboom.mods.mm.ports.mekanism.gas;

import io.ticticboom.mods.mm.ports.mekanism.MekChemicalConfiguredPort;
import io.ticticboom.mods.mm.ports.mekanism.MekChemicalPortStorage;
import mekanism.api.MekanismAPI;
import mekanism.api.chemical.ChemicalTankBuilder;
import mekanism.api.chemical.IChemicalTank;
import mekanism.api.chemical.gas.Gas;
import mekanism.api.chemical.gas.GasStack;
import mekanism.common.capabilities.Capabilities;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;

public class MekGasPortStorage extends MekChemicalPortStorage<Gas, GasStack> {


    public MekGasPortStorage(MekChemicalConfiguredPort config) {
        super(config);
    }

    @Override
    protected IChemicalTank<Gas, GasStack> createTank(long capacity) {
        return ChemicalTankBuilder.GAS.create(capacity, null);
    }

    @Override
    protected boolean isCapability(Capability<?> cap) {
        return cap == Capabilities.GAS_HANDLER_CAPABILITY;
    }

    @Override
    protected GasStack createStack(ResourceLocation loc, long amount) {
        var gas = MekanismAPI.gasRegistry().getValue(loc);
        return new GasStack(gas, amount);
    }

    @Override
    protected MekChemicalPortStorage<Gas, GasStack> createSelf() {
        return new MekGasPortStorage(config);
    }
}
