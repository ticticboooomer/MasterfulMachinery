package io.ticticboom.mods.mm.port.mekanism.chemical.register;

import io.ticticboom.mods.mm.menu.MMContainerMenu;
import io.ticticboom.mods.mm.model.PortModel;
import io.ticticboom.mods.mm.port.IPortMenu;
import io.ticticboom.mods.mm.port.mekanism.pigment.register.MekanismPigmentPortBlockEntity;
import io.ticticboom.mods.mm.setup.RegistryGroupHolder;
import io.ticticboom.mods.mm.util.BlockUtils;
import io.ticticboom.mods.mm.util.MenuUtils;
import mekanism.api.chemical.Chemical;
import mekanism.api.chemical.ChemicalStack;
import net.minecraft.world.entity.player.Inventory;

public class MekanismChemicalPortMenu<CHEMICAL extends Chemical<CHEMICAL>, STACK extends ChemicalStack<CHEMICAL>> extends MMContainerMenu implements IPortMenu {

    private final PortModel model;
    private final RegistryGroupHolder groupHolder;
    private final MekanismChemicalPortBlockEntity<CHEMICAL, STACK> be;

    protected MekanismChemicalPortMenu(PortModel model, RegistryGroupHolder groupHolder, int windowId, MekanismChemicalPortBlockEntity<CHEMICAL, STACK> be, Inventory inv) {
        super(groupHolder.getMenu().get(), groupHolder.getBlock().get(), windowId, MenuUtils.createAccessFromBlockEntity(be), 0);
        this.model = model;
        this.groupHolder = groupHolder;
        this.be = be;
        BlockUtils.setupPlayerInventory(this, inv, 0, 0);
    }

    @Override
    public <T> T getBlockEntity() {
        return (T) be;
    }

    @Override
    public PortModel getModel() {
        return model;
    }
}
