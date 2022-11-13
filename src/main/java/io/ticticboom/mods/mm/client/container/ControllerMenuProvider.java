package io.ticticboom.mods.mm.client.container;

import io.ticticboom.mods.mm.block.entity.ControllerBlockEntity;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import org.jetbrains.annotations.Nullable;

public class ControllerMenuProvider implements MenuProvider {

    private final ControllerBlockEntity be;
    private final MenuType<?> mType;

    public ControllerMenuProvider(ControllerBlockEntity be, MenuType<?> mType) {

        this.be = be;
        this.mType = mType;
    }

    @Override
    public Component getDisplayName() {
        return new TextComponent("Controller");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int id, Inventory inv, Player player) {
        return new ControllerContainer(mType, id, inv, be);
    }
}
