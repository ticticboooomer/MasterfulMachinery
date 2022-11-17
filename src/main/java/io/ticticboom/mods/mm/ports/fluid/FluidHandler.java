package io.ticticboom.mods.mm.ports.fluid;

import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import org.jetbrains.annotations.NotNull;

public class FluidHandler implements IFluidHandler {

    public FluidStack fluid = FluidStack.EMPTY;
    private int capacity;

    public FluidHandler(int capacity) {

        this.capacity = capacity;
    }

    @Override
    public int getTanks() {
        return 1;
    }

    @NotNull
    @Override
    public FluidStack getFluidInTank(int tank) {
        if (tank != 0) {
            return FluidStack.EMPTY;
        }
        return fluid;
    }

    @Override
    public int getTankCapacity(int tank) {
        if (tank != 0) {
            return 0;
        }
        return capacity;
    }

    @Override
    public boolean isFluidValid(int tank, @NotNull FluidStack stack) {
        if (tank != 0) {
            return false;
        }
        if (fluid.isEmpty()) {
            return true;
        }
        if (fluid.containsFluid(stack)) {
            return true;
        }
        return false;
    }

    @Override
    public int fill(FluidStack resource, FluidAction action) {
        if (!isFluidValid(0, resource)) {
            return 0;
        }
        var remainCapacity = capacity - fluid.getAmount();
        if (action.simulate()) {
            return Math.min(remainCapacity, resource.getAmount());
        } else if (action.execute()) {
            var toFill = Math.min(remainCapacity, resource.getAmount());
            if (fluid.isEmpty()) {
                fluid = new FluidStack(resource.getFluid(), toFill);
            } else {
                fluid.setAmount(fluid.getAmount() + toFill);
            }
            return toFill;
        }

        return 0;
    }

    @NotNull
    @Override
    public FluidStack drain(FluidStack resource, FluidAction action) {
        if (!fluid.containsFluid(resource)) {
            return FluidStack.EMPTY;
        }

        var drained = Math.min(resource.getAmount(), fluid.getAmount());
        if (action.simulate()) {
            return new FluidStack(resource.getFluid(), drained);
        } else if (action.execute()) {
            fluid.setAmount(fluid.getAmount() - drained);
            return new FluidStack(resource.getFluid(), drained);
        }
        return FluidStack.EMPTY;
    }

    @NotNull
    @Override
    public FluidStack drain(int maxDrain, FluidAction action) {
        if (fluid.isEmpty()) {
            return fluid;
        }
        var drained = Math.min(maxDrain, fluid.getAmount());
        if (action.simulate()) {
            return new FluidStack(fluid.getFluid(), drained);
        } else if (action.execute()) {
            fluid.setAmount(fluid.getAmount() - drained);
        }
        return FluidStack.EMPTY;
    }
}
