package io.ticticboom.mods.mm.mixin;

import io.ticticboom.mods.mm.structure.StructureManager;
import net.minecraft.server.MinecraftServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Collection;
import java.util.concurrent.CompletableFuture;

@Mixin(MinecraftServer.class)
public class ServerMixin {
    @Inject(method = "reloadResources(Ljava/util/Collection;)Ljava/util/concurrent/CompletableFuture;", at = @At("RETURN"))
    private void reloadResources(Collection<String> coll, CallbackInfoReturnable<CompletableFuture<Void>> cir) {
        StructureManager.validateAllPieces();
    }
}
