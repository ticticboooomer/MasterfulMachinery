package io.ticticboom.mods.mm.structure;

import com.google.gson.JsonObject;
import io.ticticboom.mods.mm.ports.base.IOPortStorage;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

import java.util.Optional;

public abstract class MMStructurePart {
    public abstract IConfiguredStructurePart parse(JsonObject json);
    public abstract boolean validatePlacement(Level level, BlockPos expectedPos, IConfiguredStructurePart config);
    public Optional<IOPortStorage> getPortIfPresent(Level level, BlockPos expectedPos, IConfiguredStructurePart config) {
        return Optional.empty();
    }
}
