package io.ticticboom.mods.mm.setup;

import io.ticticboom.mods.mm.Ref;
import io.ticticboom.mods.mm.foundation.scanner.StructureScannerBlock;
import io.ticticboom.mods.mm.foundation.scanner.StructureScannerBlockEntity;
import io.ticticboom.mods.mm.foundation.scanner.StructureScannerMenu;
import io.ticticboom.mods.mm.foundation.scanner.StructureScannerblockItem;
import io.ticticboom.mods.mm.item.BlueprintItem;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.common.CreativeModeTabRegistry;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class MMRegisters {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Ref.ID);
    public static final DeferredRegister<Item> ITEM = DeferredRegister.create(ForgeRegistries.ITEMS, Ref.ID);
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, Ref.ID);
    public static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(ForgeRegistries.MENU_TYPES, Ref.ID);
    public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Ref.ID);

    public static final RegistryObject<StructureScannerBlock> SCANNER_BLOCK = MMRegisters.BLOCKS.register("structure_scanner", StructureScannerBlock::new);
    public static final RegistryObject<BlockEntityType<StructureScannerBlockEntity>> SCANNER_BLOCK_ENTITY = MMRegisters.BLOCK_ENTITIES.register("structure_scanner", () -> BlockEntityType.Builder.of(StructureScannerBlockEntity::new, SCANNER_BLOCK.get()).build(null));
    public static final RegistryObject<StructureScannerblockItem> SCANNER_BLOCK_ITEM = MMRegisters.ITEM.register("structure_scanner", StructureScannerblockItem::new);
    public static final RegistryObject<MenuType<StructureScannerMenu>> SCANNER_BLOCK_MENU = MMRegisters.MENUS.register("structure_scanner", () -> IForgeMenuType.create(StructureScannerMenu::new));


    public static final RegistryObject<Item> BLUEPRINT = ITEM.register("blueprint", BlueprintItem::new);

    public static final RegistryObject<CreativeModeTab> MM_TAB = TABS.register("mm", () -> CreativeModeTab.builder().title(Component.literal("Masterful Machinery"))
            .icon(() -> BLUEPRINT.get().getDefaultInstance())
            .withBackgroundLocation(Ref.Textures.CREATIVE_TAB_BG)
            .withSearchBar(55)
            .displayItems((p, o) -> o.acceptAll(ITEM.getEntries().stream().map(x -> x.get().getDefaultInstance()).toList())).build());

    public static void register() {
        var bus = FMLJavaModLoadingContext.get().getModEventBus();
        BLOCKS.register(bus);
        ITEM.register(bus);
        BLOCK_ENTITIES.register(bus);
        MENUS.register(bus);
        TABS.register(bus);
    }
}
