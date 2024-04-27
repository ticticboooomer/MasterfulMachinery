package io.ticticboom.mods.mm.port.item.register;

import io.ticticboom.mods.mm.port.common.SlottedContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class ItemPortScreen extends SlottedContainerScreen<ItemPortMenu> {

    public ItemPortScreen(ItemPortMenu menu, Inventory inv, Component title) {
        super(menu, inv, title);

    }

}
