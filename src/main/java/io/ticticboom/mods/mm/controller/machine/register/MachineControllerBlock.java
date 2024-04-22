package io.ticticboom.mods.mm.controller.machine.register;

import io.ticticboom.mods.mm.Ref;
import io.ticticboom.mods.mm.controller.IControllerBlock;
import io.ticticboom.mods.mm.controller.IControllerPart;
import io.ticticboom.mods.mm.datagen.provider.MMBlockstateProvider;
import io.ticticboom.mods.mm.model.config.ControllerModel;
import io.ticticboom.mods.mm.setup.RegistryGroupHolder;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DirectionalBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.client.model.generators.VariantBlockStateBuilder;
import org.jetbrains.annotations.Nullable;

public class MachineControllerBlock extends HorizontalDirectionalBlock implements IControllerPart, IControllerBlock {
    private final ControllerModel model;
    private final RegistryGroupHolder groupHolder;

    public MachineControllerBlock(ControllerModel model, RegistryGroupHolder groupHolder) {
        super(Properties.of().requiresCorrectToolForDrops().destroyTime(5f).explosionResistance(6f).sound(SoundType.METAL));
        this.model = model;
        this.groupHolder = groupHolder;
        registerDefaultState(this.getStateDefinition().any()
                .setValue(FACING, Direction.NORTH));
    }

    @Override
    public ControllerModel getModel() {
        return model;
    }

    @Override
    public void generateModel(MMBlockstateProvider provider) {
        var mdl = provider.dynamicBlockNorthOverlay(groupHolder.getBlock().getId(), Ref.Textures.BASE_BLOCK, Ref.Textures.CONTROLLER_OVERLAY);
        provider.directionalState(groupHolder.getBlock().get(), mdl);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }


    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        return defaultBlockState().setValue(FACING, ctx.getHorizontalDirection());
    }
}
