package io.ticticboom.mods.mm.controller.machine.register;

import io.ticticboom.mods.mm.controller.IControllerBlockEntity;
import io.ticticboom.mods.mm.menu.MMContainerMenu;
import io.ticticboom.mods.mm.model.config.ControllerModel;
import io.ticticboom.mods.mm.setup.RegistryGroupHolder;
import io.ticticboom.mods.mm.util.MenuUtils;
import lombok.Getter;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public class MachineControllerMenu extends MMContainerMenu {

    @Getter
    private final ControllerModel model;
    private final Inventory inv;
    @Getter
    private final IControllerBlockEntity be;

    public MachineControllerMenu(ControllerModel  model, RegistryGroupHolder groupHolder, int windowId, Inventory inv, IControllerBlockEntity be) {
        super(groupHolder, windowId, MenuUtils.createAccessFromBlockEntity(be.getBlockEntity()), 0);
        this.model = model;
        this.inv = inv;
        this.be = be;
    }

    public MachineControllerMenu(ControllerModel model, RegistryGroupHolder groupHolder, int windowId, Inventory inv, FriendlyByteBuf buf) {
        this(model, groupHolder, windowId, inv, (IControllerBlockEntity) inv.player.level().getBlockEntity(buf.readBlockPos()));
    }
}
