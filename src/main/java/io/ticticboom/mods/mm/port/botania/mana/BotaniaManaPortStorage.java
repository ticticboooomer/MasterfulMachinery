package io.ticticboom.mods.mm.port.botania.mana;

import com.google.gson.JsonObject;
import io.ticticboom.mods.mm.port.IPortStorage;
import io.ticticboom.mods.mm.port.IPortStorageModel;
import io.ticticboom.mods.mm.port.common.INotifyChangeFunction;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;

import java.util.UUID;

public class BotaniaManaPortStorage implements IPortStorage {

    private final BotaniaManaPortStorageModel model;
    private final INotifyChangeFunction changed;
    private final UUID uid = UUID.randomUUID();

    @Getter
    private int capacity;
    @Getter
    @Setter
    private int stored = 0;

    public BotaniaManaPortStorage(BotaniaManaPortStorageModel model, INotifyChangeFunction changed) {

        this.model = model;
        this.changed = changed;
        capacity = model.capacity();
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
        return null;
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
        return null;
    }

    public int receiveMana(int mana, boolean sim) {
        var canBeFilled = Math.min(capacity - stored, mana);
        if (!sim) {
            stored += canBeFilled;
        }
        return canBeFilled;
    }

    public int extractMana(int mana, boolean sim) {
        var canBeDrained = Math.min(stored, mana);
        if (!sim) {
            stored -= canBeDrained;
        }
        return canBeDrained;
    }
}
