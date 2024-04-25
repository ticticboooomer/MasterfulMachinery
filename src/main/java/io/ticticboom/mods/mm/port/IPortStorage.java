package io.ticticboom.mods.mm.port;

import io.ticticboom.mods.mm.model.PortModel;
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
        int playerOffsetX = 8;
        int playerOffsetY = 141;
        for (int j = 0; j < 3; j++) {
            for (int i = 0; i < 9; i++) {
                container.addSlot(new Slot(inv, 9 + (j * 9 + i), i * 18 + playerOffsetX, j * 18 + playerOffsetY));
            }
        }

        for (int i = 0; i < 9; i++) {
            container.addSlot(new Slot(inv, i, 8 + (i * 18), 199));
        }
    }
}
