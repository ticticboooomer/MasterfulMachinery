package io.ticticboom.mods.mm.setup.loader;

import com.google.gson.JsonObject;
import io.ticticboom.mods.mm.model.PortModel;
import io.ticticboom.mods.mm.port.MMPortRegistry;
import io.ticticboom.mods.mm.port.PortType;

import java.util.ArrayList;
import java.util.List;

public class PortLoader extends AbstractConfigLoader<PortModel> {

    public static void loadAll() {
        new PortLoader().load();
    }

    @Override
    protected String getConfigPath() {
        return "ports";
    }

    @Override
    protected List<PortModel> parseModels(List<JsonObject> jsons) {
        var result = new ArrayList<PortModel>();
        result.ensureCapacity(jsons.size() * 2);
        for (JsonObject json : jsons) {
            var inputModel = PortModel.parse(json, true);
            var outputModel = PortModel.parse(json, false);
            result.add(inputModel);
            result.add(outputModel);
        }
        return result;
    }

    @Override
    protected void registerModels(List<PortModel> portModels) {
        for (PortModel portModel : portModels) {
            PortType portType = MMPortRegistry.get(portModel.type());
            portType.register(portModel);
        }
    }
}
