package io.ticticboom.mods.mm.setup;

import io.ticticboom.mods.mm.ModRoot;
import io.ticticboom.mods.mm.block.ControllerBlock;
import io.ticticboom.mods.mm.block.PortBlock;
import io.ticticboom.mods.mm.block.entity.ControllerBlockEntity;
import io.ticticboom.mods.mm.block.entity.PortBlockEntity;
import io.ticticboom.mods.mm.client.container.ControllerContainer;
import io.ticticboom.mods.mm.client.container.PortContainer;
import io.ticticboom.mods.mm.ports.base.MMPortTypeEntry;
import io.ticticboom.mods.mm.setup.model.ControllerModel;
import io.ticticboom.mods.mm.setup.model.PortModel;
import io.ticticboom.mods.mm.util.Deferred;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.registries.RegistryObject;

import javax.sound.sampled.Port;
import java.util.HashMap;
import java.util.Map;

public class PortManager extends BaseJsonManager {
    public static Map<ResourceLocation, PortModel> REGISTRY = new HashMap<>();

    public static void load() {
        var path = getConfigDirectory("ports");
        var files = loadFiles(path);

        if (files == null) {
            return;
        }

        for (var json : files) {
            var res = PortModel.parse(json);
            if (res == null) {
                continue;
            }
            REGISTRY.put(res.id(), res);
            MMPortTypeEntry entry = MMRegistries.PORTS.get(res.port());
            final Deferred<RegistryObject<MenuType<?>>> menuType = new Deferred<>();
            Deferred<RegistryObject<BlockEntityType<?>>> blockEntityType = new Deferred<>();
            final var block = MMRegistries.BLOCKS.register(res.blockId().getPath(), entry.blockSupplier(res.input(), res, menuType, blockEntityType));
            MMRegistries.ITEMS.register(res.blockId().getPath(), () -> new BlockItem(block.get(), new Item.Properties().tab(ModRoot.MM_GROUP)));
            blockEntityType.set(MMRegistries.BLOCK_ENTITIES.register(res.blockId().getPath(), () -> BlockEntityType.Builder.of(entry.beSupplier(res.input(), res, blockEntityType.data), block.get()).build(null)));
            menuType.set(MMRegistries.MENU_TYPES.register(res.blockId().getPath(), () -> IForgeMenuType.create((a, b, c) -> new PortContainer(a, b, c, menuType.data.get(), res))));
        }
    }
}
