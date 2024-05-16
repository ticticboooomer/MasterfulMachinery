package io.ticticboom.mods.mm.port.mekanism.gas.register;

import io.ticticboom.mods.mm.model.PortModel;
import io.ticticboom.mods.mm.port.mekanism.chemical.register.MekanismChemicalPortMenu;
import io.ticticboom.mods.mm.setup.RegistryGroupHolder;
import mekanism.api.chemical.gas.Gas;
import mekanism.api.chemical.gas.GasStack;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;

public class MekanismGasPortMenu extends MekanismChemicalPortMenu<Gas, GasStack> {

    public MekanismGasPortMenu(PortModel model, RegistryGroupHolder groupHolder, int windowId, Inventory inv, MekanismGasPortBlockEntity be) {
        super(model, groupHolder, windowId, be);
    }

    public MekanismGasPortMenu(PortModel model, RegistryGroupHolder groupHolder, int windowId, Inventory inv, FriendlyByteBuf buf) {
        this(model, groupHolder, windowId, inv, ((MekanismGasPortBlockEntity) inv.player.level().getBlockEntity(buf.readBlockPos())));
    }
}