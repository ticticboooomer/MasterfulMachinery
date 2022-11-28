package io.ticticboom.mods.mm.structure.tag;

import com.google.gson.JsonObject;
import io.ticticboom.mods.mm.Ref;
import io.ticticboom.mods.mm.ports.base.IOPortStorage;
import io.ticticboom.mods.mm.structure.IConfiguredStructurePart;
import io.ticticboom.mods.mm.structure.MMStructurePart;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagManager;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.Tags;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Optional;

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
        return state.is(BlockTags.create(currentConfig.tag()));
    }
}
