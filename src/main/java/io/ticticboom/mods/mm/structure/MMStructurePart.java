package io.ticticboom.mods.mm.structure;

import com.google.gson.JsonObject;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.registries.ForgeRegistryEntry;

public abstract class MMStructurePart extends ForgeRegistryEntry<MMStructurePart> {
    public abstract IConfiguredStructurePart parse(JsonObject json);
    public abstract boolean validatePlacement(Level level, BlockPos expectedPos, IConfiguredStructurePart config);
}
