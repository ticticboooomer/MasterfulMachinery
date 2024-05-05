package io.ticticboom.mods.mm.piece.type.porttype;

import io.ticticboom.mods.mm.model.PortModel;
import io.ticticboom.mods.mm.piece.StructurePieceSetupMetadata;
import io.ticticboom.mods.mm.piece.type.StructurePiece;
import io.ticticboom.mods.mm.port.IPortBlock;
import io.ticticboom.mods.mm.port.IPortBlockEntity;
import io.ticticboom.mods.mm.port.MMPortRegistry;
import io.ticticboom.mods.mm.setup.RegistryGroupHolder;
import io.ticticboom.mods.mm.structure.StructureModel;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

public class PortTypeStructurePiece extends StructurePiece {

    private final ResourceLocation portTypeId;
    private final Optional<Boolean> input;

    private final List<Block> blocks = new ArrayList<>();

    public PortTypeStructurePiece(ResourceLocation portTypeId, Optional<Boolean> input) {
        this.portTypeId = portTypeId;
        this.input = input;
    }

    @Override
    public void validateSetup(StructurePieceSetupMetadata meta) {
        for (RegistryGroupHolder port : MMPortRegistry.PORTS) {
            if (port.getBlock().get() instanceof IPortBlock pb) {
                PortModel model = pb.getModel();
                if (!model.type().equals(portTypeId)) {
                    continue;
                }
                if (input.isPresent() && !input.get().equals(model.input())) {
                    continue;
                }
                blocks.add(port.getBlock().get());
            }
        }
    }

    @Override
    public boolean formed(Level level, BlockPos pos, StructureModel model) {
        var be = level.getBlockEntity(pos);
        if (be instanceof IPortBlockEntity pbe) {
            if (!pbe.getModel().type().equals(portTypeId)) {
                return false;
            }
            if (input.isPresent() && !input.get().equals(pbe.getModel().input())) {
                return false;
            }
            return true;
        }
        return false;
    }

    @Override
    public Supplier<List<Block>> createBlocksSupplier() {
        return () -> blocks;
    }

    @Override
    public Component createDisplayComponent() {
        return Component.literal("Port Type: ").append(Component.literal(portTypeId.toString()).withStyle(ChatFormatting.DARK_AQUA));
    }
}
