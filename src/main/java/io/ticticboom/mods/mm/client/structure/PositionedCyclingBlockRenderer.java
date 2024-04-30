package io.ticticboom.mods.mm.client.structure;

import net.minecraft.core.BlockPos;

import java.util.List;

public class PositionedCyclingBlockRenderer extends Positioned<TickCycling<GuiBlockRenderer>> {

    public PositionedCyclingBlockRenderer(List<GuiBlockRenderer> parts, BlockPos pos) {
        super(new TickCycling<>(parts), pos);
    }
}
