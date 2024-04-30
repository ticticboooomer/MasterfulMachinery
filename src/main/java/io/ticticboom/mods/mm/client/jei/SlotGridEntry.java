package io.ticticboom.mods.mm.client.jei;

public class SlotGridEntry {
    public final int x;
    public final int y;

    private boolean used;
    public SlotGridEntry(int x, int y) {

        this.x = x;
        this.y = y;
    }

    public void setUsed() {
        used = true;
    }

    public boolean used() {
        return used;
    }
}
