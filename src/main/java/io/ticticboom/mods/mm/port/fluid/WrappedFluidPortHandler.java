package io.ticticboom.mods.mm.port.fluid;

import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import org.jetbrains.annotations.NotNull;

public class WrappedFluidPortHandler implements IFluidHandler {
    private final FluidPortHandler handler;

    public WrappedFluidPortHandler(FluidPortHandler handler) {
        this.handler = handler;
    }

    @Override
    public int getTanks() {
        return handler.getTanks();
    }

    @Override
    public @NotNull FluidStack getFluidInTank(int tank) {
        return handler.getFluidInTank(tank);
    }

    @Override
    public int getTankCapacity(int tank) {
        return handler.getTankCapacity(tank);
    }

    @Override
    public boolean isFluidValid(int tank, @NotNull FluidStack stack) {
        return handler.isFluidValid(tank, stack);
    }

    @Override
    public int fill(FluidStack resource, FluidAction action) {
        if (resource.isEmpty()) {
            return 0;
        }

        int filled = 0;
        for (int i = 0; i < handler.getTanks(); i++) {
            FluidStack stack = handler.getFluidInTank(i);
            int tankCapacity = handler.getTankCapacity(i);
            if (stack.getAmount() >= tankCapacity) {
                continue;
            }

            if (stack.isEmpty()) {
                filled = tankCapacity;
                if (action.execute()) {
                    handler.setFluidInTank(i, new FluidStack(resource.getFluid(), filled));
                }
                break;
            } else if (handler.isFluidValid(i, resource)) {
                var amountToFill = Math.min(tankCapacity - stack.getAmount(), resource.getAmount());
                if (action.execute()) {
                    handler.setFluidInTank(i, new FluidStack(stack.getFluid(), stack.getAmount() + amountToFill));
                }
                filled = amountToFill;
                break;
            }
            handler.getChanged().call();
        }

        handler.getChanged().call();
        return filled;
    }

    @Override
    public @NotNull FluidStack drain(FluidStack resource, FluidAction action) {
        if (resource.isEmpty()) {
            return FluidStack.EMPTY;
        }

        var drained = 0;
        var taken = FluidStack.EMPTY;
        for (int i = 0; i < handler.getTanks(); i++) {
            taken = handler.innerDrain(i, resource.getFluid(), resource.getAmount(), action.simulate());
            drained = taken.getAmount();
            if (drained != 0) {
                break;
            }
        }
        handler.getChanged().call();
        return new FluidStack(resource.getFluid(), drained);
    }

    @Override
    public @NotNull FluidStack drain(int maxDrain, FluidAction action) {
        FluidStack res = FluidStack.EMPTY;
        for (int i = 0; i < handler.getTanks(); i++) {
            FluidStack stack = handler.getFluidInTank(i);
            res = handler.innerDrain(i, stack.getFluid(), maxDrain, action.simulate());
            if (res.getAmount() != 0) {
                break;
            }
        }
        handler.getChanged().call();
        return res;
    }
}
