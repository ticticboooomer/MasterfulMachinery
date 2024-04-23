package io.ticticboom.mods.mm.ports;

import io.ticticboom.mods.mm.model.config.PortModel;
import io.ticticboom.mods.mm.setup.RegistryGroupHolder;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.RegistryObject;

public abstract class PortType {
    public abstract IPortParser getParser();

    public abstract RegistryObject<BlockEntityType<?>> registerBlockEntity(PortModel model, RegistryGroupHolder groupHolder, boolean isInput);
    public abstract RegistryObject<Block> registerBlock(PortModel model, RegistryGroupHolder groupHolder, boolean isInput);
    public abstract RegistryObject<Item> registerItem(PortModel model, RegistryGroupHolder groupHolder, boolean isInput);
    public abstract RegistryObject<MenuType<?>> registerMenu(PortModel model, RegistryGroupHolder groupHolder, boolean isInput);
    public RegistryGroupHolder register(PortModel model, boolean isInput) {
        RegistryGroupHolder groupHolder = new RegistryGroupHolder();
        groupHolder.setMenu(registerMenu(model, groupHolder, isInput));
        groupHolder.setBlock(registerBlock(model, groupHolder, isInput));
        groupHolder.setBe(registerBlockEntity(model, groupHolder, isInput));
        groupHolder.setItem(registerItem(model, groupHolder, isInput));
        groupHolder.setRegistryId(model.type());
        return groupHolder;
    }
}
