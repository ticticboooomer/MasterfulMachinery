package io.ticticboom.mods.mm.net.packet;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import io.ticticboom.mods.mm.recipe.MachineRecipeManager;
import io.ticticboom.mods.mm.recipe.RecipeModel;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class ProcessesSyncPkt {
    public Map<ResourceLocation, JsonElement> recipes;

    public ProcessesSyncPkt(Map<ResourceLocation, RecipeModel> recipes) {
        this.recipes = new HashMap<>();
        for (Map.Entry<ResourceLocation, RecipeModel> entry : recipes.entrySet()) {
            this.recipes.put(entry.getKey(), entry.getValue().config());
        }
    }

    protected ProcessesSyncPkt() {

    }


    public static void encode(ProcessesSyncPkt packet, FriendlyByteBuf buf) {
        buf.writeVarInt(packet.recipes.size());
        for (Map.Entry<ResourceLocation, JsonElement> entry : packet.recipes.entrySet()) {
            buf.writeResourceLocation(entry.getKey());
            buf.writeUtf(entry.getValue().toString());
        }
    }

    public static ProcessesSyncPkt decode(FriendlyByteBuf buf) {
        ProcessesSyncPkt packet = new ProcessesSyncPkt();
        packet.recipes = new HashMap<>();
        int count = buf.readVarInt();
        for (int i = 0; i < count; i++) {
            packet.recipes.put(buf.readResourceLocation(), JsonParser.parseString(buf.readUtf()));
        }
        return packet;
    }

    public static void handle(ProcessesSyncPkt packet, Supplier<NetworkEvent.Context> context) {
        handler(packet);
    }

    public static void handler(ProcessesSyncPkt packet) {
        MachineRecipeManager.recieveRecipes(packet.recipes);
    }
}
