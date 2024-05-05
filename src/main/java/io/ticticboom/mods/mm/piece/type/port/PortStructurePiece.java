package io.ticticboom.mods.mm.piece.type.port;

import com.electronwill.nightconfig.core.AbstractConfig;
import io.ticticboom.mods.mm.Ref;
import io.ticticboom.mods.mm.model.PortModel;
import io.ticticboom.mods.mm.piece.StructurePieceSetupMetadata;
import io.ticticboom.mods.mm.piece.type.StructurePiece;
import io.ticticboom.mods.mm.port.IPortBlock;
import io.ticticboom.mods.mm.port.IPortBlockEntity;
import io.ticticboom.mods.mm.port.MMPortRegistry;
import io.ticticboom.mods.mm.setup.RegistryGroupHolder;
import io.ticticboom.mods.mm.structure.StructureModel;
import io.ticticboom.mods.mm.util.PortUtils;
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

public class PortStructurePiece extends StructurePiece {

    private final ResourceLocation portId;
    private final Optional<Boolean> input;
    private final List<Block> blocks = new ArrayList<>();

    public PortStructurePiece(ResourceLocation portId, Optional<Boolean> input) {

        this.portId = portId;
        this.input = input;
    }

    @Override
    public void validateSetup(StructurePieceSetupMetadata meta) {
        for (RegistryGroupHolder port : MMPortRegistry.PORTS) {
            if (port.getBlock().get() instanceof IPortBlock pb) {
                PortModel model = pb.getModel();
                if (!model.id().equals(PortUtils.id(portId.getPath(), model.input()))) {
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
            if (!pbe.getModel().id().equals(PortUtils.id(portId.getPath(), pbe.getModel().input()))) {
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
        return Component.literal("Port: ").append(Component.literal(portId.toString()).withStyle(ChatFormatting.DARK_AQUA));
    }
}
