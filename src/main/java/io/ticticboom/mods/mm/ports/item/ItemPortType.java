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
    public RegistryObject<BlockEntityType<?>> registerBlockEntity(PortModel model, RegistryGroupHolder groupHolder) {
        return MMRegisters.BLOCK_ENTITIES.register(model.id(), () -> BlockEntityType.Builder.of((p, s) -> new ItemPortBlockEntity(groupHolder, model, model.input(), p, s), groupHolder.getBlock().get()).build(null));
    }

    @Override
    public RegistryObject<Block> registerBlock(PortModel model, RegistryGroupHolder groupHolder) {
        return MMRegisters.BLOCKS.register(model.id(), () -> new ItemPortBlock(model, groupHolder, model.input()));
    }

    @Override
    public RegistryObject<Item> registerItem(PortModel model, RegistryGroupHolder groupHolder) {
        return MMRegisters.ITEM.register(model.id(), () -> new ItemPortBlockItem(model, groupHolder, model.input()));
    }

    @Override
    public RegistryObject<MenuType<?>> registerMenu(PortModel model, RegistryGroupHolder groupHolder) {
        return MMRegisters.MENUS.register(model.id(), () -> IForgeMenuType.create((i, o, u) -> new ItemPortMenu(model, groupHolder, model.input(), i, o, u)));
    }
}
