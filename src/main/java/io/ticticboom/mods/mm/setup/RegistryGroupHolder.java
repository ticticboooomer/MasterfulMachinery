package io.ticticboom.mods.mm.setup;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

@NoArgsConstructor
@Getter
@Setter
public class RegistryGroupHolder {
    private ResourceLocation registryId;
    private RegistryObject<Block> block;
    private RegistryObject<Item> item;
    private RegistryObject<BlockEntityType<?>> be;
    private RegistryObject<MenuType<?>> menu;
    @OnlyIn(Dist.CLIENT)
    private MenuScreens.ScreenConstructor<?, AbstractContainerScreen<?>> screen;
}
