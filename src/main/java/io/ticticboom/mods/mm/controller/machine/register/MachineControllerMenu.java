package io.ticticboom.mods.mm.controller.machine.register;

import io.ticticboom.mods.mm.controller.IControllerBlockEntity;
import io.ticticboom.mods.mm.menu.MMContainerMenu;
import io.ticticboom.mods.mm.model.ControllerModel;
import io.ticticboom.mods.mm.setup.RegistryGroupHolder;
import io.ticticboom.mods.mm.util.BlockUtils;
import io.ticticboom.mods.mm.util.MenuUtils;
import lombok.Getter;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.DataSlot;

public class MachineControllerMenu extends MMContainerMenu {

    @Getter
    private final ControllerModel model;
    private final Inventory inv;
    @Getter
    private final IControllerBlockEntity be;

    public MachineControllerMenu(ControllerModel model, RegistryGroupHolder groupHolder, int windowId, Inventory inv,
            IControllerBlockEntity be) {
        super(groupHolder.getMenu().get(), groupHolder.getBlock().get(), windowId, MenuUtils.createAccessFromBlockEntity(be.getBlockEntity()), 0);
        this.model = model;
        this.inv = inv;
        this.be = be;
        BlockUtils.setupPlayerInventory(this, inv, -1, -1);

    }

    public MachineControllerMenu(ControllerModel model, RegistryGroupHolder groupHolder, int windowId, Inventory inv,
            FriendlyByteBuf buf) {
        this(model, groupHolder, windowId, inv,
                (IControllerBlockEntity) inv.player.level.getBlockEntity(buf.readBlockPos()));
    }
}
