package io.ticticboom.mods.mm.event;

import io.ticticboom.mods.mm.net.MMNetwork;
import io.ticticboom.mods.mm.net.packet.ProcessesSyncPkt;
import io.ticticboom.mods.mm.net.packet.StructureSyncPkt;
import io.ticticboom.mods.mm.port.botania.mana.register.BotaniaManaPortBlockEntity;
import io.ticticboom.mods.mm.recipe.MachineRecipeManager;
import io.ticticboom.mods.mm.structure.StructureManager;
import net.minecraft.network.protocol.Packet;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ClientPlayerNetworkEvent;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.TagsUpdatedEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.server.ServerStartedEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.network.PacketDistributor;
import vazkii.botania.api.BotaniaForgeClientCapabilities;
import vazkii.botania.forge.CapabilityUtil;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ForgeEventsListener {

    @SubscribeEvent
    public static void registerReloadListeners(AddReloadListenerEvent event) {
        event.addListener(new StructureManager());
        event.addListener(new MachineRecipeManager());
    }

    @SubscribeEvent
    public static void onJoin(PlayerEvent.PlayerLoggedInEvent event) {
        var structurePacket = new StructureSyncPkt(StructureManager.STRUCTURES);
        MMNetwork.INSTANCE.send(PacketDistributor.PLAYER.with(() -> ((ServerPlayer) event.getEntity())), structurePacket);
        
        var processPacket  = new ProcessesSyncPkt(MachineRecipeManager.RECIPES);
        MMNetwork.INSTANCE.send(PacketDistributor.PLAYER.with(() -> ((ServerPlayer) event.getEntity())), processPacket);
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
