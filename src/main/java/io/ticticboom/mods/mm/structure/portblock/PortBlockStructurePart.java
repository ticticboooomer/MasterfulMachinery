package io.ticticboom.mods.mm.structure.portblock;

import com.google.gson.JsonObject;
import io.ticticboom.mods.mm.ports.base.IOPortStorage;
import io.ticticboom.mods.mm.ports.base.IPortBE;
import io.ticticboom.mods.mm.structure.IConfiguredStructurePart;
import io.ticticboom.mods.mm.structure.MMStructurePart;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Optional;

public class PortBlockStructurePart extends MMStructurePart {
    @Override
    public IConfiguredStructurePart parse(JsonObject json) {
        var id = ResourceLocation.tryParse(json.get("portId").getAsString());

        Optional<String> name = Optional.empty();
        if (json.has("id")) {
            name = Optional.of(json.get("id").getAsString());
        }
        return new PortBlockConfiguredStructurePart(id, name);
    }

    @Override
    public boolean validatePlacement(Level level, BlockPos expectedPos, IConfiguredStructurePart config) {
        var state = level.getBlockState(expectedPos);
        return ForgeRegistries.BLOCKS.getKey(state.getBlock()).toString().equals(((PortBlockConfiguredStructurePart) config).portId().toString());
    }

    @Override
    public Optional<IOPortStorage> getPortIfPresent(Level level, BlockPos expectedPos, IConfiguredStructurePart config) {
        var conf = ((PortBlockConfiguredStructurePart) config);
        var block = level.getBlockEntity(expectedPos);
        if (block instanceof IPortBE be) {
            return Optional.of(new IOPortStorage(conf.id().isPresent() ? conf.id().get() : "", be.storage(), be.model().input()));
        }
        return Optional.empty();
    }
}
