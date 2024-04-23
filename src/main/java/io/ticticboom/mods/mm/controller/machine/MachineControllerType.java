package io.ticticboom.mods.mm.controller.machine;

import io.ticticboom.mods.mm.controller.ControllerType;
import io.ticticboom.mods.mm.controller.machine.register.*;
import io.ticticboom.mods.mm.model.config.ControllerModel;
import io.ticticboom.mods.mm.setup.MMRegisters;
import io.ticticboom.mods.mm.setup.RegistryGroupHolder;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.registries.RegistryObject;

public class MachineControllerType extends ControllerType {
    @Override
    public RegistryObject<BlockEntityType<?>> registerBlockEntity(ControllerModel model, RegistryGroupHolder groupHolder) {
        return MMRegisters.BLOCK_ENTITIES.register(model.id(), () -> BlockEntityType.Builder.of((p, s) -> new MachineControllerBlockEntity(model, groupHolder, p, s), groupHolder.getBlock().get()).build(null));
    }

    @Override
    public RegistryObject<Block> registerBlock(ControllerModel model, RegistryGroupHolder groupHolder) {
        return MMRegisters.BLOCKS.register(model.id(), () -> new MachineControllerBlock(model, groupHolder));
    }

    @Override
    public RegistryObject<Item> registerItem(ControllerModel model, RegistryGroupHolder groupHolder) {
        return MMRegisters.ITEM.register(model.id(), () -> new MachineControllerBlockItem(model, groupHolder));
    }

    @Override
    public RegistryObject<MenuType<?>> registerMenu(ControllerModel model, RegistryGroupHolder groupHolder) {
        return MMRegisters.MENUS.register(model.id(), () -> IForgeMenuType.create((i,o,u) -> new MachineControllerMenu(model, groupHolder, i, o, u)));
    }

    @Override
    public void registerScreen(RegistryGroupHolder groupHolder) {
        MenuScreens.register((MenuType<MachineControllerMenu>)groupHolder.getMenu().get(), MachineControllerScreen::new);
    }
}
