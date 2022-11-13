package io.ticticboom.mods.mm.core.mixin;

import io.ticticboom.mods.mm.ModRoot;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackRepository;
import net.minecraft.server.packs.repository.RepositorySource;
import net.minecraftforge.fml.loading.moddiscovery.ModFile;
import net.minecraftforge.forgespi.locating.IModFile;
import net.minecraftforge.resource.PathResourcePack;
import net.minecraftforge.resource.ResourcePackLoader;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;

@Mixin(ResourcePackLoader.class)
public class ResourceLoaderMixin {
    @Inject(method = "loadResourcePacks(Lnet/minecraft/server/packs/repository/PackRepository;Ljava/util/function/BiFunction;)V", at = @At("RETURN"), remap = false)
    private static void injectPacks(PackRepository resourcePacks, BiFunction<Map<IModFile, ? extends PathResourcePack>, BiConsumer<? super PathResourcePack, Pack>, ? extends RepositorySource> packFinder, CallbackInfo ci) {
        ModRoot.injectDatapackFinder(resourcePacks);
    }
}
