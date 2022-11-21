package io.ticticboom.mods.mm.ports.mekanism.infuse;

import io.ticticboom.mods.mm.Ref;
import io.ticticboom.mods.mm.ports.base.IConfiguredPort;
import io.ticticboom.mods.mm.ports.base.PortStorage;
import io.ticticboom.mods.mm.ports.energy.EnergyConfiguredPort;
import io.ticticboom.mods.mm.ports.mekanism.MekChemicalConfiguredPort;
import io.ticticboom.mods.mm.ports.mekanism.MekChemicalPortTypeEntry;
import mekanism.api.chemical.infuse.InfuseType;
import mekanism.api.chemical.infuse.InfusionStack;
import net.minecraft.resources.ResourceLocation;

public class MekInfusePortTypeEntry extends MekChemicalPortTypeEntry<InfuseType, InfusionStack> {
    public MekInfusePortTypeEntry() {
        super("infuseType");
    }

    @Override
    public Class<? extends PortStorage> storageClass() {
        return MekInfusePortStorage.class;
    }

    @Override
    public ResourceLocation overlay(boolean input) {
        return input ? Ref.res("block/compat_ports/mekanism_infusion_input_cutout") : Ref.res("block/compat_ports/mekanism_infusion_output_cutout");
    }

    @Override
    public PortStorage createStorage(IConfiguredPort config) {
        return new MekInfusePortStorage((MekChemicalConfiguredPort) config);
    }
}
