package io.ticticboom.mods.mm.block;

import io.ticticboom.mods.mm.datagen.provider.MMBlockstateProvider;
import io.ticticboom.mods.mm.model.PortModel;
import io.ticticboom.mods.mm.port.MMPortRegistry;
import io.ticticboom.mods.mm.setup.RegistryGroupHolder;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.RegistryObject;

public abstract class ExtraBlockType {
    public abstract RegistryObject<Block> registerBlock(ExtraBlockModel model, RegistryGroupHolder groupHolder);
    public abstract RegistryObject<Item> registerItem(ExtraBlockModel model, RegistryGroupHolder groupHolder);

    public RegistryGroupHolder register(ExtraBlockModel model) {
        RegistryGroupHolder groupHolder = new RegistryGroupHolder();
        groupHolder.setBlock(registerBlock(model, groupHolder));
        groupHolder.setItem(registerItem(model, groupHolder));
        groupHolder.setRegistryId(model.type());
        MMExtraBlockRegistry.BLOCKS.add(groupHolder);
        return groupHolder;
    }
}
