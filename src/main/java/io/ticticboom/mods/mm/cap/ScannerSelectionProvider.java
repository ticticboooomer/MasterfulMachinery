package io.ticticboom.mods.mm.cap;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.DataResult;
import net.minecraft.core.Direction;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ScannerSelectionProvider implements ICapabilityProvider, ICapabilitySerializable<Tag> {

    public ScannerSelection handler = new ScannerSelection();
    public LazyOptional<IScannerSelection> handleLazyOptional = LazyOptional.of(() -> handler);

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == MMCapabilities.SCANNER_SELECTION) {
            return handleLazyOptional.cast();
        }
        return LazyOptional.empty();
    }

    @Override
    public Tag serializeNBT() {
        DataResult<Tag> apply = NbtOps.INSTANCE.withEncoder(ScannerSelection.CODEC).apply(handler);
        var tag = apply.getOrThrow(false, RuntimeException::new);
        return tag;
    }

    @Override
    public void deserializeNBT(Tag nbt) {
        var apply = NbtOps.INSTANCE.withDecoder(ScannerSelection.CODEC).apply(nbt);
        var val = apply.getOrThrow(false, RuntimeException::new);
        handler.from(val.getFirst());
    }
}
