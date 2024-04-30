package io.ticticboom.mods.mm.client.jei;

public class SlotGridEntry {
    public final int x;
    public final int y;

    private boolean used;
    public SlotGridEntry(int x, int y) {

        this.x = x;
        this.y = y;
    }

    public int getInnerX() {
        return x + 1;
    }

    public int getInnerY() {
        return y + 1;
    }

    public void setUsed() {
        used = true;
    }

    public boolean used() {
        return used;
    }
}
