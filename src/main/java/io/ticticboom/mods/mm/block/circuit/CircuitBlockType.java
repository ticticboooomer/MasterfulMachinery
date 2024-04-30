package io.ticticboom.mods.mm.block.circuit;

import io.ticticboom.mods.mm.block.ExtraBlockModel;
import io.ticticboom.mods.mm.block.ExtraBlockType;
import io.ticticboom.mods.mm.datagen.provider.MMBlockstateProvider;
import io.ticticboom.mods.mm.setup.MMRegisters;
import io.ticticboom.mods.mm.setup.RegistryGroupHolder;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.RegistryObject;

public class CircuitBlockType extends ExtraBlockType {
    @Override
    public RegistryObject<Block> registerBlock(ExtraBlockModel model, RegistryGroupHolder groupHolder) {
        return MMRegisters.BLOCKS.register(model.id(), () -> new CircuitBlock(model, groupHolder));
    }

    @Override
    public RegistryObject<Item> registerItem(ExtraBlockModel model, RegistryGroupHolder groupHolder) {
        return MMRegisters.ITEM.register(model.id(), () -> new CircuitBlockItem(model, groupHolder));
    }
}
