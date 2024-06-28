package io.ticticboom.mods.mm.datagen.provider;

import com.google.common.collect.ImmutableList;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mojang.datafixers.util.Pair;
import io.ticticboom.mods.mm.Ref;
import io.ticticboom.mods.mm.controller.IControllerPart;
import io.ticticboom.mods.mm.extra.IExtraBlockPart;
import io.ticticboom.mods.mm.port.IPortPart;
import io.ticticboom.mods.mm.setup.MMRegisters;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.ValidationContext;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSet;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;

import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class MMLootTableProvider extends LootTableProvider {

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();

    public MMLootTableProvider(DataGenerator generator) {
        super(generator);
        this.generator = generator;
    }

    private final DataGenerator generator;


    @Override
    protected List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, LootTable.Builder>>>, LootContextParamSet>> getTables() {
        return ImmutableList.of(Pair.of(BlockLoot::new, LootContextParamSets.BLOCK));
    }

    @Override
    protected void validate(Map<ResourceLocation, LootTable> map, ValidationContext validationtracker) {
    }

    public static class BlockLoot implements Consumer<BiConsumer<ResourceLocation, LootTable.Builder>> {

        @Override
        public void accept(BiConsumer<ResourceLocation, LootTable.Builder> consumer) {
            for (var blockEntry : MMRegisters.BLOCKS.getEntries()) {
                var block = blockEntry.get();
                if (block instanceof IControllerPart controllerPart) {
                    consumer.accept(Ref.id("blocks/" + controllerPart.getModel().id()), createBlockLootTable(block));
                }
                if (block instanceof IPortPart portPart) {
                    consumer.accept(Ref.id("blocks/" + portPart.getModel().id()), createBlockLootTable(block));
                }
                if (block instanceof IExtraBlockPart extra) {
                    consumer.accept(Ref.id("blocks/" + extra.getModel().id()), createBlockLootTable(block));
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
