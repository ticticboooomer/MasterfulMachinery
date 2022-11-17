package io.ticticboom.mods.mm.ports.fluid;

import com.google.gson.JsonObject;
import io.ticticboom.mods.mm.Ref;
import io.ticticboom.mods.mm.ports.base.IConfiguredIngredient;
import io.ticticboom.mods.mm.ports.base.IConfiguredPort;
import io.ticticboom.mods.mm.ports.base.MMPortTypeEntry;
import io.ticticboom.mods.mm.ports.base.PortStorage;
import io.ticticboom.mods.mm.ports.item.ItemConfiguredIngredient;
import io.ticticboom.mods.mm.ports.item.ItemConfiguredPort;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;

public class FluidPortTypeEntry extends MMPortTypeEntry {
    @Override
    public ResourceLocation id() {
        return Ref.Ports.FLUID;
    }

    @Override
    public Class<? extends PortStorage> storageClass() {
        return FluidPortStorage.class;
    }

    @Override
    public IConfiguredPort parse(JsonObject element) {
        return new FluidConfiguredPort(element.get("capacity").getAsInt());
    }

    @Override
    public IConfiguredIngredient parseIngredient(JsonObject json) {
        var fluid = ResourceLocation.tryParse(json.get("fluid").getAsString());
        var amount = json.get("amount").getAsInt();
        return new FluidConfiguredIngredient(fluid, amount);
    }

    @Override
    public ResourceLocation overlay(boolean input) {
        if (input) {
            return Ref.res("block/base_ports/fluid_input_cutout");
        }
        return Ref.res("block/base_ports/fluid_output_cutout");
    }

    @Override
    public PortStorage createStorage(IConfiguredPort config) {
        return new FluidPortStorage((FluidConfiguredPort) config);
    }

    @Override
    public boolean processInputs(IConfiguredIngredient ingredient, List<PortStorage> storage) {
        var conf = (FluidConfiguredIngredient) ingredient;
        var counter = 0;
        var fluid = ForgeRegistries.FLUIDS.getValue(conf.fluid());
        var fs = new FluidStack(fluid, conf.amount());
        for (PortStorage portStorage : storage) {
            if (portStorage instanceof FluidPortStorage fps) {
                var drained = fps.handler.drain(fs, IFluidHandler.FluidAction.EXECUTE);
                counter += drained.getAmount();
                if (counter >= conf.amount()) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean processOutputs(IConfiguredIngredient ingredient, List<PortStorage> storage) {
        var conf = (FluidConfiguredIngredient) ingredient;
        var counter = 0;
        var fluid = ForgeRegistries.FLUIDS.getValue(conf.fluid());
        var fs = new FluidStack(fluid, conf.amount());
        for (PortStorage portStorage : storage) {
            if (portStorage instanceof FluidPortStorage fps) {
                var drained = fps.handler.fill(fs, IFluidHandler.FluidAction.EXECUTE);
                counter += drained;
                if (counter >= conf.amount()) {
                    return true;
                }
            }
        }
        return false;
    }
}
