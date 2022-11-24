package io.ticticboom.mods.mm.compat.jei;

import net.minecraft.core.Vec3i;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;

public class SlotGrid {

    private final int xs;
    private final int ys;
    private final int width;
    private final int height;
    private List<SlotGridEntry> slots;

    public SlotGrid(int xs, int ys, int width, int height) {
        this.xs = xs;
        this.ys = ys;
        this.width = width;
        this.height = height;
        slots = new ArrayList<>();
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                slots.add(new SlotGridEntry(x * xs, y * ys));
            }
        }
    }

    public SlotGridEntry next() {
        for (SlotGridEntry slot : slots) {
            if (!slot.used()) {
                return slot;
            }
        }
        return new SlotGridEntry(1000, 1000);
    }
}
