package io.ticticboom.mods.mm.setup;

import io.ticticboom.mods.mm.ModRoot;
import io.ticticboom.mods.mm.block.ControllerBlock;
import io.ticticboom.mods.mm.block.entity.ControllerBlockEntity;
import io.ticticboom.mods.mm.client.container.ControllerContainer;
import io.ticticboom.mods.mm.setup.model.ControllerModel;
import io.ticticboom.mods.mm.util.Deferred;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.registries.RegistryObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ControllerManager extends BaseJsonManager {
    public static Map<ResourceLocation, ControllerModel> REGISTRY = new HashMap<>();

    public static void load() {
        var path = getConfigDirectory("controllers");
        var files = loadFiles(path);

        if (files == null) {
            return;
        }

        for (var json : files) {
            var res = ControllerModel.parse(json);
            REGISTRY.put(res.id(), res);
            Deferred<RegistryObject<BlockEntityType<?>>> blockEntityType = new Deferred<>();
            final Deferred<RegistryObject<MenuType<?>>> menuType = new Deferred<>();
            final var block = MMRegistries.BLOCKS.register(res.blockId().getPath(), () -> new ControllerBlock(res, blockEntityType.data, menuType.data));
            MMRegistries.ITEMS.register(res.blockId().getPath(), () -> new BlockItem(block.get(), new Item.Properties().tab(ModRoot.MM_GROUP)));
            blockEntityType.set(MMRegistries.BLOCK_ENTITIES.register(res.blockId().getPath(), () -> BlockEntityType.Builder.of((a, b) -> new ControllerBlockEntity(blockEntityType.data.get(), a, b), block.get()).build(null)));
            menuType.set(MMRegistries.MENU_TYPES.register(res.blockId().getPath(), () -> IForgeMenuType.create((a, b, c) -> new ControllerContainer(a, b, c, menuType.data.get()))));
        }
    }
}
