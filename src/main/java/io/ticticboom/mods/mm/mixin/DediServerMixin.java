package io.ticticboom.mods.mm.mixin;

import io.ticticboom.mods.mm.structure.StructureManager;
import net.minecraft.server.dedicated.DedicatedServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(DedicatedServer.class)
public class DediServerMixin {

    @Inject(method = "initServer", at = @At("RETURN"))
    public void initServerMixin(CallbackInfoReturnable<Boolean> cir) {
        StructureManager.validateAllPieces();
    }
}
