package io.ticticboom.mods.mm.setup;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.RegistryObject;

@NoArgsConstructor
@Getter
@Setter
public class RegistryGroupHolder {
    private RegistryObject<Block> block;
    private RegistryObject<Item> item;
    private RegistryObject<BlockEntityType<?>> be;
    private RegistryObject<MenuType<?>> menu;
}
