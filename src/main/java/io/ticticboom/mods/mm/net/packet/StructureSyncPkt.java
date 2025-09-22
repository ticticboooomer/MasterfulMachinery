package io.ticticboom.mods.mm.net.packet;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import io.ticticboom.mods.mm.structure.StructureManager;
import io.ticticboom.mods.mm.structure.StructureModel;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class StructureSyncPkt {
    public Map<ResourceLocation, JsonElement> structures;

    public StructureSyncPkt(final Map<ResourceLocation, StructureModel> structures) {
        this.structures = new HashMap<>();
        for (Map.Entry<ResourceLocation, StructureModel> structure : structures.entrySet()) {
            this.structures.put(structure.getKey(), structure.getValue().getConfig());
        }
    }

    protected StructureSyncPkt() {
    }

    public static void encode(StructureSyncPkt packet, FriendlyByteBuf buf) {
        buf.writeInt(packet.structures.size());
        for (Map.Entry<ResourceLocation, JsonElement> strcture : packet.structures.entrySet()) {
            String string = strcture.getValue().toString();
            buf.writeResourceLocation(strcture.getKey());
            buf.writeUtf(string);
        }
    }

    public static StructureSyncPkt decode(FriendlyByteBuf buf) {
        StructureSyncPkt packet = new StructureSyncPkt();
        int initialCapacity = buf.readInt();
        packet.structures = new HashMap<>(initialCapacity);
        for (int i = 0; i < initialCapacity; i++) {
            ResourceLocation key = buf.readResourceLocation();
            String string = buf.readUtf();
            packet.structures.put(key, JsonParser.parseString(string));
        }
        return packet;
    }

    public static void handle(StructureSyncPkt packet, Supplier<NetworkEvent.Context> context) {
        handler(packet);
    }

    public static void handler(StructureSyncPkt packet) {
        StructureManager.receiveStructures(packet.structures);
        StructureManager.validateAllPieces();
    }
}