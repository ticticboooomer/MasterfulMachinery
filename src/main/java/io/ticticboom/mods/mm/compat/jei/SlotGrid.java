package io.ticticboom.mods.mm.compat.jei;

import java.util.ArrayList;
import java.util.List;

public class SlotGrid {

    private final int xs;
    private final int ys;
    private final int width;
    private final int height;
    private final int offsetX;
    private final int offsetY;
    private List<SlotGridEntry> slots;

    public SlotGrid(int xs, int ys, int width, int height, int offsetX, int offsetY) {
        this.xs = xs;
        this.ys = ys;
        this.width = width;
        this.height = height;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        slots = new ArrayList<>();
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                slots.add(new SlotGridEntry((x * xs) + offsetX, (y * ys) + offsetY));
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

    public List<SlotGridEntry> getSlots() {
        return slots.stream().filter(SlotGridEntry::used).toList();
    }
}
