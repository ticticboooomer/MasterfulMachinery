package io.ticticboom.mods.mm.datagen.provider;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.ticticboom.mods.mm.Ref;
import io.ticticboom.mods.mm.controller.IControllerPart;
import io.ticticboom.mods.mm.port.IPortPart;
import io.ticticboom.mods.mm.setup.MMRegisters;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;

import java.util.function.BiConsumer;

public class MMLootTableProvider extends LootTableProvider {

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();

    public MMLootTableProvider(DataGenerator generator) {
        super(generator.getPackOutput(), ImmutableSet.of(),
                ImmutableList.of(new SubProviderEntry(MMLootTableSubProvider::new, LootContextParamSets.BLOCK)));
        this.generator = generator;
    }

    private final DataGenerator generator;

    public static class MMLootTableSubProvider implements LootTableSubProvider {

        @Override
        public void generate(BiConsumer<ResourceLocation, LootTable.Builder> consumer) {
            for (var blockEntry : MMRegisters.BLOCKS.getEntries()) {
                var block = blockEntry.get();
                if (block instanceof IControllerPart controllerPart) {
                    consumer.accept(Ref.id(controllerPart.getModel().id()), createBlockLootTable(block));
                }
                if (block instanceof IPortPart portPart) {
                    consumer.accept(Ref.id(portPart.getModel().id()), createBlockLootTable(block));
                }
            }
        }

        protected LootTable.Builder createBlockLootTable(Block block) {
            LootPool.Builder builder = LootPool.lootPool()
                    .setRolls(ConstantValue.exactly(1f))
                    .add(LootItem.lootTableItem(block));
            return LootTable.lootTable().withPool(builder);
        }
    }
}
