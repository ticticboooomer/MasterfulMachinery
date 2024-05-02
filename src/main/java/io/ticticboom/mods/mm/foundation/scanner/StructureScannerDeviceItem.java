package io.ticticboom.mods.mm.foundation.scanner;

import io.ticticboom.mods.mm.cap.IScannerSelection;
import io.ticticboom.mods.mm.cap.MMCapabilities;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;

public class StructureScannerDeviceItem extends Item {
    public StructureScannerDeviceItem() {
        super(new Properties());
    }

    @Override
    public InteractionResult useOn(UseOnContext ctx) {
        var pos = ctx.getClickedPos();
        ItemStack tool = ctx.getItemInHand();
        var selection = tool.getCapability(MMCapabilities.SCANNER_SELECTION).orElseThrow(() -> new RuntimeException("No Selection Capability found!"));
        if (selection.getStartSelection() == null) {
            selection.setStartSelection(pos);
            ctx.getPlayer().sendSystemMessage(Component.literal("== ").withStyle(ChatFormatting.DARK_GREEN).append(Component.literal(String.format("First selection set as (%s, %s, %s)", pos.getX(), pos.getY(), pos.getZ()))));
        } else if (selection.getEndSelection() == null) {
            selection.setEndSelection(pos);
            ctx.getPlayer().sendSystemMessage(Component.literal("== ").withStyle(ChatFormatting.DARK_GREEN).append(Component.literal(String.format("Second selection set as (%s, %s, %s)", pos.getX(), pos.getY(), pos.getZ()))));
        } else {
            selection.reset();
            ctx.getPlayer().sendSystemMessage(Component.literal("== ").withStyle(ChatFormatting.DARK_PURPLE).append(Component.literal("Selection Reset")));
        }
        return InteractionResult.SUCCESS;
    }
}
