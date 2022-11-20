package io.ticticboom.mods.mm.datagen.gen;

import io.ticticboom.mods.mm.Ref;
import io.ticticboom.mods.mm.block.ControllerBlock;
import io.ticticboom.mods.mm.block.PortBlock;
import io.ticticboom.mods.mm.ports.base.IPortBlock;
import io.ticticboom.mods.mm.setup.MMRegistries;
import io.ticticboom.mods.mm.setup.model.PortModel;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.Direction;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ModelBuilder;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.client.model.generators.VariantBlockStateBuilder;
import net.minecraftforge.client.model.generators.loaders.MultiLayerModelBuilder;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

import java.util.stream.Collectors;

public class MMBlockStateProvider extends BlockStateProvider {
    public MMBlockStateProvider(DataGenerator gen, ExistingFileHelper exFileHelper) {
        super(gen, Ref.ID, exFileHelper);
    }
    public static final ResourceLocation BASE_TEXTURE = new ResourceLocation(Ref.ID, "block/base_block");
    private static final ResourceLocation CONTROLLER_TEXTURE = new ResourceLocation(Ref.ID, "block/controller_cutout");
    @Override
    protected void registerStatesAndModels() {
        var controllers = MMRegistries.BLOCKS.getEntries().stream().filter(x -> x.get() instanceof ControllerBlock).collect(Collectors.toList());
        var ports = MMRegistries.BLOCKS.getEntries().stream().filter(x -> x.get() instanceof IPortBlock).collect(Collectors.toList());

        for (var controller : controllers) {
            if (!controller.isPresent()){
                return;
            }

            dynamicBlockNorthOverlay(controller.getId(), BASE_TEXTURE, CONTROLLER_TEXTURE);
            VariantBlockStateBuilder variantBuilder = getVariantBuilder(controller.get());
            variantBuilder.partialState().with(HorizontalDirectionalBlock.FACING, Direction.NORTH).modelForState().modelFile(new ModelFile.UncheckedModelFile(new ResourceLocation(Ref.ID, "block/" + controller.getId().getPath()))).rotationY(0).addModel();
            variantBuilder.partialState().with(HorizontalDirectionalBlock.FACING, Direction.SOUTH).modelForState().modelFile(new ModelFile.UncheckedModelFile(new ResourceLocation(Ref.ID, "block/" + controller.getId().getPath()))).rotationY(180).addModel();
            variantBuilder.partialState().with(HorizontalDirectionalBlock.FACING, Direction.EAST).modelForState().modelFile(new ModelFile.UncheckedModelFile(new ResourceLocation(Ref.ID, "block/" + controller.getId().getPath()))).rotationY(90).addModel();
            variantBuilder.partialState().with(HorizontalDirectionalBlock.FACING, Direction.WEST).modelForState().modelFile(new ModelFile.UncheckedModelFile(new ResourceLocation(Ref.ID, "block/" + controller.getId().getPath()))).rotationY(270).addModel();
        }

        for (var port : ports) {
            var portType = MMRegistries.PORTS.get(((IPortBlock) port.get()).model().port());
            portType.generateBlockStates(this, (port.get()));
        }
    }

    private void dynamicBlockNorthOverlay(ResourceLocation loc, ResourceLocation baseTexture, ResourceLocation overlayTexture) {
        models().getBuilder(loc.toString()).parent(new ModelFile.UncheckedModelFile(mcLoc("block/block")))
                .texture("particle", overlayTexture)
//                .transforms()
//                .transform(ModelBuilder.Perspective.THIRDPERSON_LEFT)
//                .rotation(75F, 45F, 0F)
//                .translation(0F, 2.5f, 0)
//                .scale(0.375f,0.375f,0.375f)
//                .end()
//                .transform(ModelBuilder.Perspective.THIRDPERSON_RIGHT)
//                .rotation(75F, 45F, 0F)
//                .translation(0F, 2.5F, 0)
//                .scale(0.375F, 0.375F, 0.375F)
//                .end()
//                .end()
                .customLoader(MultiLayerModelBuilder::begin)
                .submodel(RenderType.solid(), this.models().nested().parent(new ModelFile.UncheckedModelFile(mcLoc("block/block")))
                        .texture("base", baseTexture)
                        .element()
                        .from(0, 0, 0)
                        .to(16, 16, 16)
                        .cube("#base")
                        //.allFaces((dir, uv) -> uv.uvs(0F,0.0F, 16F,16F))
                        .end()
                )
                .submodel(RenderType.translucent(), this.models().nested().parent(new ModelFile.UncheckedModelFile(mcLoc("block/block")))
                        .texture("overlay", overlayTexture)
                        .element()
                        .from(0, 0, 0)
                        .to(16, 16, 16)
                        .face(Direction.NORTH)
                        .texture("#overlay")
                        //.allFaces((dir, uv) -> uv.uvs(0F,0F, 16F,16F))
                        .end()
                        .end()
                )
                .end();
    }
    public void dynamicBlock(ResourceLocation loc, ResourceLocation baseTexture, ResourceLocation overlayTexture) {
        models().getBuilder(loc.toString()).parent(new ModelFile.UncheckedModelFile(mcLoc("block/block")))
                .texture("particle", overlayTexture)
                .transforms()
                .transform(ModelBuilder.Perspective.THIRDPERSON_LEFT)
                .rotation(75F, 45F, 0F)
                .translation(0F, 2.5F, 0)
                .scale(0.375F, 0.375F, 0.375F)
                .end()
                .transform(ModelBuilder.Perspective.THIRDPERSON_RIGHT)
                .rotation(75F, 45F, 0F)
                .translation(0F, 2.5F, 0)
                .scale(0.375F, 0.375F, 0.375F)
                .end()
                .end()
                .customLoader(MultiLayerModelBuilder::begin)
                .submodel(RenderType.solid(), this.models().nested().parent(new ModelFile.UncheckedModelFile(mcLoc("block/block")))
                        .texture("base", baseTexture)
                        .element()
                        .from(0, 0, 0)
                        .to(16, 16, 16)
                        .cube("#base")
                        //.allFaces((dir, uv) -> uv.uvs(0F,0.0F, 16F,16F))
                        .end()
                )
                .submodel(RenderType.translucent(), this.models().nested().parent(new ModelFile.UncheckedModelFile(mcLoc("block/block")))
                        .texture("overlay", overlayTexture)
                        .element()
                        .from(0, 0, 0)
                        .to(16, 16, 16)
                        .allFaces((dir, uv) -> uv.texture("#overlay"))
                        .end()
                )
                .end();
    }
}
