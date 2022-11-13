package io.ticticboom.mods.mm.ports.item;

import com.google.gson.JsonObject;
import io.ticticboom.mods.mm.Ref;
import io.ticticboom.mods.mm.ports.base.IConfiguredPort;
import io.ticticboom.mods.mm.ports.base.MMPortTypeEntry;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.BlockState;

public class ItemPortTypeEntry extends MMPortTypeEntry {
    @Override
    public ResourceLocation id() {
        return Ref.res("item");
    }

    @Override
    public IConfiguredPort parse(JsonObject element) {
        var rows = element.get("slotRows").getAsInt();
        var cols = element.get("slotCols").getAsInt();
        return new ItemConfiguredPort(rows, cols);
    }

    @Override
    public ResourceLocation overlay(boolean input) {
        return input ? Ref.res("block/base_ports/item_input_cutout") : Ref.res("block/base_ports/item_output_cutout");
    }
}
