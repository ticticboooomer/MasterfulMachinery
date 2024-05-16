package io.ticticboom.mods.mm.port.mekanism.gas.register;

import io.ticticboom.mods.mm.model.PortModel;
import io.ticticboom.mods.mm.port.mekanism.chemical.register.MekanismChemicalPortBlockItem;
import io.ticticboom.mods.mm.setup.RegistryGroupHolder;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;

public class MekanismGasPortBlockItem extends MekanismChemicalPortBlockItem {

    public MekanismGasPortBlockItem(PortModel model, RegistryGroupHolder groupHolder) {
        super(model, groupHolder);
    }

    @Override
    public Component getTypeName() {
        return Component.literal("Mekanism Gas").withStyle(ChatFormatting.BOLD, ChatFormatting.LIGHT_PURPLE);
    }
}
