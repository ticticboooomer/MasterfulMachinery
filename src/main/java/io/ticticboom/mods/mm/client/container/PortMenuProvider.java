package io.ticticboom.mods.mm.client.container;

import io.ticticboom.mods.mm.block.entity.PortBlockEntity;
import io.ticticboom.mods.mm.setup.model.PortModel;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import org.jetbrains.annotations.Nullable;

public class PortMenuProvider implements MenuProvider {

    private PortBlockEntity be;
    private MenuType<?> menuType;
    private PortModel model;

    public PortMenuProvider(PortBlockEntity be, MenuType<?> menuType, PortModel model) {
        this.be = be;
        this.menuType = menuType;
        this.model = model;
    }

    @Override
    public Component getDisplayName() {
        return Component.literal("Port");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int id, Inventory inv, Player player) {
        return new PortContainer(model, menuType, id, inv, be);
    }
}
