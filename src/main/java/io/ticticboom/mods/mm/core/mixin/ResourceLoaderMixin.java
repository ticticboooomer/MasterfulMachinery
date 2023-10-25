package io.ticticboom.mods.mm.core.mixin;

import io.ticticboom.mods.mm.ModRoot;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackRepository;
import net.minecraft.server.packs.repository.RepositorySource;
import net.minecraftforge.forgespi.locating.IModFile;
import net.minecraftforge.resource.PathPackResources;
import net.minecraftforge.resource.ResourcePackLoader;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

@Mixin(ResourcePackLoader.class)
public class ResourceLoaderMixin {
    @Inject(method = "loadResourcePacks(Lnet/minecraft/server/packs/repository/PackRepository;Ljava/util/function/Function;)V", at = @At("RETURN"), remap = false)
    private static void injectPacks(PackRepository resourcePacks, Function<Map<IModFile, ? extends PathPackResources>, ? extends RepositorySource> packFinder, CallbackInfo ci) {
        ModRoot.injectDatapackFinder(resourcePacks);
    }
}
