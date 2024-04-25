package io.ticticboom.mods.mm.port;

import net.minecraft.world.level.Level;

import java.util.List;

public interface IPortIngredient {
    boolean canProcess(Level level, List<IPortStorage> inputStorages);
    void process(Level level, List<IPortStorage> inputStorages);
}
