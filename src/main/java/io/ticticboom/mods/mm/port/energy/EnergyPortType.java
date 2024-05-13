package io.ticticboom.mods.mm.port.energy;

import io.ticticboom.mods.mm.compat.kjs.builder.port.PortConfigBuilderJS;
import io.ticticboom.mods.mm.model.PortModel;
import io.ticticboom.mods.mm.port.IPortParser;
import io.ticticboom.mods.mm.port.IPortStorageFactory;
import io.ticticboom.mods.mm.port.PortType;
import io.ticticboom.mods.mm.port.common.ISlottedPortStorageModel;
import io.ticticboom.mods.mm.port.energy.compat.EnergyPortConfigBuilderJS;
import io.ticticboom.mods.mm.port.energy.register.*;
import io.ticticboom.mods.mm.setup.MMRegisters;
import io.ticticboom.mods.mm.setup.RegistryGroupHolder;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Consumer;

public class EnergyPortType extends PortType {
    @Override
    public IPortParser getParser() {
        return new EnergyPortParser();
    }

    @Override
    public RegistryObject<BlockEntityType<?>> registerBlockEntity(PortModel model, RegistryGroupHolder groupHolder) {
        return MMRegisters.BLOCK_ENTITIES.register(model.id(), () -> BlockEntityType.Builder.of((p, s) -> new EnergyPortBlockEntity(model, groupHolder, model.input(), p, s), groupHolder.getBlock().get()).build(null));
    }

    @Override
    public RegistryObject<Block> registerBlock(PortModel model, RegistryGroupHolder groupHolder) {
        return MMRegisters.BLOCKS.register(model.id(), () -> new EnergyPortBlock(model, groupHolder, model.input()));
    }

    @Override
    public RegistryObject<Item> registerItem(PortModel model, RegistryGroupHolder groupHolder) {
        return MMRegisters.ITEMS.register(model.id(), () -> new EnergyPortBlockItem(model, groupHolder, model.input()));
    }

    @Override
    public RegistryObject<MenuType<?>> registerMenu(PortModel model, RegistryGroupHolder groupHolder) {
        return MMRegisters.MENUS.register(model.id(), () -> IForgeMenuType.create((i, o, u) -> new EnergyPortMenu(model, groupHolder, model.input(), i, o, u)));
    }

    @Override
    public void registerScreen(RegistryGroupHolder groupHolder) {
        MenuScreens.register((MenuType<EnergyPortMenu>)groupHolder.getMenu().get(), EnergyPortScreen::new);
    }

    @Override
    public IPortStorageFactory createStorageFactory(Consumer<PortConfigBuilderJS> consumer) {
        var builder = new EnergyPortConfigBuilderJS();
        consumer.accept(builder);
        return new EnergyPortStorageFactory(((EnergyPortStorageModel) builder.build()));
    }
}
