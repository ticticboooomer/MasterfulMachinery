package io.ticticboom.mods.mm.port.kinetic;

import com.simibubi.create.content.kinetics.BlockStressValues;
import com.simibubi.create.foundation.utility.Couple;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.Nullable;

public class MMKineticStressProvider implements BlockStressValues.IStressValueProvider {

    private final boolean input;
    private final float stress;

    public MMKineticStressProvider(boolean input, float stress) {
        this.input = input;
        this.stress = stress;
    }

    @Override
    public double getImpact(Block block) {
        return input ? stress : 0;
    }

    @Override
    public double getCapacity(Block block) {
        return !input ? stress : 0;
    }

    @Override
    public boolean hasImpact(Block block) {
        return input;
    }

    @Override
    public boolean hasCapacity(Block block) {
        return !input;
    }

    @Nullable
    @Override
    public Couple<Integer> getGeneratedRPM(Block block) {
        return null;
    }
}
