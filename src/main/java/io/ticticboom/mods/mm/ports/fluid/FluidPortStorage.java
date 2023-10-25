package io.ticticboom.mods.mm.ports.fluid;

import io.ticticboom.mods.mm.ports.base.PortStorage;
import io.ticticboom.mods.mm.capability.MMCapabilities;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.registries.ForgeRegistries;

public class FluidPortStorage extends PortStorage {

    public FluidConfiguredPort config;
    public final MMFluidTank handler;
    public final LazyOptional<MMFluidTank> handlerLO;

    @Override
    public InteractionResult playerInteractWithItem(Player player, Level level, BlockPos pos, InteractionHand hand) {
        if (FluidUtil.interactWithFluidHandler(player, hand, handler)) {
            return InteractionResult.CONSUME;
        }
        return InteractionResult.PASS;
    }

    public FluidPortStorage(FluidConfiguredPort config) {
        this.config = config;
        handler = new MMFluidTank(config.capacity());
        handlerLO = LazyOptional.of(() -> handler);
    }

    @Override
    public void read(CompoundTag tag) {
        if (tag.contains("Amount") && tag.contains("Fluid")) {
            var amount = tag.getInt("Amount");
            String fluidId = tag.getString("Fluid");
            var fluid = ForgeRegistries.FLUIDS.getValue(ResourceLocation.tryParse(fluidId));
            handler.setStack(new FluidStack(fluid, amount));
        }
    }

    @Override
    public CompoundTag write() {
        var result = new CompoundTag();
        result.putString("Fluid", ForgeRegistries.FLUIDS.getKey(handler.stack().getFluid()).toString());
        result.putInt("Amount", handler.stack().getAmount());
        return result;
    }

    @Override
    public void onDestroy(Level level, BlockPos pos) {

    }

    @Override
    public PortStorage deepClone() {
        var result = new FluidPortStorage(config);
        result.handler.setStack(handler.stack().copy());
        return result;
    }

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> cap) {
        if (cap == MMCapabilities.FLUIDS) {
            return handlerLO.cast();
        }
        return LazyOptional.empty();
    }
}
