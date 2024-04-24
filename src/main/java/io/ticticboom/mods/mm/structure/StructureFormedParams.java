package io.ticticboom.mods.mm.structure;

import lombok.Getter;
import net.minecraft.world.level.Level;

@Getter
public class StructureFormedParams {
    private final Level level;
    private final StructureModel model;

    public StructureFormedParams(Level level, StructureModel model) {

        this.level = level;
        this.model = model;
    }
}
