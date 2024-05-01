package io.ticticboom.mods.mm.foundation.scanner;

import io.ticticboom.mods.mm.setup.MMRegisters;
import io.ticticboom.mods.mm.util.BlockUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.stream.Stream;

public class StructureScannerBlock extends HorizontalDirectionalBlock implements EntityBlock {

    public StructureScannerBlock() {
        super(BlockUtils.createBlockProperties());
    }

    private static final VoxelShape SHAPE_N = Stream.of(
            Block.box(1, 2, 1, 15, 5, 6),
            Block.box(0, 0, 0, 16, 2, 16),
            Block.box(0.5, 2, 6.5, 15.5, 5, 15.5),
            Block.box(0, 5, 6, 16, 6, 16),
            Block.box(4, 6, 7, 15, 14, 13),
            Block.box(1, 5.5, 7, 3, 6.5, 9),
            Block.box(1.25, 11, 7.25, 2.75, 11.25, 8.75),
            Block.box(1, 6, 11, 3, 12.25, 14),
            Block.box(1, 11.25, 6.75, 3, 12.25, 11.25),
            Block.box(0, 2, 0, 1, 3, 6),
            Block.box(15, 2, 0, 16, 3, 6),
            Block.box(0, 2, 6, 1, 5, 7),
            Block.box(15, 2, 6, 16, 5, 7),
            Block.box(15, 2, 15, 16, 5, 16),
            Block.box(0, 2, 15, 1, 5, 16)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

    private static final VoxelShape SHAPE_E = Stream.of(
            Block.box(10, 2, 1, 15, 5, 15),
            Block.box(0, 0, 0, 16, 2, 16),
            Block.box(0.5, 2, 0.5, 9.5, 5, 15.5),
            Block.box(0, 5, 0, 10, 6, 16),
            Block.box(3, 6, 4, 9, 14, 15),
            Block.box(7, 5.5, 1, 9, 6.5, 3),
            Block.box(7.25, 11, 1.25, 8.75, 11.25, 2.75),
            Block.box(2, 6, 1, 5, 12.25, 3),
            Block.box(4.75, 11.25, 1, 9.25, 12.25, 3),
            Block.box(10, 2, 0, 16, 3, 1),
            Block.box(10, 2, 15, 16, 3, 16),
            Block.box(9, 2, 0, 10, 5, 1),
            Block.box(9, 2, 15, 10, 5, 16),
            Block.box(0, 2, 15, 1, 5, 16),
            Block.box(0, 2, 0, 1, 5, 1)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

    private static final VoxelShape SHAPE_S = Stream.of(
            Block.box(1, 2, 10, 15, 5, 15),
            Block.box(0, 0, 0, 16, 2, 16),
            Block.box(0.5, 2, 0.5, 15.5, 5, 9.5),
            Block.box(0, 5, 0, 16, 6, 10),
            Block.box(1, 6, 3, 12, 14, 9),
            Block.box(13, 5.5, 7, 15, 6.5, 9),
            Block.box(13.25, 11, 7.25, 14.75, 11.25, 8.75),
            Block.box(13, 6, 2, 15, 12.25, 5),
            Block.box(13, 11.25, 4.75, 15, 12.25, 9.25),
            Block.box(15, 2, 10, 16, 3, 16),
            Block.box(0, 2, 10, 1, 3, 16),
            Block.box(15, 2, 9, 16, 5, 10),
            Block.box(0, 2, 9, 1, 5, 10),
            Block.box(0, 2, 0, 1, 5, 1),
            Block.box(15, 2, 0, 16, 5, 1)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

    private static final VoxelShape SHAPE_W = Stream.of(
            Block.box(1, 2, 1, 6, 5, 15),
            Block.box(0, 0, 0, 16, 2, 16),
            Block.box(6.5, 2, 0.5, 15.5, 5, 15.5),
            Block.box(6, 5, 0, 16, 6, 16),
            Block.box(7, 6, 1, 13, 14, 12),
            Block.box(7, 5.5, 13, 9, 6.5, 15),
            Block.box(7.25, 11, 13.25, 8.75, 11.25, 14.75),
            Block.box(11, 6, 13, 14, 12.25, 15),
            Block.box(6.75, 11.25, 13, 11.25, 12.25, 15),
            Block.box(0, 2, 15, 6, 3, 16),
            Block.box(0, 2, 0, 6, 3, 1),
            Block.box(6, 2, 15, 7, 5, 16),
            Block.box(6, 2, 0, 7, 5, 1),
            Block.box(15, 2, 0, 16, 5, 1),
            Block.box(15, 2, 15, 16, 5, 16)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder.add(FACING));
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        return defaultBlockState().setValue(FACING, ctx.getHorizontalDirection());
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter getter, BlockPos pos, CollisionContext collisionContext) {
        switch (state.getValue(FACING)) {
            case NORTH:
                return SHAPE_N;
            case EAST:
                return SHAPE_E;
            case SOUTH:
                return SHAPE_S;
            case WEST:
                return SHAPE_W;
            default:
                throw new IllegalStateException("Invalid State");
        }
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return MMRegisters.SCANNER_BLOCK_ENTITY.get().create(pos, state);
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        return BlockUtils.commonUse(state, level, pos, player, hand, hitResult, StructureScannerBlockEntity.class, null);
    }
}
