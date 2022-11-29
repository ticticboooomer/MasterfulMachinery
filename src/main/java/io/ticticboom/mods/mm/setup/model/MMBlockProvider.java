package io.ticticboom.mods.mm.setup.model;

import mekanism.api.providers.IBlockProvider;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;

public class MMBlockProvider implements IBlockProvider {

    private RegistryObject<Block> block;

    public MMBlockProvider(RegistryObject<Block> block) {

        this.block = block;
    }

    @NotNull
    @Override
    public Block getBlock() {
        return block.get();
    }

    @Override
    public Item asItem() {
        return getBlock().asItem();
    }
}
