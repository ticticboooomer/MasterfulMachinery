package io.ticticboom.mods.mm.port.item.register;

import io.ticticboom.mods.mm.menu.MMContainerMenu;
import io.ticticboom.mods.mm.model.PortModel;
import io.ticticboom.mods.mm.port.IPortBlockEntity;
import io.ticticboom.mods.mm.port.IPortMenu;
import io.ticticboom.mods.mm.port.IPortStorage;
import io.ticticboom.mods.mm.port.item.ItemPortStorageModel;
import io.ticticboom.mods.mm.setup.RegistryGroupHolder;
import io.ticticboom.mods.mm.util.MenuUtils;
import lombok.Getter;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;

public class ItemPortMenu extends MMContainerMenu implements IPortMenu {
    @Getter
    private final PortModel model;
    private final RegistryGroupHolder groupHolder;
    private final boolean isInput;
    private final Inventory inv;
    private final IPortBlockEntity be;

    private static int calcExtraSlots(IPortStorage storage) {
        ItemPortStorageModel storageModel = (ItemPortStorageModel) storage.getStorageModel();
        return storageModel.rows() * storageModel.columns();
    }

    public ItemPortMenu(PortModel model, RegistryGroupHolder groupHolder, boolean isInput, int windowId, Inventory inv, IPortBlockEntity be) {
        super(groupHolder.getMenu().get(), groupHolder.getBlock().get(), windowId, MenuUtils.createAccessFromBlockEntity(be.getBlockEntity()), calcExtraSlots(be.getStorage()));
        this.model = model;
        this.groupHolder = groupHolder;
        this.isInput = isInput;
        this.inv = inv;
        this.be = be;
        IPortStorage storage = be.getStorage();
        storage.setupContainer(this, inv, model);
    }

    public ItemPortMenu(PortModel model, RegistryGroupHolder groupHolder, boolean isInput, int windowId, Inventory inv, FriendlyByteBuf buf) {
        this(model, groupHolder, isInput, windowId, inv, (IPortBlockEntity) inv.player.level().getBlockEntity(buf.readBlockPos()));
    }

    @Override
    public <T> T getBlockEntity() {
        return (T)be;
    }
}
