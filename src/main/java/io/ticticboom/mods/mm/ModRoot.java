package io.ticticboom.mods.mm;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import io.ticticboom.mods.mm.client.container.ControllerContainer;
import io.ticticboom.mods.mm.client.container.PortContainer;
import io.ticticboom.mods.mm.client.screen.ControllerScreen;
import io.ticticboom.mods.mm.client.screen.PortScreen;
import io.ticticboom.mods.mm.datagen.DataGenFactory;
import io.ticticboom.mods.mm.datagen.GeneratedRepoSource;
import io.ticticboom.mods.mm.datagen.gen.MMBlockStateProvider;
import io.ticticboom.mods.mm.datagen.gen.MMItemModelProvider;
import io.ticticboom.mods.mm.datagen.gen.MMLangProvider;
import io.ticticboom.mods.mm.datagen.gen.MMLootTableProvider;
import io.ticticboom.mods.mm.setup.MMRegistries;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.data.DataGenerator;
import net.minecraft.server.packs.repository.PackRepository;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.registries.RegistryObject;

import java.io.IOException;

@Mod(Ref.ID)
public class ModRoot {

    private static DataGenerator gen;
    private static boolean generated = false;

    public static CreativeModeTab MM_GROUP = new CreativeModeTab("mm") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(Items.ACACIA_PLANKS);
        }
    };

    public ModRoot() {
        DataGenFactory.init();
        MMRegistries.register();
        registerDataGen();
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        bus.addListener(this::clientSetup);
        try {
            if (FMLEnvironment.dist == Dist.CLIENT) {
                Minecraft.getInstance().getResourcePackRepository().addPackFinder(new GeneratedRepoSource());
            }
        } catch (Exception ignored) {
        }
        MinecraftForge.EVENT_BUS.addListener(this::serverStart);
    }

    public void serverStart(ServerStartingEvent event) {
        event.getServer().getPackRepository().addPackFinder(new GeneratedRepoSource());
    }

    private void registerDataGen() {
        gen = DataGenFactory.create();
        gen.addProvider(new MMLootTableProvider(gen));

        if (FMLEnvironment.dist != Dist.DEDICATED_SERVER) {
            ExistingFileHelper efh = new ExistingFileHelper(ImmutableList.of(), ImmutableSet.of(), false, null, null);
            gen.addProvider(new MMBlockStateProvider(gen, efh));
            gen.addProvider(new MMItemModelProvider(gen, efh));
            gen.addProvider(new MMLangProvider(gen));
        }
    }

    public static void generate() {
        if (!generated) {
            try {
                if (!ModLoader.isLoadingStateValid()) {
                    return;
                }
                gen.run();
            } catch (IOException e) {
                e.printStackTrace();
            }
            generated = true;
        }
    }

    public static void injectDatapackFinder(PackRepository resourcePacks) {
        if (DistExecutor.unsafeRunForDist(() -> () -> resourcePacks != Minecraft.getInstance().getResourcePackRepository(), () -> () -> true)) {
            resourcePacks.addPackFinder(new GeneratedRepoSource());
            Ref.LOG.info("Injecting data pack finder.");
        }
    }

    private void clientSetup(final FMLClientSetupEvent event) {
        for (RegistryObject<Block> entry : MMRegistries.BLOCKS.getEntries()) {
            ItemBlockRenderTypes.setRenderLayer(entry.get(), layer -> layer == RenderType.solid() || layer == RenderType.translucent());
        }
        for (RegistryObject<MenuType<?>> entry : MMRegistries.MENU_TYPES.getEntries()) {
            if (entry.getId().getPath().endsWith("controller")) {
                MenuScreens.register((MenuType<ControllerContainer>) entry.get(), ControllerScreen::new);
            } else if (entry.getId().getPath().endsWith("port")) {
                MenuScreens.register((MenuType<PortContainer>) entry.get(), PortScreen::new);
            }
        }
    }
}
