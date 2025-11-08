package io.ticticboom.mods.mm.setup;

import io.ticticboom.mods.mm.Ref;
import io.ticticboom.mods.mm.debug.tool.DebugToolItem;
import io.ticticboom.mods.mm.item.BlueprintItem;
import io.ticticboom.mods.mm.structure.StructureManager;
import io.ticticboom.mods.mm.structure.projector.StructureProjectorBlock;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.*;

public class MMRegisters {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Ref.ID);
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Ref.ID);
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, Ref.ID);
    public static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(ForgeRegistries.MENU_TYPES, Ref.ID);
    public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Ref.ID);

    public static final RegistryObject<BlueprintItem> BLUEPRINT = ITEMS.register("blueprint", BlueprintItem::new);
    public static final RegistryObject<Item> DEBUG_TOOL = ITEMS.register("debug_tool", DebugToolItem::new);
    public static final RegistryObject<Block> STRUCTURE_PROJECTOR = BLOCKS.register("structure_projector", StructureProjectorBlock::new);
    public static final RegistryObject<Item> STRUCTURE_PROJECTOR_ITEM = ITEMS.register("structure_projector_item", () -> new BlockItem(STRUCTURE_PROJECTOR.get(), new Item.Properties()));

    public static final RegistryObject<CreativeModeTab> MM_TAB = TABS.register("mm", () -> CreativeModeTab.builder().title(Component.translatable("tab.mm.main"))
            .icon(() -> BLUEPRINT.get().getDefaultInstance())
            .withBackgroundLocation(Ref.UiTextures.CREATIVE_TAB_BG)
            .withSearchBar(55)
            .displayItems((p, o) -> o.acceptAll(ITEMS.getEntries().stream().map(x -> x.get().getDefaultInstance()).toList()))
            .build());

    public static final RegistryObject<CreativeModeTab> MM_STRUCTURE_TAB = TABS.register("mm_structures", () -> CreativeModeTab.builder().title(Component.translatable("tab.mm.structures"))
            .icon(() -> BLUEPRINT.get().getDefaultInstance())
            .withBackgroundLocation(Ref.UiTextures.CREATIVE_TAB_BG)
            .withSearchBar(55)
            .displayItems((p, o) -> o.acceptAll(StructureManager.STRUCTURE_BLUEPRINTS.values()))
            .build());

    public static void register() {
        var bus = FMLJavaModLoadingContext.get().getModEventBus();
        BLOCKS.register(bus);
        ITEMS.register(bus);
        BLOCK_ENTITIES.register(bus);
        MENUS.register(bus);
        TABS.register(bus);
    }
}
