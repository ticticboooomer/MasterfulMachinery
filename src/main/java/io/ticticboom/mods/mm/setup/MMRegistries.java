package io.ticticboom.mods.mm.setup;

import com.google.gson.Gson;
import io.ticticboom.mods.mm.Ref;
import io.ticticboom.mods.mm.ports.base.MMPortTypeEntry;
import io.ticticboom.mods.mm.ports.item.ItemPortTypeEntry;
import io.ticticboom.mods.mm.structure.MMStructurePart;
import io.ticticboom.mods.mm.structure.block.BlockStructurePart;
import io.ticticboom.mods.mm.structure.tag.BlockTagStructurePart;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLConstructModEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.*;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class MMRegistries {

    public static final Gson GSON = new Gson();
    public static DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Ref.ID);
    public static DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Ref.ID);
    public static DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, Ref.ID);
    public static DeferredRegister<MenuType<?>> MENU_TYPES = DeferredRegister.create(ForgeRegistries.CONTAINERS, Ref.ID);

    public static Map<ResourceLocation, MMPortTypeEntry> PORTS = new HashMap<>();
    public static Supplier<IForgeRegistry<MMStructurePart>> STRUCTURE_PARTS;

    @SubscribeEvent
    public static void on(NewRegistryEvent event) {
        STRUCTURE_PARTS = event.create(new RegistryBuilder<MMStructurePart>().setType(MMStructurePart.class).setName(Ref.STRUCTURE_PART_REGISTRY));
    }

    @SubscribeEvent
    public static void on(RegistryEvent.Register<MMStructurePart> event) {
        event.getRegistry().registerAll(
                new BlockStructurePart().setRegistryName(Ref.StructureParts.BLOCK),
                new BlockTagStructurePart().setRegistryName(Ref.StructureParts.TAG)
        );
    }

    public static void registerPorts() {
        PORTS.put(Ref.Ports.ITEM, new ItemPortTypeEntry());
    }

    @SubscribeEvent
    public static void on(FMLConstructModEvent event) {
        ControllerManager.load();
        PortManager.load();
    }

    public static void register() {
        registerPorts();
        var bus = FMLJavaModLoadingContext.get().getModEventBus();
        BLOCKS.register(bus);
        ITEMS.register(bus);
        BLOCK_ENTITIES.register(bus);
        MENU_TYPES.register(bus);
    }
}
