package io.ticticboom.mods.mm.port.pneumaticcraft.air;

import com.google.gson.JsonObject;
import io.ticticboom.mods.mm.cap.MMCapabilities;
import io.ticticboom.mods.mm.port.IPortStorage;
import io.ticticboom.mods.mm.port.IPortStorageModel;
import io.ticticboom.mods.mm.port.common.INotifyChangeFunction;
import lombok.Getter;
import me.desht.pneumaticcraft.api.PNCCapabilities;
import me.desht.pneumaticcraft.api.pressure.PressureTier;
import me.desht.pneumaticcraft.api.tileentity.IAirHandlerMachine;
import me.desht.pneumaticcraft.common.capabilities.MachineAirHandler;
import me.desht.pneumaticcraft.common.util.DirectionUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.IntTag;
import net.minecraft.nbt.Tag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.ItemStackHandler;

import java.util.*;

public class PneumaticAirPortStorage implements IPortStorage {
    public final PneumaticAirPortStorageModel model;
    private final INotifyChangeFunction changed;

    @Getter
    private IAirHandlerMachine airhandler;
    private final LazyOptional<IAirHandlerMachine> airhandlerLO;
    public final Map<IAirHandlerMachine, List<Direction>> airHandlerMap = new IdentityHashMap();
    private final UUID uid = UUID.randomUUID();

    public PneumaticAirPortStorage(PneumaticAirPortStorageModel model, INotifyChangeFunction changed) {
        this.model = model;
        this.changed = changed;
        airhandler = new MachineAirHandler(model.tier(), model.volume());
        airhandlerLO = LazyOptional.of(() -> airhandler);
    }

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> capability) {
        if (hasCapability(capability)) {
            return airhandlerLO.cast();
        } else {
            return LazyOptional.empty();
        }
    }


    @Override
    public <T> boolean hasCapability(Capability<T> capability) {
        return capability == PNCCapabilities.AIR_HANDLER_MACHINE_CAPABILITY;
    }

    @Override
    public CompoundTag save(CompoundTag tag) {
        tag.put("Air", serializeNBT());
        return tag;
    }

    @Override
    public void load(CompoundTag tag) {
        deserializeNBT(tag.get("Air"));
    }

    @Override
    public IPortStorageModel getStorageModel() {
        return model;
    }

    @Override
    public UUID getStorageUid() {
        return uid;
    }

    @Override
    public JsonObject debugDump() {
        JsonObject dump = new JsonObject();
        dump.addProperty("uid", uid.toString());
        return dump;
    }



    public float getPressure(){
        return airhandler.getPressure();
    }

    public void addAir(int air){
        airhandler.addAir(air);
    }

    public Tag serializeNBT() {
        return IntTag.valueOf(this.airhandler.getAir());
    }
    public void deserializeNBT(Tag nbt) {
        if (nbt instanceof IntTag intNbt) {
            this.addAir(intNbt.getAsInt());
        } else {
            throw new IllegalArgumentException("Can not deserialize to an instance that isn't the default implementation");
        }

    }

    public void onNeighborBlockUpdate(BlockPos fromPos) {
        airHandlerMap.clear();
        for (Direction side : DirectionUtil.VALUES) {
            airhandlerLO.cast().ifPresent(handler -> airHandlerMap.computeIfAbsent((IAirHandlerMachine) handler, k -> new ArrayList<>()).add(side));
        }
        airHandlerMap.forEach(IAirHandlerMachine::setConnectedFaces);

    }



}
