package io.ticticboom.mods.mm.thread;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import io.ticticboom.mods.mm.Ref;
import io.ticticboom.mods.mm.block.ControllerBlock;
import io.ticticboom.mods.mm.ports.base.IPortBlock;
import io.ticticboom.mods.mm.setup.MMRegistries;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.fml.loading.FMLPaths;

import java.io.IOException;
import java.nio.file.Files;
import java.util.*;

public class StructureGenThread extends Thread {

    private final BlockPos start;
    private final BlockPos end;
    private final Level level;

    public StructureGenThread(BlockPos start, BlockPos end, Level level) {

        this.start = start;
        this.end = end;
        this.level = level;
    }

    private List<List<String>> layout = new ArrayList<>();
    private Map<String, JsonObject> key = new HashMap<>();
    private int counter = 64;

    private String increment() {
        counter++;
        if ((char) counter == ':' || (char) counter == 'C') {
            counter++;
        }
        return Character.toString((char) counter);
    }


    private String setPort(IPortBlock port, BlockPos pos) {
        for (Map.Entry<String, JsonObject> entry : key.entrySet()) {
            if (entry.getValue().get("type").getAsString().equals("mm:port")) {
                if (entry.getValue().get("port").getAsString().equals(port.model().port().toString()) &&
                        entry.getValue().get("input").getAsBoolean() == port.model().input()) {
                    return entry.getKey();
                }
            }
        }
        var json = new JsonObject();
        json.addProperty("type", Ref.StructureParts.PORT.toString());
        json.addProperty("port", port.model().port().toString());
        json.addProperty("input", port.model().input());
        var chara = increment();
        key.put(chara, json);
        return chara;
    }

    private String setBlock(Block block, BlockPos pos) {
        for (Map.Entry<String, JsonObject> entry : key.entrySet()) {
            if (entry.getValue().get("type").getAsString().equals("mm:block")) {
                if (entry.getValue().get("block").getAsString().equals(block.getRegistryName().toString())) {
                    return entry.getKey();
                }
            }
        }
        var json = new JsonObject();
        json.addProperty("type", Ref.StructureParts.BLOCK.toString());
        json.addProperty("block", block.getRegistryName().toString());
        var chara = increment();
        key.put(chara, json);
        return chara;
    }

    private void save(String controllerId) {
        var layoutJson = new JsonArray();
        Collections.reverse(layout);
        for (List<String> strings : layout) {
            var inner = new JsonArray();
            for (String string : strings) {
                inner.add(string);
            }
            layoutJson.add(inner);
        }

        var keyJson = new JsonObject();
        for (Map.Entry<String, JsonObject> entry : key.entrySet()) {
            keyJson.add(entry.getKey(), entry.getValue());
        }


        var json = new JsonObject();
        json.addProperty("controllerId", controllerId);
        json.add("name", new JsonObject());
        json.add("layout", layoutJson);
        json.add("key", keyJson);
        var path = FMLPaths.GAMEDIR.get().resolve("mm_generated");
        try {
            if (!Files.exists(path)) {
                Files.createDirectories(path);
            }
            Files.writeString(path.resolve(new Date().getTime() + ".json"), json.toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run() {
        var controllerId = "";
        var startX = Math.min(start.getX(), end.getX());
        var startZ = Math.min(start.getZ(), end.getZ());
        var startY = Math.min(start.getY(), end.getY());

        var endX = Math.max(start.getX(), end.getX());
        var endZ = Math.max(start.getZ(), end.getZ());
        var endY = Math.max(start.getY(), end.getY());
        for (int y = startY; y <= endY; y++) {
            var layer = new ArrayList<String>();
            for (int z = startZ; z <= endZ; z++) {
                StringBuilder row = new StringBuilder();
                for (int x = startX; x <= endX; x++) {
                    BlockPos pos = new BlockPos(x, y, z);
                    var state = level.getBlockState(pos);
                    if (state.getBlock() instanceof IPortBlock port) {
                        var id = setPort(port, pos);
                        row.append(id);
                    } else if (state.getBlock() instanceof ControllerBlock controller) {
                        controllerId = controller.model().id().toString();
                        row.append("C");
                    } else if (!state.isAir()) {
                        var id = setBlock(state.getBlock(), pos);
                        row.append(id);
                    }
                }
                layer.add(row.toString());
            }
            layout.add(layer);
        }
        save(controllerId);
    }
}
