package io.ticticboom.mods.mm.port.item;

import com.google.gson.JsonObject;
import io.ticticboom.mods.mm.port.IPortIngredient;
import io.ticticboom.mods.mm.port.IPortParser;
import io.ticticboom.mods.mm.port.IPortStorageFactory;
import io.ticticboom.mods.mm.port.item.register.ItemPortMenu;
import io.ticticboom.mods.mm.port.item.register.ItemPortScreen;
import io.ticticboom.mods.mm.setup.RegistryGroupHolder;
import io.ticticboom.mods.mm.util.ParserUtils;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.world.inventory.MenuType;

public class ItemPortParser implements IPortParser {
    @Override
    public IPortStorageFactory parseStorage(JsonObject json) {
        int rows = json.get("rows").getAsInt();
        int columns = json.get("columns").getAsInt();
        return new ItemPortStorageFactory(new ItemPortStorageModel(rows, columns));
    }

    @Override
    public IPortIngredient parseRecipeIngredient(JsonObject json) {
        var itemId = ParserUtils.parseId(json, "item");
        var count = json.get("count").getAsInt();
        return new ItemPortIngredient(itemId, count);
    }
}
