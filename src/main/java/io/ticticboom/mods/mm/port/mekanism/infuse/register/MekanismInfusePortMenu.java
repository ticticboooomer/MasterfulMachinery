package io.ticticboom.mods.mm.port.mekanism.infuse.register;

import io.ticticboom.mods.mm.model.PortModel;
import io.ticticboom.mods.mm.port.mekanism.chemical.register.MekanismChemicalPortBlockEntity;
import io.ticticboom.mods.mm.port.mekanism.chemical.register.MekanismChemicalPortMenu;
import io.ticticboom.mods.mm.port.mekanism.chemical.register.MekanismChemicalPortScreen;
import io.ticticboom.mods.mm.setup.RegistryGroupHolder;
import mekanism.api.chemical.infuse.InfuseType;
import mekanism.api.chemical.infuse.InfusionStack;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;

public class MekanismInfusePortMenu extends MekanismChemicalPortMenu<InfuseType, InfusionStack> {

    public MekanismInfusePortMenu(PortModel model, RegistryGroupHolder groupHolder, int windowId, MekanismChemicalPortBlockEntity<InfuseType, InfusionStack> be) {
        super(model, groupHolder, windowId, be);
    }

    public MekanismInfusePortMenu(PortModel model, RegistryGroupHolder groupHolder, int i, Inventory o, FriendlyByteBuf u) {
        this(model, groupHolder, i, (MekanismInfusePortBlockEntity) o.player.level().getBlockEntity(u.readBlockPos()));
    }
}
