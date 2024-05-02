package io.ticticboom.mods.mm.foundation.scanner;

import io.ticticboom.mods.mm.setup.MMRegisters;
import net.minecraft.world.item.BlockItem;


public class StructureScannerBlockItem extends BlockItem {
    public StructureScannerBlockItem() {
        super(MMRegisters.SCANNER_BLOCK.get(), new Properties());
    }
}
