package io.ticticboom.mods.mm.cap;

import io.ticticboom.mods.mm.Ref;
import io.ticticboom.mods.mm.setup.MMRegisters;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.items.IItemHandler;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class MMCapabilities {
    public static final Capability<IItemHandler> ITEM = CapabilityManager.get(new CapabilityToken<>() {});
    public static final Capability<IFluidHandler> FLUID = CapabilityManager.get(new CapabilityToken<>() {});
    public static final Capability<IEnergyStorage> ENERGY = CapabilityManager.get(new CapabilityToken<>() {});
    public static final Capability<IScannerSelection> SCANNER_SELECTION = CapabilityManager.get(new CapabilityToken<>() {});

    @SubscribeEvent
    public void registerCaps(RegisterCapabilitiesEvent event) {
        event.register(IScannerSelection.class);
    }

    @SubscribeEvent
    public void attachCaps(AttachCapabilitiesEvent<ItemStack> event) {
        if (event.getObject().getItem() == MMRegisters.SCANNER_TOOL.get()) {
            event.addCapability(Ref.SCANNER_CAP, new ScannerSelectionProvider());
        }
    }
}
