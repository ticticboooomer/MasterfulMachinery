package io.ticticboom.mods.mm.event;

import io.ticticboom.mods.mm.net.MMNetwork;
import io.ticticboom.mods.mm.net.packet.ProcessesSyncPkt;
import io.ticticboom.mods.mm.net.packet.StructureSyncPkt;
import io.ticticboom.mods.mm.recipe.MachineRecipeManager;
import io.ticticboom.mods.mm.structure.StructureManager;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.network.PacketDistributor;

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

}
