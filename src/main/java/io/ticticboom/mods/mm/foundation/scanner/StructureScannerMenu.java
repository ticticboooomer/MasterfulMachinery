package io.ticticboom.mods.mm.foundation.scanner;

import io.ticticboom.mods.mm.menu.MMContainerMenu;
import io.ticticboom.mods.mm.setup.MMRegisters;
import io.ticticboom.mods.mm.util.BlockUtils;
import io.ticticboom.mods.mm.util.MenuUtils;
import lombok.Getter;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;

public class StructureScannerMenu extends MMContainerMenu {

    @Getter
    private final StructureScannerBlockEntity be;

    public StructureScannerMenu(int windowId, Inventory inv, StructureScannerBlockEntity be) {
        super(MMRegisters.SCANNER_BLOCK_MENU.get(), MMRegisters.SCANNER_BLOCK.get(), windowId, MenuUtils.createAccessFromBlockEntity(be), 0);
        this.be = be;
        var container = new StructureScannerContainer(be.getHandler());
        this.addSlot(new Slot(container, 0, 152, 8));
        BlockUtils.setupPlayerInventory(this, inv, 0, 0);
    }

    public StructureScannerMenu(int windowId, Inventory inv, FriendlyByteBuf buf) {
        this(windowId, inv, (StructureScannerBlockEntity) inv.player.level().getBlockEntity(buf.readBlockPos()));
    }
}
