package io.ticticboom.mods.mm.ports.js;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.ticticboom.mods.mm.ports.base.PortStorage;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;

public class PortStorageJS extends PortStorage {

    private final ConfiguredPortJS config;
    public JsonObject data = new JsonObject();
    private DataCallback readC;
    private DataCallback write;
    private DestroyCallback destroy;

    public PortStorageJS(DataCallback readC, DataCallback write, DestroyCallback destroy, ConfiguredPortJS config) {
        this.readC = readC;
        this.write = write;
        this.destroy = destroy;
        this.config = config;
    }

    public PortStorageJS(DataCallback readC, DataCallback write, DestroyCallback destroy, ConfiguredPortJS config, JsonObject data) {
        this.readC = readC;
        this.write = write;
        this.destroy = destroy;
        this.config = config;
        this.data = data;

    }

    @Override
    public void read(CompoundTag tag) {
        readC.run(tag, data);
    }

    @Override
    public CompoundTag write() {
        var tag = new CompoundTag();
        write.run(tag, data);
        return tag;
    }

    @Override
    public void onDestroy(Level level, BlockPos pos) {
        destroy.run(level, pos, data);
    }

    @Override
    public PortStorage deepClone() {
        return new PortStorageJS(readC, write, destroy, config, JsonParser.parseString(data.toString()).getAsJsonObject());
    }

    @FunctionalInterface
    public interface DataCallback {
        void run(CompoundTag tag, JsonObject data);
    }

    public interface DestroyCallback {
        void run(Level level, BlockPos pos, JsonObject data);
    }
}
