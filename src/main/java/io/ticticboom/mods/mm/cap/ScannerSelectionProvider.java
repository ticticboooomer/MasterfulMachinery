package io.ticticboom.mods.mm.cap;

import net.minecraft.core.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ScannerSelectionProvider implements ICapabilityProvider {

    public IScannerSelection handler = new ScannerSelection();
    public LazyOptional<IScannerSelection> handleLazyOptional = LazyOptional.of(() -> handler);

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == MMCapabilities.SCANNER_SELECTION) {
            return handleLazyOptional.cast();
        }
        return LazyOptional.empty();
    }
}
