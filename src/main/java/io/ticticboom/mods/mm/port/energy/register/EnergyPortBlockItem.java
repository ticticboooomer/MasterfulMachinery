package io.ticticboom.mods.mm.port.energy.register;

import io.ticticboom.mods.mm.model.PortModel;
import io.ticticboom.mods.mm.port.IPortItem;
import io.ticticboom.mods.mm.port.IPortPart;
import io.ticticboom.mods.mm.setup.MMRegisters;
import io.ticticboom.mods.mm.setup.RegistryGroupHolder;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;

public class EnergyPortBlockItem extends BlockItem implements IPortItem {
    private final PortModel model;
    private final RegistryGroupHolder groupHolder;
    private final boolean isInput;

    public EnergyPortBlockItem(PortModel model, RegistryGroupHolder groupHolder, boolean isInput) {
        super(groupHolder.getBlock().get(), new Properties().tab(MMRegisters.MM_TAB));
        this.model = model;
        this.groupHolder = groupHolder;
        this.isInput = isInput;
    }

    @Override
    public PortModel getModel() {
        return model;
    }

    @Override
    public Component getTypeName() {
        return Component.literal("Energy").withStyle(ChatFormatting.BOLD, ChatFormatting.GOLD);
    }
}
