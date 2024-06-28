package io.ticticboom.mods.mm.port.mekanism.pigment;

import io.ticticboom.mods.mm.compat.kjs.builder.PortConfigBuilderJS;
import io.ticticboom.mods.mm.model.PortModel;
import io.ticticboom.mods.mm.port.IPortParser;
import io.ticticboom.mods.mm.port.IPortStorageFactory;
import io.ticticboom.mods.mm.port.PortType;
import io.ticticboom.mods.mm.port.mekanism.chemical.MekanismChemicalPortStorageModel;
import io.ticticboom.mods.mm.port.mekanism.chemical.compat.MekanismChemicalConfigBuilderJS;
import io.ticticboom.mods.mm.port.mekanism.pigment.register.*;
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

public class MekanismPigmentPortType extends PortType {
    @Override
    public IPortParser getParser() {
        return new MekanismPigmentPortParser();
    }

    @Override
    public RegistryObject<BlockEntityType<?>> registerBlockEntity(PortModel model, RegistryGroupHolder groupHolder) {
        return MMRegisters.BLOCK_ENTITIES.register(model.id(), () -> BlockEntityType.Builder.of((p, s) -> new MekanismPigmentPortBlockEntity(model, groupHolder, model.input(), p, s)).build(null));
    }

    @Override
    public RegistryObject<Block> registerBlock(PortModel model, RegistryGroupHolder groupHolder) {
        return MMRegisters.BLOCKS.register(model.id(), () -> new MekanismPigmentPortBlock(model, groupHolder, model.input()));
    }

    @Override
    public RegistryObject<Item> registerItem(PortModel model, RegistryGroupHolder groupHolder) {
        return MMRegisters.ITEMS.register(model.id(), () -> new MekanismPigmentPortBlockItem(model, groupHolder));
    }

    @Override
    public RegistryObject<MenuType<?>> registerMenu(PortModel model, RegistryGroupHolder groupHolder) {
        return MMRegisters.MENUS.register(model.id(), () -> IForgeMenuType.create((i, o, u) -> new MekanismPigmentPortMenu(model, groupHolder, i, o, u)));
    }

    @Override
    public void registerScreen(RegistryGroupHolder groupHolder) {
        MenuScreens.register((MenuType<MekanismPigmentPortMenu>) groupHolder.getMenu().get(), MekanismPigmentPortScreen::new);
    }

    @Override
    public IPortStorageFactory createStorageFactory(Consumer<PortConfigBuilderJS> consumer) {
        var builder = new MekanismChemicalConfigBuilderJS();
        consumer.accept(builder);
        return new MekanismPigmentPortStorageFactory((MekanismChemicalPortStorageModel) builder.build());
    }
}
