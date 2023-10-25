package io.ticticboom.mods.mm.client.container;

import io.ticticboom.mods.mm.ports.base.IPortBE;
import io.ticticboom.mods.mm.setup.model.PortModel;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.jetbrains.annotations.Nullable;

public class PortContainer extends AbstractContainerMenu {

    private PortModel model;
    public final IPortBE be;

    protected PortContainer(PortModel model,  @Nullable MenuType<?> menu, int id, Inventory inv, IPortBE be) {
        super(menu, id);
        this.model = model;
        this.be = be;
        be.storage().setupContainer(this, inv, (BlockEntity) be);
    }

    @Override
    public boolean stillValid(Player p_38874_) {
        return true;
    }

    public PortContainer(int windowId, Inventory inv, FriendlyByteBuf data, MenuType<?> type, PortModel model) {
        this(model, type, windowId, inv, (IPortBE) inv.player.level.getBlockEntity(data.readBlockPos()));
    }

    @Override
    public ItemStack quickMoveStack(Player p_38941_, int p_38942_) {
        return ItemStack.EMPTY;
    }
}
