package io.ticticboom.mods.mm.ports.mekanism.pigment;

import io.ticticboom.mods.mm.Ref;
import io.ticticboom.mods.mm.ports.base.IConfiguredPort;
import io.ticticboom.mods.mm.ports.base.PortStorage;
import io.ticticboom.mods.mm.ports.mekanism.MekChemicalConfiguredPort;
import io.ticticboom.mods.mm.ports.mekanism.MekChemicalPortTypeEntry;
import mekanism.api.chemical.pigment.Pigment;
import mekanism.api.chemical.pigment.PigmentStack;
import net.minecraft.resources.ResourceLocation;

public class MekPigmentPortTypeEntry extends MekChemicalPortTypeEntry<Pigment, PigmentStack> {
    public MekPigmentPortTypeEntry() {
        super("pigment");
    }

    @Override
    public Class<? extends PortStorage> storageClass() {
        return MekPigmentPortStorage.class;
    }

    @Override
    public ResourceLocation overlay(boolean input) {
        return input ? Ref.res("block/compat_ports/mekanism_pigment_input_cutout") : Ref.res("block/compat_ports/mekanism_pigment_output_cutout");
    }

    @Override
    public PortStorage createStorage(IConfiguredPort config) {
        return new MekPigmentPortStorage((MekChemicalConfiguredPort) config);
    }
}
