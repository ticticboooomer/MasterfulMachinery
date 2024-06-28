package io.ticticboom.mods.mm.port.mekanism.pigment.register;

import io.ticticboom.mods.mm.model.PortModel;
import io.ticticboom.mods.mm.port.mekanism.chemical.register.MekanismChemicalPortBlock;
import io.ticticboom.mods.mm.port.mekanism.chemical.register.MekanismChemicalPortBlockItem;
import io.ticticboom.mods.mm.setup.RegistryGroupHolder;
import net.minecraft.network.chat.Component;

public class MekanismPigmentPortBlockItem extends MekanismChemicalPortBlockItem {

    public MekanismPigmentPortBlockItem(PortModel model, RegistryGroupHolder groupHolder) {
        super(model, groupHolder);
    }

    @Override
    public Component getTypeName() {
        return Component.literal("Mekanism Pigment");
    }
}