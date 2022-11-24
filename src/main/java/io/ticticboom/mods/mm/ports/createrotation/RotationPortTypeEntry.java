package io.ticticboom.mods.mm.ports.createrotation;

import com.google.gson.JsonObject;
import io.ticticboom.mods.mm.Ref;
import io.ticticboom.mods.mm.ports.base.IConfiguredIngredient;
import io.ticticboom.mods.mm.ports.base.IConfiguredPort;
import io.ticticboom.mods.mm.ports.base.MMPortTypeEntry;
import io.ticticboom.mods.mm.ports.base.PortStorage;
import io.ticticboom.mods.mm.ports.createrotation.block.CreateRotationGenPortBlockEntity;
import io.ticticboom.mods.mm.ports.createrotation.block.CreateRotationPortBlock;
import io.ticticboom.mods.mm.ports.createrotation.block.CreateRotationPortBlockEntity;
import io.ticticboom.mods.mm.setup.model.PortModel;
import io.ticticboom.mods.mm.util.Deferred;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;
import java.util.function.Supplier;

public class RotationPortTypeEntry extends MMPortTypeEntry {

    @Override
    public Class<? extends PortStorage> storageClass() {
        return RotationPortStorage.class;
    }

    @Override
    public IConfiguredPort parse(JsonObject element) {
        var stress = element.get("stress").getAsInt();
        return new RotationConfiguredPort(stress);
    }

    @Override
    public IConfiguredIngredient parseIngredient(JsonObject json) {
        var speed = json.get("speed").getAsFloat();
        return new RotationConfiguredIngredient(speed);
    }

    @Override
    public ResourceLocation overlay(boolean input) {
        return input ? Ref.res("block/compat_ports/create_rotation_input_cutout") : Ref.res("block/compat_ports/create_rotation_output_cutout");
    }

    @Override
    public PortStorage createStorage(IConfiguredPort config) {
        RotationConfiguredPort conf = (RotationConfiguredPort) config;
        return new RotationPortStorage(conf.stress());
    }

    @Override
    public boolean processInputs(IConfiguredIngredient ingredient, List<PortStorage> storage) {
        RotationConfiguredIngredient conf = (RotationConfiguredIngredient) ingredient;
        for (PortStorage portStorage : storage) {
            if (portStorage instanceof RotationPortStorage rot) {
                if (Math.abs(rot.speed) >= conf.speed() && !rot.isOverStressed) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean processOutputs(IConfiguredIngredient ingredient, List<PortStorage> storage) {
        RotationConfiguredIngredient conf = (RotationConfiguredIngredient) ingredient;
        for (PortStorage portStorage : storage) {
            if (portStorage instanceof RotationPortStorage rot) {
                rot.speed = conf.speed();
            }
        }
        return true;
    }
    @Override
    public Supplier<Block> blockSupplier(boolean input, PortModel model, Deferred<RegistryObject<MenuType<?>>> menu, Deferred<RegistryObject<BlockEntityType<?>>> beType) {
        return () -> new CreateRotationPortBlock(model, menu.data, beType.data);
    }

    @Override
    public BlockEntityType.BlockEntitySupplier<?> beSupplier(boolean input, PortModel model, RegistryObject<BlockEntityType<?>> beType) {
        if (input) {
            return (a, b) -> new CreateRotationPortBlockEntity(beType.get(), a, b, model);
        } else {
            return (a, b) -> new CreateRotationGenPortBlockEntity(beType.get(), a, b, model);
        }
    }
}
