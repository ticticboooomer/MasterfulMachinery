package io.ticticboom.mods.mm.port.mekanism.slurry;

import io.ticticboom.mods.mm.compat.kjs.builder.PortConfigBuilderJS;
import io.ticticboom.mods.mm.model.PortModel;
import io.ticticboom.mods.mm.port.IPortParser;
import io.ticticboom.mods.mm.port.IPortStorageFactory;
import io.ticticboom.mods.mm.port.PortType;
import io.ticticboom.mods.mm.port.mekanism.chemical.MekanismChemicalPortStorageModel;
import io.ticticboom.mods.mm.port.mekanism.chemical.compat.MekanismChemicalConfigBuilderJS;
import io.ticticboom.mods.mm.port.mekanism.chemical.register.MekanismChemicalPortBlock;
import io.ticticboom.mods.mm.port.mekanism.chemical.register.MekanismChemicalPortBlockItem;
import io.ticticboom.mods.mm.port.mekanism.gas.MekanismGasPortParser;
import io.ticticboom.mods.mm.port.mekanism.slurry.register.*;
import io.ticticboom.mods.mm.setup.MMRegisters;
import io.ticticboom.mods.mm.setup.RegistryGroupHolder;
import mekanism.common.inventory.container.type.BaseMekanismContainerType;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Consumer;

public class MekanismSlurryPortType extends PortType {

    @Override
    public IPortParser getParser() {
        return new MekanismSlurryPortParser();
    }

    @Override
    public RegistryObject<BlockEntityType<?>> registerBlockEntity(PortModel model, RegistryGroupHolder groupHolder) {
        return MMRegisters.BLOCK_ENTITIES.register(model.id(), () -> BlockEntityType.Builder.of((p, s) -> new MekanismSlurryPortBlockEntity(model, groupHolder, model.input(), p, s)).build(null));
    }

    @Override
    public RegistryObject<Block> registerBlock(PortModel model, RegistryGroupHolder groupHolder) {
        return MMRegisters.BLOCKS.register(model.id(), () -> new MekanismSlurryPortBlock(model, groupHolder, model.input()));
    }

    @Override
    public RegistryObject<Item> registerItem(PortModel model, RegistryGroupHolder groupHolder) {
        return MMRegisters.ITEMS.register(model.id(), () -> new MekanismSlurryPortBlockItem(model, groupHolder));
    }

    @Override
    public RegistryObject<MenuType<?>> registerMenu(PortModel model, RegistryGroupHolder groupHolder) {
        return MMRegisters.MENUS.register(model.id(), () -> IForgeMenuType.create((i, o, u) -> new MekanismSlurryPortMenu(model, groupHolder, i, o, u)));
    }

    @Override
    public void registerScreen(RegistryGroupHolder groupHolder) {
        MenuScreens.register((MenuType<MekanismSlurryPortMenu>) groupHolder.getMenu().get(), MekanismSlurryPortScreen::new);
    }

    @Override
    public IPortStorageFactory createStorageFactory(Consumer<PortConfigBuilderJS> consumer) {
        var builder = new MekanismChemicalConfigBuilderJS();
        consumer.accept(builder);
        var model = (MekanismChemicalPortStorageModel) builder.build();
        return new MekanismSlurryPortStorageFactory(model);
    }
}
