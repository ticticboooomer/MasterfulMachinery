package io.ticticboom.mods.mm.structure.block;

import com.google.gson.JsonObject;
import io.ticticboom.mods.mm.Ref;
import io.ticticboom.mods.mm.ports.base.IOPortStorage;
import io.ticticboom.mods.mm.structure.MMStructurePart;
import io.ticticboom.mods.mm.structure.IConfiguredStructurePart;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;

import java.util.Optional;

public class BlockStructurePart extends MMStructurePart {
    @Override
    public IConfiguredStructurePart parse(JsonObject json) {
        var block = ResourceLocation.tryParse(json.get("block").getAsString());
        return new BlockConfiguredStructurePart(block, Ref.StructureParts.BLOCK);
    }

    @Override
    public boolean validatePlacement(Level level, BlockPos expectedPos, IConfiguredStructurePart config) {
        var currentConfig = (BlockConfiguredStructurePart)config;
        var state = level.getBlockState(expectedPos);
        return state.getBlock().getRegistryName().equals(currentConfig.block());
    }

    @Override
    public Optional<IOPortStorage> getPortIfPresent(Level level, BlockPos expectedPos, IConfiguredStructurePart config) {
        return Optional.empty();
    }
}
