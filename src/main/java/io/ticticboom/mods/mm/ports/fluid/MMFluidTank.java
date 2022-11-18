package io.ticticboom.mods.mm.ports.fluid;

import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.templates.FluidTank;

public class MMFluidTank extends FluidTank {
    public MMFluidTank(int capacity) {
        super(capacity);
    }

    public FluidStack stack() {
        return fluid;
    }

    public void setStack(FluidStack stack) {
        this.fluid = stack;
    }
}
