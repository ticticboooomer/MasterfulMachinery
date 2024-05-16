package io.ticticboom.mods.mm.port.mekanism.slurry.register;

import io.ticticboom.mods.mm.model.PortModel;
import io.ticticboom.mods.mm.port.mekanism.chemical.register.MekanismChemicalPortBlockEntity;
import io.ticticboom.mods.mm.port.mekanism.chemical.register.MekanismChemicalPortMenu;
import io.ticticboom.mods.mm.setup.RegistryGroupHolder;
import mekanism.api.chemical.slurry.Slurry;
import mekanism.api.chemical.slurry.SlurryStack;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;

public class MekanismSlurryPortMenu extends MekanismChemicalPortMenu<Slurry, SlurryStack> {

    public MekanismSlurryPortMenu(PortModel model, RegistryGroupHolder groupHolder, int windowId, Inventory inv,  MekanismChemicalPortBlockEntity<Slurry, SlurryStack> be) {
        super(model, groupHolder, windowId, be, inv);
    }

    public MekanismSlurryPortMenu(PortModel model, RegistryGroupHolder groupHolder, int windowId, Inventory inv, FriendlyByteBuf buf) {
        this(model, groupHolder, windowId, inv, (MekanismSlurryPortBlockEntity) inv.player.level().getBlockEntity(buf.readBlockPos()));
    }
}
