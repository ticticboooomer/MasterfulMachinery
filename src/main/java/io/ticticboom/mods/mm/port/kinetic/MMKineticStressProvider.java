package io.ticticboom.mods.mm.port.kinetic;

import com.simibubi.create.content.kinetics.BlockStressValues;
import com.simibubi.create.foundation.utility.Couple;
import io.ticticboom.mods.mm.model.PortModel;
import io.ticticboom.mods.mm.port.IPortBlock;
import io.ticticboom.mods.mm.port.IPortStorageModel;
import net.minecraft.world.level.block.Block;
import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.Nullable;

public class MMKineticStressProvider implements BlockStressValues.IStressValueProvider {

    private Pair<PortModel, CreateKineticPortStorageModel> getModel(Block block) {
        if (block instanceof IPortBlock pb) {
            var storageModel = pb.getModel().config().getModel();
            if (storageModel instanceof CreateKineticPortStorageModel psm) {
                return Pair.of(pb.getModel(), psm);
            }
        }
        return null;
    }

    @Override
    public double getImpact(Block block) {
        var models = getModel(block);
        if (models == null) {
            return 0;
        }
        return models.getLeft().input() ? models.getRight().stress() : 0;
    }

    @Override
    public double getCapacity(Block block) {
        var models = getModel(block);
        if (models == null) {
            return 0;
        }
        return !models.getLeft().input() ? models.getRight().stress() : 0;
    }

    @Override
    public boolean hasImpact(Block block) {
        var models = getModel(block);
        if (models == null) {
            return false;
        }
        return models.getLeft().input();
    }

    @Override
    public boolean hasCapacity(Block block) {
        var models = getModel(block);
        if (models == null) {
            return false;
        }
        return models.getLeft().input();
    }

    @Nullable
    @Override
    public Couple<Integer> getGeneratedRPM(Block block) {
        return Couple.create(0, 256);
    }
}
