package io.ticticboom.mods.mm.port;

import io.ticticboom.mods.mm.model.PortModel;
import io.ticticboom.mods.mm.util.BlockUtils;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;

public interface IPortStorage {
    <T> LazyOptional<T> getCapability();

    <T> boolean hasCapability(Capability<T> capability);

    CompoundTag save(CompoundTag tag);

    void load(CompoundTag tag);

    IPortStorageModel getStorageModel();

    default void setupContainer(AbstractContainerMenu container, Inventory inv, PortModel model) {
        BlockUtils.setupPlayerInventory(container, inv);
    }
}
