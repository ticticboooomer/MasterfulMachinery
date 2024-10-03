package io.ticticboom.mods.mm.net;

import io.ticticboom.mods.mm.Ref;
import io.ticticboom.mods.mm.net.packet.ProcessesSyncPkt;
import io.ticticboom.mods.mm.net.packet.StructureSyncPkt;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

public class MMNetwork {

    private static final String PROTOCOL_VERSION = "1";
    public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
            Ref.id("main"),
            () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals
    );


    public static void init() {
        int index = 0;
        INSTANCE.registerMessage(index++, StructureSyncPkt.class, StructureSyncPkt::encode, StructureSyncPkt::decode, StructureSyncPkt::handle);
        INSTANCE.registerMessage(index++, ProcessesSyncPkt.class, ProcessesSyncPkt::encode, ProcessesSyncPkt::decode, ProcessesSyncPkt::handle);
    }
}
