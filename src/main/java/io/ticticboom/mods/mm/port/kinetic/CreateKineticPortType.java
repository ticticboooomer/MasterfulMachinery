package io.ticticboom.mods.mm.port.kinetic;

import io.ticticboom.mods.mm.Ref;
import io.ticticboom.mods.mm.compat.kjs.builder.PortConfigBuilderJS;
import io.ticticboom.mods.mm.model.PortModel;
import io.ticticboom.mods.mm.port.IPortParser;
import io.ticticboom.mods.mm.port.IPortStorageFactory;
import io.ticticboom.mods.mm.port.PortType;
import io.ticticboom.mods.mm.port.kinetic.compat.CreateKineticConfigBuilderJS;
import io.ticticboom.mods.mm.port.kinetic.register.CreateKineticGenPortBlockEntity;
import io.ticticboom.mods.mm.port.kinetic.register.CreateKineticPortBlock;
import io.ticticboom.mods.mm.port.kinetic.register.CreateKineticPortBlockEntity;
import io.ticticboom.mods.mm.port.kinetic.register.CreateKineticPortBlockItem;
import io.ticticboom.mods.mm.setup.MMRegisters;
import io.ticticboom.mods.mm.setup.RegistryGroupHolder;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Consumer;

public class CreateKineticPortType extends PortType {

    @Override
    public IPortParser getParser() {
        return new CreateKineticPortParser();
    }

    @Override
    public RegistryObject<BlockEntityType<?>> registerBlockEntity(PortModel model, RegistryGroupHolder groupHolder) {
        if (model.input()) {
            return  MMRegisters.BLOCK_ENTITIES.register(model.id(), () -> BlockEntityType.Builder.of((p, s) -> new CreateKineticPortBlockEntity(model, groupHolder, p, s), groupHolder.getBlock().get()).build(null));
        } else {
            return MMRegisters.BLOCK_ENTITIES.register(model.id(), () -> BlockEntityType.Builder.of((p, s) -> new CreateKineticGenPortBlockEntity(model, groupHolder, p, s), groupHolder.getBlock().get()).build(null));
        }
    }

    @Override
    public RegistryObject<Block> registerBlock(PortModel model, RegistryGroupHolder groupHolder) {
        return MMRegisters.BLOCKS.register(model.id(), () -> new CreateKineticPortBlock(model, groupHolder));
    }

    @Override
    public RegistryObject<Item> registerItem(PortModel model, RegistryGroupHolder groupHolder) {
        return MMRegisters.ITEMS.register(model.id(), () -> new CreateKineticPortBlockItem(model, groupHolder));
    }

    @Override
    public RegistryObject<MenuType<?>> registerMenu(PortModel model, RegistryGroupHolder groupHolder) {
        return null;
    }

    @Override
    public void registerScreen(RegistryGroupHolder groupHolder) {

    }

    @Override
    public IPortStorageFactory createStorageFactory(Consumer<PortConfigBuilderJS> consumer) {
        var builder = new CreateKineticConfigBuilderJS();
        consumer.accept(builder);
        return new CreateKineticPortStorageFactory((CreateKineticPortStorageModel) builder.build());
    }
}
