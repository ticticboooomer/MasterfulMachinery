package io.ticticboom.mods.mm.port.mekanism.infuse.register;

import io.ticticboom.mods.mm.model.PortModel;
import io.ticticboom.mods.mm.port.mekanism.chemical.register.MekanismChemicalPortBlockItem;
import io.ticticboom.mods.mm.setup.RegistryGroupHolder;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;

public class MekanismInfusePortBlockItem extends MekanismChemicalPortBlockItem {

    public MekanismInfusePortBlockItem(PortModel model, RegistryGroupHolder groupHolder) {
        super(model, groupHolder);
    }

    @Override
    public Component getTypeName() {
        return Component.literal("Mekanism Infusion").withStyle(ChatFormatting.BOLD, ChatFormatting.DARK_RED);
    }
}
