package io.ticticboom.mods.mm.compat.kjs.event;

import dev.latvian.mods.kubejs.event.EventJS;
import io.ticticboom.mods.mm.compat.kjs.builder.ExtraBlockBuilderJS;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class ExtraBlockEventJS extends EventJS {
    @Getter
    private final List<ExtraBlockBuilderJS> builder = new ArrayList<>();

    public ExtraBlockBuilderJS create(String id) {
        var res = new ExtraBlockBuilderJS(id);
        builder.add(res);
        return res;
    }
}
