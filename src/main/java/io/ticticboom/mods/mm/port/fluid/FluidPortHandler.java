package io.ticticboom.mods.mm.port.fluid;

import com.mojang.serialization.Codec;
import io.ticticboom.mods.mm.Ref;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.*;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class FluidPortHandler implements IFluidHandler, INBTSerializable<CompoundTag> {

    private final int tanks;
    private final int capacity;

    private final NonNullList<FluidStack> stacks;

    public static final Codec<List<FluidStack>> STACKS_CODEC = Codec.list(FluidStack.CODEC);

    public FluidPortHandler(int tanks, int capacity) {
        this.tanks = tanks;
        this.capacity = capacity;
        stacks = NonNullList.withSize(tanks, FluidStack.EMPTY);
    }

    @Override
    public int getTanks() {
        return tanks;
    }

    @Override
    public @NotNull FluidStack getFluidInTank(int i) {
        return stacks.get(i);
    }

    @Override
    public int getTankCapacity(int i) {
        return capacity;
    }

    @Override
    public boolean isFluidValid(int i, @NotNull FluidStack fluidStack) {
        FluidStack slotStack = stacks.get(i);
        return slotStack.isEmpty() || slotStack.isFluidEqual(fluidStack);
    }

    @Override
    public int fill(FluidStack stack, FluidAction action) {
        if (stack.isEmpty()) {
            return 0;
        }

        int filled = 0;
        for (int slot = 0; slot < stacks.size(); slot++) {
            filled += innerFill(slot, stack.getFluid(), stack.getAmount() - filled, action.simulate());
        }
        return filled;
    }

    private int innerFill(int slot, Fluid fluid, int amount, boolean simulate) {
        FluidStack slotStack = stacks.get(slot);
        int storedAmount = slotStack.getAmount();
        if (!isFluidValid(slot, new FluidStack(fluid, amount))) {
            return 0;
        }

        var canBeFilled = Math.min(capacity - storedAmount, amount);

        if (!simulate) {
            stacks.get(slot).setAmount(storedAmount + canBeFilled);
        }
        return canBeFilled;
    }

    @Override
    public @NotNull FluidStack drain(FluidStack stack, FluidAction action) {
        if (stack.isEmpty()) {
            return FluidStack.EMPTY;
        }
        int drained = 0;
        for (int slot = 0; slot < stacks.size(); slot++) {
            var innerDrained = innerDrain(slot, stack.getFluid(), stack.getAmount(), action.simulate());
            drained += innerDrained.getAmount();
        }
        return new FluidStack(stack.getFluid(), drained);
    }

    private FluidStack innerDrain(int slot, Fluid fluid, int amount, boolean simulate) {
        FluidStack slotStack = stacks.get(slot);
        int storedAmount = slotStack.getAmount();
        if (!isFluidValid(slot, new FluidStack(fluid, amount))) {
            return FluidStack.EMPTY;
        }

        var canBeDrained = Math.min(storedAmount, amount);
        if (!simulate) {
            stacks.get(slot).setAmount(storedAmount - canBeDrained);
        }
        return new FluidStack(fluid, canBeDrained);
    }

    @Override
    public @NotNull FluidStack drain(int i, FluidAction action) {
        Fluid fluid = findFirstFluid();
        if (fluid == null) {
            return FluidStack.EMPTY;
        }

        int drained = 0;
        for (int slot = 0; slot < stacks.size(); slot++) {
            var innerDrained = innerDrain(slot, fluid, i, action.simulate());
            drained += innerDrained.getAmount();
        }
        return new FluidStack(fluid, drained);
    }

    private Fluid findFirstFluid() {
        for (int slot = 0; slot < stacks.size(); slot++) {
            FluidStack stack = stacks.get(slot);
            if (!stack.isEmpty()) {
                return stack.getFluid();
            }
        }
        return null;
    }

    @Override
    public CompoundTag serializeNBT() {
        var tag = new CompoundTag();
        var dataResult = STACKS_CODEC.encode(stacks, NbtOps.INSTANCE, tag);
        var result = dataResult.getOrThrow(false, Ref.LOG::error);
        return (CompoundTag) result;
    }


    @Override
    public void deserializeNBT(CompoundTag nbt) {
        var dataResult = STACKS_CODEC.decode(NbtOps.INSTANCE, nbt);
        var result = dataResult.getOrThrow(false, Ref.LOG::error);
        stacks.clear();
        stacks.addAll(result.getFirst());
    }
}
