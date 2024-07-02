package io.ticticboom.mods.mm.event;

import io.ticticboom.mods.mm.port.botania.mana.register.BotaniaManaPortBlockEntity;
import io.ticticboom.mods.mm.recipe.MachineRecipeManager;
import io.ticticboom.mods.mm.structure.StructureManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import vazkii.botania.api.BotaniaForgeClientCapabilities;
import vazkii.botania.forge.CapabilityUtil;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ForgeEventsListener {

    @SubscribeEvent
    public static void registerReloadListeners(AddReloadListenerEvent event) {
        event.addListener(new StructureManager());
        event.addListener(new MachineRecipeManager());
    }

    // TODO uncomment for botania port
//    @SubscribeEvent
//    public static void attachCaps(AttachCapabilitiesEvent<BlockEntity> event) {
//        if (event.getObject() instanceof BotaniaManaPortBlockEntity) {
//            event.addCapability(new ResourceLocation("botania", "wand_hud"),
//                    CapabilityUtil.makeProvider(BotaniaForgeClientCapabilities.WAND_HUD, new BotaniaManaPortBlockEntity.WandHud(((BotaniaManaPortBlockEntity) event.getObject()))));
//        }
//    }
}
