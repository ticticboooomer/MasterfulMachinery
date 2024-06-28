package io.ticticboom.mods.mm.port.mekanism.chemical;

import com.google.gson.JsonObject;
import io.ticticboom.mods.mm.port.IPortStorage;
import io.ticticboom.mods.mm.port.IPortStorageModel;
import io.ticticboom.mods.mm.port.common.INotifyChangeFunction;
import mekanism.api.Action;
import mekanism.api.AutomationType;
import mekanism.api.chemical.Chemical;
import mekanism.api.chemical.ChemicalStack;
import mekanism.api.chemical.IChemicalTank;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;

import java.util.UUID;

public abstract class MekanismChemicalPortStorage<CHEMICAL extends Chemical<CHEMICAL>, STACK extends ChemicalStack<CHEMICAL>> implements IPortStorage {

    public IChemicalTank<CHEMICAL, STACK> chemicalTank;
    private final MekanismChemicalPortStorageModel model;
    private final LazyOptional<IChemicalTank<CHEMICAL, STACK>> handleL0;
    private final UUID uid = UUID.randomUUID();

    protected MekanismChemicalPortStorage(MekanismChemicalPortStorageModel model, INotifyChangeFunction changed) {
        this.model = model;
        chemicalTank = createTank(model.amount(), changed);
        handleL0 = LazyOptional.of(() -> chemicalTank);
    }

    protected abstract IChemicalTank<CHEMICAL, STACK> createTank(long capacity, INotifyChangeFunction changed);
    protected abstract JsonObject debugStack(STACK stack);

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> capability) {
        if (hasCapability(capability)) {
            return handleL0.cast();
        }
        return LazyOptional.empty();
    }

    @Override
    public CompoundTag save(CompoundTag tag) {
        tag.put("handler", chemicalTank.serializeNBT());
        return tag;
    }

    @Override
    public void load(CompoundTag tag) {
        chemicalTank.deserializeNBT(tag.getCompound("handler"));
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
        JsonObject json = new JsonObject();
        json.addProperty("uid", uid.toString());
        json.addProperty("amount", model.amount());
        var stack = debugStack(chemicalTank.getStack());
        json.add("stack", stack);
        return json;
    }

    public STACK extract(long amount, Action action) {
        return chemicalTank.extract(amount, action, AutomationType.INTERNAL);
    }

    public STACK insert(STACK stack, Action action) {
        var leftInStack = chemicalTank.insert(stack, action, AutomationType.INTERNAL);
        long remainingToInsert = stack.getAmount() - leftInStack.getAmount();
        stack.setAmount(remainingToInsert);
        return stack;
    }
}
