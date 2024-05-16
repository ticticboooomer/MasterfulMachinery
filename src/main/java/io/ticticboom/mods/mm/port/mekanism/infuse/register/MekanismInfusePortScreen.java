package io.ticticboom.mods.mm.port.mekanism.infuse.register;

import io.ticticboom.mods.mm.port.mekanism.chemical.register.MekanismChemicalPortMenu;
import io.ticticboom.mods.mm.port.mekanism.chemical.register.MekanismChemicalPortScreen;
import mekanism.api.chemical.infuse.InfuseType;
import mekanism.api.chemical.infuse.InfusionStack;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class MekanismInfusePortScreen extends MekanismChemicalPortScreen<InfuseType, InfusionStack, MekanismInfusePortMenu> {

    public MekanismInfusePortScreen(MekanismInfusePortMenu menu, Inventory inv, Component title) {
        super(menu, inv, title);
    }
}
