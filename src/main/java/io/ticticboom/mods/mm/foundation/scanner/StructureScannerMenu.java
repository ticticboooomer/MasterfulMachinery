package io.ticticboom.mods.mm.foundation.scanner;

import io.ticticboom.mods.mm.menu.MMContainerMenu;
import io.ticticboom.mods.mm.setup.MMRegisters;
import io.ticticboom.mods.mm.util.MenuUtils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;

public class StructureScannerMenu extends MMContainerMenu {

    private final StructureScannerBlockEntity be;

    public StructureScannerMenu(int windowId, Inventory inv, StructureScannerBlockEntity be) {
        super(MMRegisters.SCANNER_BLOCK_MENU.get(), MMRegisters.SCANNER_BLOCK.get(), windowId, MenuUtils.createAccessFromBlockEntity(be), 0);
        this.be = be;
    }

    public StructureScannerMenu(int windowId, Inventory inv, FriendlyByteBuf buf) {
        this(windowId, inv, (StructureScannerBlockEntity) inv.player.level().getBlockEntity(buf.readBlockPos()));
    }
}
