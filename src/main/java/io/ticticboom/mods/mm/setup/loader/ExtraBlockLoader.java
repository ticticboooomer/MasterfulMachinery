package io.ticticboom.mods.mm.setup.loader;

import com.google.gson.JsonObject;
import io.ticticboom.mods.mm.compat.interop.MMInteropManager;
import io.ticticboom.mods.mm.extra.ExtraBlockModel;
import io.ticticboom.mods.mm.extra.MMExtraBlockRegistry;

import java.util.List;

public class ExtraBlockLoader extends AbstractConfigLoader<ExtraBlockModel> {

    public static void loadAll() {
        new ExtraBlockLoader().load();
    }

    @Override
    protected String getConfigPath() {
        return "extras";
    }

    @Override
    protected List<ExtraBlockModel> parseModels(List<JsonObject> jsons) {
        return jsons.stream().map(ExtraBlockModel::parse).toList();
    }

    @Override
    protected void registerModels(List<ExtraBlockModel> models) {
        for (ExtraBlockModel model : models) {
            var type = MMExtraBlockRegistry.get(model.type());
            type.register(model);
        }
        if (MMInteropManager.KUBEJS.isPresent()) {
            for (ExtraBlockModel model : MMInteropManager.KUBEJS.get().postRegisterExtraBlocks()) {
                var type = MMExtraBlockRegistry.get(model.type());
                type.register(model);
            }
        }
    }
}
