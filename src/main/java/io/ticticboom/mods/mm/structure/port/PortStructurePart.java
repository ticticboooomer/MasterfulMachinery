package io.ticticboom.mods.mm.structure.port;

import com.google.gson.JsonObject;
import io.ticticboom.mods.mm.ports.base.IOPortStorage;
import io.ticticboom.mods.mm.ports.base.IPortBE;
import io.ticticboom.mods.mm.ports.base.IPortBlock;
import io.ticticboom.mods.mm.structure.IConfiguredStructurePart;
import io.ticticboom.mods.mm.structure.MMStructurePart;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;

import java.util.Optional;

public class PortStructurePart extends MMStructurePart {

    @Override
    public IConfiguredStructurePart parse(JsonObject json) {
        var port = ResourceLocation.tryParse(json.get("port").getAsString());
        var input = json.get("input").getAsBoolean();
        Optional<String> id = Optional.empty();
        if (json.has("id")) {
            id = Optional.of(json.get("id").getAsString());
        }
        return new PortConfiguredStructurePart(port, input, id);
    }

    @Override
    public boolean validatePlacement(Level level, BlockPos expectedPos, IConfiguredStructurePart config) {
        var conf = ((PortConfiguredStructurePart) config);
        var block = level.getBlockState(expectedPos).getBlock();
        if (block instanceof IPortBlock port) {
            if (!port.model().port().equals(conf.port())) {
                return false;
            }
            if (conf.input() != port.model().input()) {
                return false;
            }
            return true;
        }
        return false;
    }

    @Override
    public Optional<IOPortStorage> getPortIfPresent(Level level, BlockPos expectedPos, IConfiguredStructurePart config) {
        var conf = ((PortConfiguredStructurePart) config);
        var block = level.getBlockEntity(expectedPos);
        if (block instanceof IPortBE be) {
            return Optional.of(new IOPortStorage(conf.id().isPresent() ? conf.id().get() : "", be.storage(), conf.input()));
        }
        return Optional.empty();
    }
}
