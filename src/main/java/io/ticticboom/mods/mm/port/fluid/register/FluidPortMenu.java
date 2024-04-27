package io.ticticboom.mods.mm.port.fluid.register;

import io.ticticboom.mods.mm.menu.MMContainerMenu;
import io.ticticboom.mods.mm.model.PortModel;
import io.ticticboom.mods.mm.port.IPortBlockEntity;
import io.ticticboom.mods.mm.port.IPortMenu;
import io.ticticboom.mods.mm.setup.RegistryGroupHolder;
import io.ticticboom.mods.mm.util.MenuUtils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;

public class FluidPortMenu extends MMContainerMenu implements IPortMenu {

    private final PortModel model;
    private final boolean isInput;
    private final IPortBlockEntity be;

    public FluidPortMenu(PortModel model, RegistryGroupHolder groupHolder, boolean isInput, int windowId,
            Inventory inv, IPortBlockEntity be) {
        super(groupHolder, windowId, MenuUtils.createAccessFromBlockEntity(be.getBlockEntity()), 0);
        this.model = model;
        this.isInput = isInput;
        this.be = be;
        var storage = be.getStorage();
        storage.setupContainer(this, inv, model);
    }

    public FluidPortMenu(PortModel model, RegistryGroupHolder groupHolder, boolean isInput, int windowId, Inventory inv,
            FriendlyByteBuf buf) {
        this(model, groupHolder, isInput, windowId, inv,
                (IPortBlockEntity) inv.player.level().getBlockEntity(buf.readBlockPos()));
    }

    @Override
    public PortModel getModel() {
        return model;
    }

    @Override
    public <T> T getBlockEntity() {
        return (T) be;
    }
}
