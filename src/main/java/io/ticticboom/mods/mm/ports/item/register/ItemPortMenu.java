package io.ticticboom.mods.mm.ports.item.register;

import io.ticticboom.mods.mm.menu.MMContainerMenu;
import io.ticticboom.mods.mm.model.config.PortModel;
import io.ticticboom.mods.mm.ports.IPortBlockEntity;
import io.ticticboom.mods.mm.ports.IPortPart;
import io.ticticboom.mods.mm.ports.IPortStorage;
import io.ticticboom.mods.mm.setup.RegistryGroupHolder;
import io.ticticboom.mods.mm.util.MenuUtils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.Nullable;

public class ItemPortMenu extends MMContainerMenu implements IPortPart {
    private final PortModel model;
    private final RegistryGroupHolder groupHolder;
    private final boolean isInput;
    private final Inventory inv;
    private final IPortBlockEntity be;

    public ItemPortMenu(PortModel model, RegistryGroupHolder groupHolder, boolean isInput, int windowId, Inventory inv, IPortBlockEntity be) {
        super(groupHolder, windowId, MenuUtils.createAccessFromBlockEntity(be.getBlockEntity()));
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
    public PortModel getModel() {
        return model;
    }
}
