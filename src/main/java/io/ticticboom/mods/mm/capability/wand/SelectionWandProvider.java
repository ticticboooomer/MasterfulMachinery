package io.ticticboom.mods.mm.capability.wand;

import io.ticticboom.mods.mm.capability.MMCapabilities;
import net.minecraft.core.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SelectionWandProvider implements ICapabilityProvider {

    private final ISelectionWand wand = new SelectionWand();
    private final LazyOptional<ISelectionWand> handlerLO=  LazyOptional.of(() -> wand);

    @NotNull
    @Override
    public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        return getCapability(cap);
    }

    @NotNull
    @Override
    public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap) {
        if (cap == MMCapabilities.WAND) {
            return handlerLO.cast();
        }
        return LazyOptional.empty();
    }
}
