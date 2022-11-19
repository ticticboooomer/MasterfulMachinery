package io.ticticboom.mods.mm.structure;

import com.google.gson.JsonObject;
import io.ticticboom.mods.mm.ports.base.IOPortStorage;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistryEntry;

import java.util.Optional;

public abstract class MMStructurePart extends ForgeRegistryEntry<MMStructurePart> {
    public abstract IConfiguredStructurePart parse(JsonObject json);
    public abstract boolean validatePlacement(Level level, BlockPos expectedPos, IConfiguredStructurePart config);
    public abstract Optional<IOPortStorage> getPortIfPresent(Level level, BlockPos expectedPos, IConfiguredStructurePart config);
}
