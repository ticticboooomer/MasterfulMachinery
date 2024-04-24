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

    public abstract RegistryObject<BlockEntityType<?>> registerBlockEntity(PortModel model, RegistryGroupHolder groupHolder);
    public abstract RegistryObject<Block> registerBlock(PortModel model, RegistryGroupHolder groupHolder);
    public abstract RegistryObject<Item> registerItem(PortModel model, RegistryGroupHolder groupHolder);
    public abstract RegistryObject<MenuType<?>> registerMenu(PortModel model, RegistryGroupHolder groupHolder);
    public abstract void registerScreen(RegistryGroupHolder groupHolder);

    public RegistryGroupHolder register(PortModel model) {
        RegistryGroupHolder groupHolder = new RegistryGroupHolder();
        groupHolder.setMenu(registerMenu(model, groupHolder));
        groupHolder.setBlock(registerBlock(model, groupHolder));
        groupHolder.setBe(registerBlockEntity(model, groupHolder));
        groupHolder.setItem(registerItem(model, groupHolder));
        groupHolder.setRegistryId(model.type());
        MMPortRegistry.PORTS.add(groupHolder);
        return groupHolder;
    }
}
