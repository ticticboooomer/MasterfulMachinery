package io.ticticboom.mods.mm.client.structure;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class GuiStructureLayoutPiece {
    private final Supplier<List<Block>> blocks;

    public GuiStructureLayoutPiece(Supplier<List<Block>> blocks) {
        this.blocks = blocks;
    }

    public List<GuiBlockRenderer> createBlockRenderer(BlockPos pos) {
        return blocks.get().stream().map(b -> {
            var res = new GuiBlockRenderer(b);
            res.setupAt(pos);
            return res;
        }).toList();
    }

    public List<Block> getBlocks() {
        return blocks.get();
    }
}
