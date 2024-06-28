package io.ticticboom.mods.mm.port.mekanism.gas.register;

import io.ticticboom.mods.mm.port.mekanism.chemical.register.MekanismChemicalPortScreen;
import mekanism.api.chemical.gas.Gas;
import mekanism.api.chemical.gas.GasStack;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class MekanismGasPortScreen extends MekanismChemicalPortScreen<Gas, GasStack, MekanismGasPortMenu> {

    public MekanismGasPortScreen(MekanismGasPortMenu menu, Inventory inv, Component title) {
        super(menu, inv, title);
    }
}
