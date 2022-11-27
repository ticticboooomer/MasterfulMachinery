package io.ticticboom.mods.mm.item;

import io.ticticboom.mods.mm.ModRoot;
import io.ticticboom.mods.mm.capability.MMCapabilities;
import io.ticticboom.mods.mm.capability.wand.ISelectionWand;
import io.ticticboom.mods.mm.thread.StructureGenThread;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fml.util.thread.EffectiveSide;

public class StructureSelectorWand extends Item {
    public StructureSelectorWand() {
        super(new Properties().tab(ModRoot.MM_GROUP));
    }

    @Override
    public InteractionResult useOn(UseOnContext ctx) {
        LazyOptional<ISelectionWand> capability = ctx.getItemInHand().getCapability(MMCapabilities.WAND);
        if (!capability.isPresent()) {
            return InteractionResult.PASS;
        }

        ISelectionWand wand = capability.resolve().get();
        if (wand.first() != null && wand.last() == null) {
            wand.setLast(ctx.getClickedPos());
            if (ctx.getPlayer() != null) {
                ctx.getPlayer().sendMessage(new TextComponent("Selected last pos, saving to file"), ctx.getPlayer().getUUID());
                new StructureGenThread(wand.first(), wand.last(), ctx.getLevel()).start();
            }
        } else {
            wand.setFirst(ctx.getClickedPos());
            wand.setLast(null);
            if (ctx.getPlayer() != null) {
                ctx.getPlayer().sendMessage(new TextComponent("Selected first pos, use again to select last and save."), ctx.getPlayer().getUUID());
            }
        }
        return InteractionResult.SUCCESS;
    }

    private void generateStructure(BlockPos start, BlockPos end) {

    }
}
