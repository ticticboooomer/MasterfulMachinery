package io.ticticboom.mods.mm.port.pneumaticcraft.air.register;

import io.ticticboom.mods.mm.Ref;
import io.ticticboom.mods.mm.datagen.provider.MMBlockstateProvider;
import io.ticticboom.mods.mm.model.PortModel;
import io.ticticboom.mods.mm.port.IPortBlock;
import io.ticticboom.mods.mm.setup.RegistryGroupHolder;
import io.ticticboom.mods.mm.util.BlockUtils;
import io.ticticboom.mods.mm.util.PortUtils;
import lombok.Getter;
import me.desht.pneumaticcraft.common.block.AbstractPneumaticCraftBlock;
import me.desht.pneumaticcraft.common.block.PneumaticCraftEntityBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

public class PneumaticAirPortBlock extends AbstractPneumaticCraftBlock implements IPortBlock, EntityBlock, PneumaticCraftEntityBlock {

@Getter
private final PortModel model;
private final RegistryGroupHolder groupHolder;
private final boolean isInput;

    public PneumaticAirPortBlock(PortModel model, RegistryGroupHolder groupHolder, boolean isInput) {
        super(BlockUtils.createBlockProperties());

        this.model = model;
        this.groupHolder = groupHolder;
        this.isInput = isInput;
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(new Property[]{NORTH, SOUTH, WEST, EAST});
    }
    @Override
    public void generateModel(MMBlockstateProvider provider) {
        PortUtils.commonGenerateModel(provider, groupHolder, isInput, Ref.Textures.INPUT_PNCR_AIR_PORT_OVERLAY, Ref.Textures.OUTPUT_PNCR_AIR_PORT_OVERLAY);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return groupHolder.getBe().get().create(pos, state);
    }


}
