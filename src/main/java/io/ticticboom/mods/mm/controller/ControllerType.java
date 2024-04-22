package io.ticticboom.mods.mm.controller;

import io.ticticboom.mods.mm.model.config.ControllerModel;
import io.ticticboom.mods.mm.setup.RegistryGroupHolder;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.RegistryObject;

public abstract class ControllerType {

    public abstract RegistryObject<BlockEntityType<?>> registerBlockEntity(ControllerModel model, RegistryGroupHolder groupHolder);

    public abstract RegistryObject<Block> registerBlock(ControllerModel model, RegistryGroupHolder groupHolder);

    public abstract RegistryObject<Item> registerItem(ControllerModel model, RegistryGroupHolder groupHolder);

    public abstract RegistryObject<MenuType<?>> registerMenu(ControllerModel model, RegistryGroupHolder groupHolder);

    public RegistryGroupHolder register(ControllerModel model) {
        RegistryGroupHolder groupHolder = new RegistryGroupHolder();
        groupHolder.setMenu(registerMenu(model, groupHolder));
        groupHolder.setBlock(registerBlock(model, groupHolder));
        groupHolder.setBe(registerBlockEntity(model, groupHolder));
        groupHolder.setItem(registerItem(model, groupHolder));
        return groupHolder;
    }

}
