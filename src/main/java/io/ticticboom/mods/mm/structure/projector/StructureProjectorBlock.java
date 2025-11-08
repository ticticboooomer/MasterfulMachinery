package io.ticticboom.mods.mm.structure.projector;

import com.google.common.collect.ImmutableMap;
import dev.latvian.mods.kubejs.block.custom.ShapedBlockBuilder;
import io.ticticboom.mods.mm.util.BlockUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Function;
import java.util.stream.Stream;

public class StructureProjectorBlock extends HorizontalDirectionalBlock {

    private static VoxelShape normalised(double pMinX, double pMinY, double pMinZ, double pMaxX, double pMaxY, double pMaxZ) {
        return Shapes.create(pMinX, pMinY, pMinZ, pMaxX, pMaxY, pMaxZ);
    }

    private static final VoxelShape SHAPE_N = Stream.of(
            normalised(4, 2, 4, 12, 3, 12),
            normalised(4, 0, 4, 12, 1, 12),
            normalised(6, 2.5, 6, 10, 3.5, 10),
            normalised(5, 1, 5, 11, 2, 11),
            normalised(5, 6, 5, 11, 12, 11),
            normalised(0.75, 2.5, 6.75, 2.25, 4.75, 9.25),
            normalised(13.75, 2.5, 6.75, 15.25, 4.75, 9.25),
            normalised(6, 3.5, 14, 7, 12.75, 15),
            normalised(5.75, 11.25, 13.75, 7.25, 13.5, 15.25),
            normalised(5.75, 2.5, 13.75, 7.25, 4.75, 15.25),
            normalised(8.75, 11.25, 13.75, 10.25, 13.5, 15.25),
            normalised(8.75, 2.5, 13.75, 10.25, 4.75, 15.25),
            normalised(9, 3.5, 14, 10, 12.75, 15),
            normalised(1.25, 6.25, 7.5, 2.25, 7.25, 8.5),
            normalised(13.75, 6.25, 7.5, 14.75, 7.25, 8.5),
            normalised(14, 3.5, 7, 15, 7.75, 9),
            normalised(1, 3.5, 7, 2, 7.75, 9),
            normalised(11, 2, 6, 15, 4, 10),
            normalised(1, 2, 6, 5, 4, 10),
            normalised(6, 2, 11, 10, 4, 15),
            normalised(6, 12, 9, 10, 15, 15)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

    private static final VoxelShape SHAPE_E = Stream.of(
            normalised(4, 2, 4, 12, 3, 12),
            normalised(4, 0, 4, 12, 1, 12),
            normalised(6, 2.5, 6, 10, 3.5, 10),
            normalised(5, 1, 5, 11, 2, 11),
            normalised(5, 6, 5, 11, 12, 11),
            normalised(6.75, 2.5, 0.75, 9.25, 4.75, 2.25),
            normalised(6.75, 2.5, 13.75, 9.25, 4.75, 15.25),
            normalised(1, 3.5, 6, 2, 12.75, 7),
            normalised(0.75, 11.25, 5.75, 2.25, 13.5, 7.25),
            normalised(0.75, 2.5, 5.75, 2.25, 4.75, 7.25),
            normalised(0.75, 11.25, 8.75, 2.25, 13.5, 10.25),
            normalised(0.75, 2.5, 8.75, 2.25, 4.75, 10.25),
            normalised(1, 3.5, 9, 2, 12.75, 10),
            normalised(7.5, 6.25, 1.25, 8.5, 7.25, 2.25),
            normalised(7.5, 6.25, 13.75, 8.5, 7.25, 14.75),
            normalised(7, 3.5, 14, 9, 7.75, 15),
            normalised(7, 3.5, 1, 9, 7.75, 2),
            normalised(6, 2, 11, 10, 4, 15),
            normalised(6, 2, 1, 10, 4, 5),
            normalised(1, 2, 6, 5, 4, 10),
            normalised(1, 12, 6, 7, 15, 10)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

    private static final VoxelShape SHAPE_S = Stream.of(
            normalised(4, 2, 4, 12, 3, 12),
            normalised(4, 0, 4, 12, 1, 12),
            normalised(6, 2.5, 6, 10, 3.5, 10),
            normalised(5, 1, 5, 11, 2, 11),
            normalised(5, 6, 5, 11, 12, 11),
            normalised(13.75, 2.5, 6.75, 15.25, 4.75, 9.25),
            normalised(0.75, 2.5, 6.75, 2.25, 4.75, 9.25),
            normalised(9, 3.5, 1, 10, 12.75, 2),
            normalised(8.75, 11.25, 0.75, 10.25, 13.5, 2.25),
            normalised(8.75, 2.5, 0.75, 10.25, 4.75, 2.25),
            normalised(5.75, 11.25, 0.75, 7.25, 13.5, 2.25),
            normalised(5.75, 2.5, 0.75, 7.25, 4.75, 2.25),
            normalised(6, 3.5, 1, 7, 12.75, 2),
            normalised(13.75, 6.25, 7.5, 14.75, 7.25, 8.5),
            normalised(1.25, 6.25, 7.5, 2.25, 7.25, 8.5),
            normalised(1, 3.5, 7, 2, 7.75, 9),
            normalised(14, 3.5, 7, 15, 7.75, 9),
            normalised(1, 2, 6, 5, 4, 10),
            normalised(11, 2, 6, 15, 4, 10),
            normalised(6, 2, 1, 10, 4, 5),
            normalised(6, 12, 1, 10, 15, 7)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

    private static final VoxelShape SHAPE_W = Stream.of(
            normalised(4, 2, 4, 12, 3, 12),
            normalised(4, 0, 4, 12, 1, 12),
            normalised(6, 2.5, 6, 10, 3.5, 10),
            normalised(5, 1, 5, 11, 2, 11),
            normalised(5, 6, 5, 11, 12, 11),
            normalised(6.75, 2.5, 13.75, 9.25, 4.75, 15.25),
            normalised(6.75, 2.5, 0.75, 9.25, 4.75, 2.25),
            normalised(14, 3.5, 9, 15, 12.75, 10),
            normalised(13.75, 11.25, 8.75, 15.25, 13.5, 10.25),
            normalised(13.75, 2.5, 8.75, 15.25, 4.75, 10.25),
            normalised(13.75, 11.25, 5.75, 15.25, 13.5, 7.25),
            normalised(13.75, 2.5, 5.75, 15.25, 4.75, 7.25),
            normalised(14, 3.5, 6, 15, 12.75, 7),
            normalised(7.5, 6.25, 13.75, 8.5, 7.25, 14.75),
            normalised(7.5, 6.25, 1.25, 8.5, 7.25, 2.25),
            normalised(7, 3.5, 1, 9, 7.75, 2),
            normalised(7, 3.5, 14, 9, 7.75, 15),
            normalised(6, 2, 1, 10, 4, 5),
            normalised(6, 2, 11, 10, 4, 15),
            normalised(11, 2, 6, 15, 4, 10),
            normalised(9, 12, 6, 15, 15, 10)
    ).reduce(Shapes::or).get();

    public StructureProjectorBlock() {
        super(BlockUtils.createBlockProperties());
        registerDefaultState(getStateDefinition().any()
                .setValue(FACING, Direction.NORTH));
    }

    private BlockState getState(Direction facing) {
        return defaultBlockState().setValue(FACING, facing);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext pContext) {
        return defaultBlockState().setValue(FACING, pContext.getHorizontalDirection());
    }

    @Override
    protected @NotNull ImmutableMap<BlockState, VoxelShape> getShapeForEachState(Function<BlockState, VoxelShape> pShapeGetter) {
        return ImmutableMap.of(
                getState(Direction.NORTH), SHAPE_N,
                getState(Direction.EAST), SHAPE_E,
                getState(Direction.SOUTH), SHAPE_S,
                getState(Direction.WEST), SHAPE_W
        );
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return switch (pState.getValue(FACING)) {
            case NORTH -> SHAPE_N;
            case EAST -> SHAPE_E;
            case SOUTH -> SHAPE_S;
            case WEST -> SHAPE_W;
            default -> throw new IllegalStateException("Invalid State");
        };
    }
}
