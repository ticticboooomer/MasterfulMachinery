package io.ticticboom.mods.mm.port.botania.mana.register;

import io.ticticboom.mods.mm.model.PortModel;
import io.ticticboom.mods.mm.port.IPortItem;
import io.ticticboom.mods.mm.setup.RegistryGroupHolder;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;

public class BotaniaManaPortBlockItem extends BlockItem implements IPortItem {

    private final PortModel model;
    private final RegistryGroupHolder groupHolder;

    public BotaniaManaPortBlockItem(PortModel model, RegistryGroupHolder groupHolder) {
        super(groupHolder.getBlock().get(), new Properties());
        this.model = model;
        this.groupHolder = groupHolder;
    }

    @Override
    public Component getTypeName() {
        return Component.literal("Botania Mana").withStyle(ChatFormatting.BOLD, ChatFormatting.BLUE);
    }

    @Override
    public PortModel getModel() {
        return model;
    }
}
