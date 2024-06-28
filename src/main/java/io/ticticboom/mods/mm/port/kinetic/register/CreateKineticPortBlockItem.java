package io.ticticboom.mods.mm.port.kinetic.register;

import io.ticticboom.mods.mm.model.PortModel;
import io.ticticboom.mods.mm.port.IPortItem;
import io.ticticboom.mods.mm.setup.RegistryGroupHolder;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;

public class CreateKineticPortBlockItem extends BlockItem implements IPortItem {
    private final PortModel model;
    private final RegistryGroupHolder groupHolder;

    public CreateKineticPortBlockItem(PortModel model, RegistryGroupHolder groupHolder) {
        super(groupHolder.getBlock().get(), new Properties());
        this.model = model;
        this.groupHolder = groupHolder;
    }

    @Override
    public Component getTypeName() {
        return Component.literal("Create Rotation").withStyle(ChatFormatting.BOLD, ChatFormatting.YELLOW);
    }

    @Override
    public PortModel getModel() {
        return model;
    }
}
