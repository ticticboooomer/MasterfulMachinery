package io.ticticboom.mods.mm.client.structure;

import io.ticticboom.mods.mm.structure.layout.PositionedLayoutPiece;
import io.ticticboom.mods.mm.structure.layout.StructureLayout;
import net.minecraft.core.BlockPos;

import java.util.ArrayList;
import java.util.List;

public class GuiStructureLayout {
    private final StructureLayout layout;
    private final BlockPos controllerPos;

    public GuiStructureLayout(StructureLayout layout) {

        this.layout = layout;
        controllerPos = new BlockPos(0, 0, 0);
    }

    public List<PositionedCyclingBlockRenderer> createBlockRenderers() {
        var result = new ArrayList<PositionedCyclingBlockRenderer>();
        for (PositionedLayoutPiece piece : layout.getPositionedPieces()) {
            GuiStructureLayoutPiece guiPiece = piece.piece().getGuiPiece();
            result.add(new PositionedCyclingBlockRenderer(guiPiece.createBlockRenderer(piece.pos()), piece.pos()));
        }
        return result;
    }
}
