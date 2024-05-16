package io.ticticboom.mods.mm.port.mekanism.pigment.register;

import io.ticticboom.mods.mm.port.mekanism.chemical.register.MekanismChemicalPortMenu;
import io.ticticboom.mods.mm.port.mekanism.chemical.register.MekanismChemicalPortScreen;
import mekanism.api.chemical.pigment.Pigment;
import mekanism.api.chemical.pigment.PigmentStack;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class MekanismPigmentPortScreen extends MekanismChemicalPortScreen<Pigment, PigmentStack, MekanismPigmentPortMenu> {

    public MekanismPigmentPortScreen(MekanismPigmentPortMenu menu, Inventory inv, Component title) {
        super(menu, inv, title);
    }
}
