package io.ticticboom.mods.mm.port.mekanism.slurry.register;

import io.ticticboom.mods.mm.port.mekanism.chemical.register.MekanismChemicalPortScreen;
import mekanism.api.chemical.slurry.Slurry;
import mekanism.api.chemical.slurry.SlurryStack;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class MekanismSlurryPortScreen extends MekanismChemicalPortScreen<Slurry, SlurryStack, MekanismSlurryPortMenu> {

    public MekanismSlurryPortScreen(MekanismSlurryPortMenu menu, Inventory inv, Component title) {
        super(menu, inv, title);
    }
}
