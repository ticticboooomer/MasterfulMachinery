package io.ticticboom.mods.mm.debug.tool;

import io.ticticboom.mods.mm.config.MMConfig;
import io.ticticboom.mods.mm.controller.machine.register.MachineControllerBlock;
import io.ticticboom.mods.mm.debug.output.CollectedDebugData;
import io.ticticboom.mods.mm.debug.output.DebugOutputManager;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;

public class DebugToolItem extends Item {
    public DebugToolItem() {
        super(new Properties().stacksTo(1));
    }

    @Override
    public InteractionResult useOn(UseOnContext ctx) {
        if (!MMConfig.DEBUG_TOOL || ctx.getLevel().isClientSide()) {
            return InteractionResult.PASS;
        }
        if (!ctx.getItemInHand().getItem().equals(this)) {
            return InteractionResult.PASS;
        }
        var block = ctx.getLevel().getBlockState(ctx.getClickedPos());

        if (!(block.getBlock() instanceof MachineControllerBlock)) {
            return InteractionResult.PASS;
        }
        CollectedDebugData collect = DebugOutputManager.collect(ctx.getLevel(), ctx.getClickedPos());
        DebugOutputManager.output(collect);
        return InteractionResult.SUCCESS;
    }
}
