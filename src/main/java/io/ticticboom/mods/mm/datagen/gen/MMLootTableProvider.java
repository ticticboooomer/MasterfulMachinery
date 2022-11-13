package io.ticticboom.mods.mm.datagen.gen;

import com.google.gson.Gson;
import io.ticticboom.mods.mm.ModRoot;
import io.ticticboom.mods.mm.Ref;
import io.ticticboom.mods.mm.setup.MMRegistries;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.HashCache;
import net.minecraft.data.loot.BlockLoot;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.IntRange;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.LootTables;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntry;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraftforge.registries.RegistryObject;
import org.spongepowered.tools.obfuscation.interfaces.IObfuscationDataProvider;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class MMLootTableProvider extends LootTableProvider {
    public static Gson GSON = new Gson();

    protected final Map<Block, LootTable.Builder> blockLootTable = new HashMap<>();
    private DataGenerator gen;

    public MMLootTableProvider(DataGenerator gen) {
        super(gen);
        this.gen = gen;
    }

    @Override
    public void run(HashCache cache) {
        for (RegistryObject<Block> entry : MMRegistries.BLOCKS.getEntries()) {
            var pool = LootPool.lootPool()
                    .setRolls(ConstantValue.exactly(1))
                    .add(LootItem.lootTableItem(entry.get().asItem()));
            blockLootTable.put(entry.get(), LootTable.lootTable().withPool(pool));
        }

        var tables = new HashMap<ResourceLocation, LootTable>();
        for (Map.Entry<Block, LootTable.Builder> entry : this.blockLootTable.entrySet()) {
            tables.put(entry.getKey().getLootTable(), entry.getValue().setParamSet(LootContextParamSets.BLOCK).build());
        }
        this.writeTables(cache, tables);
    }

    private void writeTables(HashCache cache, Map<ResourceLocation, LootTable> tables) {
        Path output = this.gen.getOutputFolder();
        tables.forEach((key, val) -> {
            Path path = output.resolve("data/" + key.getNamespace() + "/loot_tables/" + key.getPath() + ".json");
            try {
                DataProvider.save(GSON, cache, LootTables.serialize(val), path);
            } catch (IOException e) {
                Ref.LOG.error("Couldn't Write loot table {}", path, e);
            }
        });
    }
}
