package io.ticticboom.mods.mm.client.container;

import io.ticticboom.mods.mm.block.entity.PortBlockEntity;
import io.ticticboom.mods.mm.setup.MMRegistries;
import io.ticticboom.mods.mm.setup.model.PortModel;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public class PortContainer extends AbstractContainerMenu {

    private PortModel model;
    public final PortBlockEntity be;

    protected PortContainer(PortModel model,  @Nullable MenuType<?> menu, int id, Inventory inv, PortBlockEntity be) {
        super(menu, id);
        this.model = model;
        this.be = be;
        be.storage.setupContainer(this, inv, be);
    }

    @Override
    public boolean stillValid(Player p_38874_) {
        return true;
    }

    public PortContainer(int windowId, Inventory inv, FriendlyByteBuf data, MenuType<?> type, PortModel model) {
        this(model, type, windowId, inv, (PortBlockEntity) inv.player.level.getBlockEntity(data.readBlockPos()));
    }

    @Override
    public ItemStack quickMoveStack(Player p_38941_, int p_38942_) {
        return ItemStack.EMPTY;
    }
}
