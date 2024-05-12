package io.ticticboom.mods.mm.port.item;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.mojang.serialization.JsonOps;
import io.ticticboom.mods.mm.cap.MMCapabilities;
import io.ticticboom.mods.mm.model.PortModel;
import io.ticticboom.mods.mm.port.IPortStorage;
import io.ticticboom.mods.mm.port.IPortStorageModel;
import io.ticticboom.mods.mm.port.common.INotifyChangeFunction;
import lombok.Getter;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;

import java.util.UUID;

public class ItemPortStorage implements IPortStorage {

    @Getter
    private final ItemPortHandler handler;
    private final LazyOptional<ItemPortHandler> handlerLazyOptional;
    private final ItemPortStorageModel model;
    private final UUID uid = UUID.randomUUID();

    public ItemPortStorage(ItemPortStorageModel model, INotifyChangeFunction changed) {
        this.model = model;
        handler = new ItemPortHandler(model.rows() * model.columns(), changed);
        handlerLazyOptional = LazyOptional.of(() -> handler);
    }

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> capability) {
        if (hasCapability(capability)) {
            return handlerLazyOptional.cast();
        }
        return LazyOptional.empty();
    }

    @Override
    public <T> boolean hasCapability(Capability<T> capability) {
        return capability == MMCapabilities.ITEM;
    }

    @Override
    public CompoundTag save(CompoundTag tag) {
        Tag compoundTag = handler.serializeStacks();
        tag.put("handler", compoundTag);
        return tag;
    }

    @Override
    public void load(CompoundTag tag) {
        Tag compoundTag = tag.get("handler");
        handler.deserializeStacks(compoundTag);
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
        JsonObject json = new JsonObject();
        json.addProperty("uid", uid.toString());
        json.addProperty("rows", model.rows());
        json.addProperty("columns", model.columns());
        var stacksJson = new JsonArray();
        for (ItemStack stack : handler.getStacks()) {
            var res = JsonOps.INSTANCE.withEncoder(ItemStack.CODEC).apply(stack);
            if (res.result().isPresent()) {
                stacksJson.add(res.result().get());
            } else {
                stacksJson.add("Error Serializing Item Stack");
            }
        }
        return json;
    }

    @Override
    public void setupContainer(AbstractContainerMenu container, Inventory inv, PortModel portModel) {
        var columns = model.columns();
        var rows = model.rows();

        int offsetX = ((162 - (columns * 18)) / 2) + 8;
        int offsetY = ((108 - (rows * 18)) / 2) + 8;

        var portInv = new ItemPortContainer(this.handler);

        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < columns; x++) {
                container.addSlot(new Slot(portInv, (y * columns) + x, x * 18 + offsetX, y * 18 + offsetY));
            }
        }

        IPortStorage.super.setupContainer(container, inv, portModel);
    }

    public int canExtract(Item item, int count) {
        return handlerExtract(item, count, true);
    }

    public int extract(Item item, int count) {
        return handlerExtract(item, count, false);
    }

    private int handlerExtract(Item item, int count, boolean simulate) {
        int remaining = count;
        for (int slot = 0; slot < handler.getSlots(); slot++) {
            if (!handler.getStackInSlot(slot).is(item)) {
                continue;
            }
            var extracted = handler.extractItem(slot, remaining, simulate);
            remaining -= extracted.getCount();
        }
        return remaining;
    }

    public int canInsert(Item item, int count) {
        return handlerInsert(item, count, true);
    }

    public int insert(Item item, int count) {
        return handlerInsert(item, count, false);
    }

    private int handlerInsert(Item item, int count, boolean simulate) {
        int remainingToInsert = count;
        for (int slot = 0; slot < handler.getSlots(); slot++) {
            var toInsert = Math.min(handler.getSlotLimit(slot), remainingToInsert);
            var remains = handler.insertItem(slot, new ItemStack(item, toInsert), simulate);
            remainingToInsert -= (toInsert - remains.getCount());
        }
        return remainingToInsert;
    }
}
