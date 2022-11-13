package io.ticticboom.mods.mm.structure.tag;

import com.google.gson.JsonObject;
import io.ticticboom.mods.mm.Ref;
import io.ticticboom.mods.mm.structure.IConfiguredStructurePart;
import io.ticticboom.mods.mm.structure.MMStructurePart;
import io.ticticboom.mods.mm.structure.block.BlockConfiguredStructurePart;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class BlockTagStructurePart extends MMStructurePart {
    @Override
    public IConfiguredStructurePart parse(JsonObject json) {
        var tag = ResourceLocation.tryParse(json.get("tag").getAsString());
        return new BlockTagConfiguredStructurePart(tag, Ref.StructureParts.TAG);
    }

    @Override
    public boolean validatePlacement(Level level, BlockPos expectedPos, IConfiguredStructurePart config) {
        var currentConfig = ((BlockTagConfiguredStructurePart) config);
        var state = level.getBlockState(expectedPos);
        return state.getTags().anyMatch(x -> x.toString().equals(currentConfig.tag().toString()));
    }
}
