package io.ticticboom.mods.mm.ports.item;

import io.ticticboom.mods.mm.model.config.PortModel;
import io.ticticboom.mods.mm.ports.IPortParser;
import io.ticticboom.mods.mm.ports.PortType;
import io.ticticboom.mods.mm.ports.item.register.ItemPortBlock;
import io.ticticboom.mods.mm.ports.item.register.ItemPortBlockEntity;
import io.ticticboom.mods.mm.ports.item.register.ItemPortBlockItem;
import io.ticticboom.mods.mm.ports.item.register.ItemPortMenu;
import io.ticticboom.mods.mm.setup.MMRegisters;
import io.ticticboom.mods.mm.setup.RegistryGroupHolder;
import io.ticticboom.mods.mm.util.PortUtils;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.registries.RegistryObject;

public class ItemPortType extends PortType {
    @Override
    public IPortParser getParser() {
        return new ItemPortParser();
    }

    @Override
    public RegistryObject<BlockEntityType<?>> registerBlockEntity(PortModel model, RegistryGroupHolder groupHolder, boolean isInput) {
        return MMRegisters.BLOCK_ENTITIES.register(PortUtils.name(model, isInput), () -> BlockEntityType.Builder.of((p, s) -> new ItemPortBlockEntity(groupHolder, model, isInput, p, s), groupHolder.getBlock().get()).build(null));
    }

    @Override
    public RegistryObject<Block> registerBlock(PortModel model, RegistryGroupHolder groupHolder, boolean isInput) {
        return MMRegisters.BLOCKS.register(PortUtils.name(model, isInput), () -> new ItemPortBlock(model, groupHolder, isInput));
    }

    @Override
    public RegistryObject<Item> registerItem(PortModel model, RegistryGroupHolder groupHolder, boolean isInput) {
        return MMRegisters.ITEM.register(PortUtils.name(model, isInput), () -> new ItemPortBlockItem(model, groupHolder, isInput));
    }

    @Override
    public RegistryObject<MenuType<?>> registerMenu(PortModel model, RegistryGroupHolder groupHolder, boolean isInput) {
        return MMRegisters.MENUS.register(PortUtils.name(model, isInput), () -> IForgeMenuType.create((i, o, u) -> new ItemPortMenu(model, groupHolder, isInput, i, o, u)));
    }
}
