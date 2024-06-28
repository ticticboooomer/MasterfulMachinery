package io.ticticboom.mods.mm.port.pneumaticcraft.air.register;

import io.ticticboom.mods.mm.model.PortModel;
import io.ticticboom.mods.mm.port.IPortItem;
import io.ticticboom.mods.mm.setup.RegistryGroupHolder;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;

public class PneumaticAirPortBlockItem  extends BlockItem implements IPortItem {
    private final PortModel model;
    private final RegistryGroupHolder groupHolder;
    private final boolean isInput;

    public PneumaticAirPortBlockItem(PortModel model, RegistryGroupHolder groupHolder, boolean isInput) {
        super(groupHolder.getBlock().get(), new Properties());
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
        return Component.literal("Pne air").withStyle(ChatFormatting.BOLD, ChatFormatting.GOLD);
    }
}
