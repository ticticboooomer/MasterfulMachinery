package io.ticticboom.mods.mm.client.structure;

import io.ticticboom.mods.mm.piece.modifier.StructurePieceModifier;
import lombok.Getter;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.Block;

import java.util.List;
import java.util.function.Supplier;

public class GuiStructureLayoutPiece {
    private final Supplier<List<Block>> blocks;
    @Getter
    private final Component display;
    private final List<StructurePieceModifier> modifiers;

    public GuiStructureLayoutPiece(Supplier<List<Block>> blocks, Component display, List<StructurePieceModifier> modifiers) {
        this.blocks = blocks;
        this.display = display;
        this.modifiers = modifiers;
    }

    public List<GuiBlockRenderer> createBlockRenderer(BlockPos pos) {
        return blocks.get().stream().map(b -> {
            var res = new GuiBlockRenderer(b, modifiers);
            res.setupAt(pos);
            return res;
        }).toList();
    }

    public List<Block> getBlocks() {
        return blocks.get();
    }
}
