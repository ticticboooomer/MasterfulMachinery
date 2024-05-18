package io.ticticboom.mods.mm.port.kinetic;

import com.google.gson.JsonObject;
import io.ticticboom.mods.mm.port.IPortStorage;
import io.ticticboom.mods.mm.port.IPortStorageModel;
import io.ticticboom.mods.mm.port.common.INotifyChangeFunction;
import lombok.Getter;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;

import java.util.UUID;

public class CreateKineticPortStorage implements IPortStorage {

    private final CreateKineticPortStorageModel model;
    private final INotifyChangeFunction changed;
    private final UUID uid = UUID.randomUUID();

    @Getter
    private float speed = 0;

    public CreateKineticPortStorage(CreateKineticPortStorageModel model, INotifyChangeFunction changed) {
        this.model = model;
        this.changed = changed;
    }

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> capability) {
        return LazyOptional.empty();
    }

    @Override
    public <T> boolean hasCapability(Capability<T> capability) {
        return false;
    }

    @Override
    public CompoundTag save(CompoundTag tag) {
        return tag;
    }

    @Override
    public void load(CompoundTag tag) {

    }

    @Override
    public IPortStorageModel getStorageModel() {
        return model;
    }

    @Override
    public UUID getStorageUid() {
        return uid;
    }

    @Override
    public JsonObject debugDump() {
        var json = new JsonObject();
        json.addProperty("uid", uid.toString());
        // TODO add kinetic state to json
        return json;
    }

    public void updateSpeed(float speed) {
        boolean upd = this.speed != speed;
        this.speed = speed;
        if (upd) {
            this.changed.call();
        }
    }
}
