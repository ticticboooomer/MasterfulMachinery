package io.ticticboom.mods.mm.compat.kjs.event;

import dev.latvian.mods.kubejs.event.EventJS;
import io.ticticboom.mods.mm.compat.kjs.builder.StructureBuilderJS;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class StructureEventJS extends EventJS {
    @Getter
    private final List<StructureBuilderJS> builders = new ArrayList<>();

    public StructureBuilderJS create(String id) {
        var res = new StructureBuilderJS(id);
        builders.add(res);
        return res;
    }
}
