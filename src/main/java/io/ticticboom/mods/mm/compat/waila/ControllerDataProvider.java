package io.ticticboom.mods.mm.compat.waila;

import io.ticticboom.mods.mm.Ref;
import io.ticticboom.mods.mm.controller.machine.register.MachineControllerBlockEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.IBlockComponentProvider;
import snownee.jade.api.IServerDataProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;

public class ControllerDataProvider implements IServerDataProvider<BlockEntity>, IBlockComponentProvider {
    public static final ResourceLocation UID = Ref.id("controller_progress");
    public static final String TICK_KEY = "TickPercentage";

    public static final ControllerDataProvider INSTANCE = new ControllerDataProvider();


    @Override
    public ResourceLocation getUid() {
        return UID;
    }

    @Override
    public void appendTooltip(ITooltip tooltip, BlockAccessor blockAccessor, IPluginConfig iPluginConfig) {
        CompoundTag data = blockAccessor.getServerData();
        if (data.contains(TICK_KEY)) {
            var progress = data.getString(TICK_KEY);
            tooltip.add(Component.literal("Progress: " + progress));
        }
    }

    @Override
    public void appendServerData(CompoundTag data, ServerPlayer serverPlayer, Level level, BlockEntity be, boolean b) {
        if (be instanceof MachineControllerBlockEntity cbe) {
            if (cbe.getRecipeState() != null) {
                var tickPercentage = String.format("%.2f", cbe.getRecipeState().getTickPercentage()) + "%";
                data.putString(TICK_KEY, tickPercentage);
            } else {
                data.putString(TICK_KEY, "Idle");
            }
        }
    }
}
