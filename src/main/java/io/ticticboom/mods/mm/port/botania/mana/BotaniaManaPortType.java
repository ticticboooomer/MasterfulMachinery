package io.ticticboom.mods.mm.port.botania.mana;

import io.ticticboom.mods.mm.compat.kjs.builder.PortConfigBuilderJS;
import io.ticticboom.mods.mm.model.PortModel;
import io.ticticboom.mods.mm.port.IPortParser;
import io.ticticboom.mods.mm.port.IPortStorageFactory;
import io.ticticboom.mods.mm.port.PortType;
import io.ticticboom.mods.mm.port.botania.mana.compat.BotaniaManaPortBuilderJS;
import io.ticticboom.mods.mm.port.botania.mana.register.BotaniaManaPortBlock;
import io.ticticboom.mods.mm.port.botania.mana.register.BotaniaManaPortBlockEntity;
import io.ticticboom.mods.mm.port.botania.mana.register.BotaniaManaPortBlockItem;
import io.ticticboom.mods.mm.setup.MMRegisters;
import io.ticticboom.mods.mm.setup.RegistryGroupHolder;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Consumer;

public class BotaniaManaPortType extends PortType {

    @Override
    public IPortParser getParser() {
        return new BotaniaManaPortParser();
    }

    @Override
    public RegistryObject<BlockEntityType<?>> registerBlockEntity(PortModel model, RegistryGroupHolder groupHolder) {
        return MMRegisters.BLOCK_ENTITIES.register(model.id(), () -> BlockEntityType.Builder.of((a, b) -> new BotaniaManaPortBlockEntity(model, groupHolder, a, b), groupHolder.getBlock().get()).build(null));
    }

    @Override
    public RegistryObject<Block> registerBlock(PortModel model, RegistryGroupHolder groupHolder) {
        return MMRegisters.BLOCKS.register(model.id(), () -> new BotaniaManaPortBlock(model, groupHolder));
    }

    @Override
    public RegistryObject<Item> registerItem(PortModel model, RegistryGroupHolder groupHolder) {
        return MMRegisters.ITEMS.register(model.id(), () -> new BotaniaManaPortBlockItem(model, groupHolder));
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
        var b = new BotaniaManaPortBuilderJS();
        consumer.accept(b);
        return new BotaniaManaPortStorageFactory((BotaniaManaPortStorageModel)b.build());
    }
}
