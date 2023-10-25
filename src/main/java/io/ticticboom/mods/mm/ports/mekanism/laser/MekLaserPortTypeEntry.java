package io.ticticboom.mods.mm.ports.mekanism.laser;

import com.google.gson.JsonObject;
import io.ticticboom.mods.mm.Ref;
import io.ticticboom.mods.mm.ports.base.IConfiguredIngredient;
import io.ticticboom.mods.mm.ports.base.IConfiguredPort;
import io.ticticboom.mods.mm.ports.base.MMPortTypeEntry;
import io.ticticboom.mods.mm.ports.base.PortStorage;
import io.ticticboom.mods.mm.ports.mekanism.laser.block.MekLaserInputPortBlockEntity;
import io.ticticboom.mods.mm.ports.mekanism.laser.block.MekLaserPortBlock;
import io.ticticboom.mods.mm.setup.model.MMBlockProvider;
import io.ticticboom.mods.mm.setup.model.PortModel;
import io.ticticboom.mods.mm.util.Deferred;
import mekanism.api.math.FloatingLong;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;
import java.util.function.Supplier;

public class MekLaserPortTypeEntry extends MMPortTypeEntry {
    @Override
    public Class<? extends PortStorage> storageClass() {
        return MekLaserPortStorage.class;
    }

    @Override
    public IConfiguredPort parse(JsonObject element) {
        return new MekLaserConfiguredPort();
    }

    @Override
    public IConfiguredIngredient parseIngredient(JsonObject json) {
        var energy = json.get("energy").getAsDouble();
        return new MekLaserConfiguredIngredient(FloatingLong.create(energy));
    }

    @Override
    public ResourceLocation overlay(boolean input) {
        return input ? Ref.res("block/compat_ports/mekanism_laser_input_cutout") : Ref.res("block/compat_ports/mekanism_laser_output_cutout");
    }

    @Override
    public PortStorage createStorage(IConfiguredPort config) {
        return new MekLaserPortStorage((MekLaserConfiguredPort)config);
    }

    @Override
    public boolean processInputs(IConfiguredIngredient ingredient, List<PortStorage> storage) {
        var conf = (MekLaserConfiguredIngredient) ingredient;
        for (PortStorage portStorage : storage) {
            if (portStorage instanceof MekLaserPortStorage eps) {
                if (eps.laser.greaterOrEqual(conf.energy())) {
                    eps.laser = eps.laser.subtract(conf.energy());
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean processOutputs(IConfiguredIngredient ingredient, List<PortStorage> storage) {
        return false;
    }

    @Override
    public BlockEntityType.BlockEntitySupplier<BlockEntity> beSupplier(boolean input, PortModel model, RegistryObject<BlockEntityType<BlockEntity>> beType, RegistryObject<Block> block) {
        return (a, b) -> new MekLaserInputPortBlockEntity(new MMBlockProvider(block), a,b, model);
    }

    @Override
    public Supplier<Block> blockSupplier(boolean input, PortModel model, Deferred<RegistryObject<MenuType<?>>> menu, Deferred<RegistryObject<BlockEntityType<BlockEntity>>> beType) {
        return () -> new MekLaserPortBlock(model, menu.data, beType.data);
    }
}
