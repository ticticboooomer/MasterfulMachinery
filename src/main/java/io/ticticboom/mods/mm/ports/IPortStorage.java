package io.ticticboom.mods.mm.ports;

import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;

public interface IPortStorage {
    <T> LazyOptional<T> getCapability();
    <T> boolean hasCapability(Capability<T> capability);
    CompoundTag save(CompoundTag tag);
    void load(CompoundTag tag);
}
