package io.ticticboom.mods.mm.setup;

import com.google.gson.Gson;
import io.ticticboom.mods.mm.Ref;
import io.ticticboom.mods.mm.ports.base.MMPortTypeEntry;
import io.ticticboom.mods.mm.ports.createrotation.RotationPortTypeEntry;
import io.ticticboom.mods.mm.ports.energy.EnergyPortTypeEntry;
import io.ticticboom.mods.mm.ports.fluid.FluidPortTypeEntry;
import io.ticticboom.mods.mm.ports.item.ItemPortTypeEntry;
import io.ticticboom.mods.mm.ports.mekanism.gas.MekGasPortTypeEntry;
import io.ticticboom.mods.mm.recipe.MMRecipeEntry;
import io.ticticboom.mods.mm.recipe.pertick.PerTickRecipeEntry;
import io.ticticboom.mods.mm.recipe.simple.SimpleRecipeEntry;
import io.ticticboom.mods.mm.structure.MMStructurePart;
import io.ticticboom.mods.mm.structure.block.BlockStructurePart;
import io.ticticboom.mods.mm.structure.port.PortStructurePart;
import io.ticticboom.mods.mm.structure.tag.BlockTagStructurePart;
import io.ticticboom.mods.mm.structure.transformers.MMStructureTransform;
import io.ticticboom.mods.mm.structure.transformers.RotationStructureTransform;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
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
    public static Supplier<IForgeRegistry<MMRecipeEntry>> RECIPE_ENTRIES;
    public static Supplier<IForgeRegistry<MMStructurePart>> STRUCTURE_PARTS;
    public static Supplier<IForgeRegistry<MMStructureTransform>> STRUCTURE_TRANSFORMS;
    @SubscribeEvent
    public static void on(NewRegistryEvent event) {
        STRUCTURE_PARTS = event.create(new RegistryBuilder<MMStructurePart>().setType(MMStructurePart.class).setName(Ref.STRUCTURE_PART_REGISTRY));
        RECIPE_ENTRIES = event.create(new RegistryBuilder<MMRecipeEntry>().setType(MMRecipeEntry.class).setName(Ref.RECIPE_ENTRIES_REGISTRY));
        STRUCTURE_TRANSFORMS = event.create(new RegistryBuilder<MMStructureTransform>().setType(MMStructureTransform.class).setName(Ref.STRUCTURE_TRANSFORMS_REGISTRY));
    }

    @SubscribeEvent
    public static void registerStructureParts(RegistryEvent.Register<MMStructurePart> event) {
        event.getRegistry().registerAll(
                new BlockStructurePart().setRegistryName(Ref.StructureParts.BLOCK),
                new BlockTagStructurePart().setRegistryName(Ref.StructureParts.TAG),
                new PortStructurePart().setRegistryName(Ref.StructureParts.PORT)
        );
    }

    @SubscribeEvent
    public static void registerRecipeEntries(RegistryEvent.Register<MMRecipeEntry> event) {
        event.getRegistry().registerAll(
                new SimpleRecipeEntry().setRegistryName(Ref.RecipeEntries.SIMPLE),
                new PerTickRecipeEntry().setRegistryName(Ref.RecipeEntries.PER_TICK)
        );
    }

    @SubscribeEvent
    public static void registerStructureTransforms(RegistryEvent.Register<MMStructureTransform> event) {
        event.getRegistry().registerAll(
                new RotationStructureTransform(Rotation.CLOCKWISE_90).setRegistryName(Ref.StructureTransforms.ROT_90),
                new RotationStructureTransform(Rotation.CLOCKWISE_180).setRegistryName(Ref.StructureTransforms.ROT_180),
                new RotationStructureTransform(Rotation.COUNTERCLOCKWISE_90).setRegistryName(Ref.StructureTransforms.ROT_270)
        );
    }

    public static void registerPorts() {
        PORTS.put(Ref.Ports.ITEM, new ItemPortTypeEntry());
        PORTS.put(Ref.Ports.FLUID, new FluidPortTypeEntry());
        PORTS.put(Ref.Ports.ENERGY, new EnergyPortTypeEntry());
        if (ModList.get().isLoaded("create")) {
            PORTS.put(Ref.Ports.CREATE_ROT, new RotationPortTypeEntry());
        }
        if (ModList.get().isLoaded("mekanism")) {
            PORTS.put(Ref.Ports.MEK_GAS, new MekGasPortTypeEntry());
        }
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
