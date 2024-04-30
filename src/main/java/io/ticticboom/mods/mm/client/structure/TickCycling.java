package io.ticticboom.mods.mm.client.structure;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class TickCycling<T> {
    @Getter
    private final List<T> part;
    @Setter
    private int interval = 0;
    private int counter = 0;
    private int index = 0;
    private int maxIndex = 0;

    public TickCycling(List<T> part) {
        this.part = part;
        index = 0;
        maxIndex = part.size() - 1;
    }

    public void tick() {
        counter++;
        if (counter % interval == 0) {
            index++;
        }
        if (index > maxIndex) {
            index = 0;
        }
    }

    public T next() {
        return part.get(index);
    }

    public void reset() {
        index = 0;
        counter = 0;
    }
}
