package io.ticticboom.mods.mm.debug.output;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import io.ticticboom.mods.mm.Ref;
import io.ticticboom.mods.mm.controller.machine.register.MachineControllerBlockEntity;
import io.ticticboom.mods.mm.model.PortModel;
import io.ticticboom.mods.mm.port.MMPortRegistry;
import io.ticticboom.mods.mm.recipe.MachineRecipeManager;
import io.ticticboom.mods.mm.recipe.RecipeModel;
import io.ticticboom.mods.mm.recipe.RecipeStateModel;
import io.ticticboom.mods.mm.recipe.RecipeStorages;
import io.ticticboom.mods.mm.structure.StructureManager;
import io.ticticboom.mods.mm.structure.StructureModel;
import lombok.SneakyThrows;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.fml.loading.FMLPaths;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class DebugOutputManager {

    public static CollectedDebugData collect(Level level, BlockPos pos) {
        CollectedDebugData data = new CollectedDebugData();
        BlockEntity be = level.getBlockEntity(pos);
        if (be instanceof MachineControllerBlockEntity cbe) {
            data.setControllerDef(cbe.getModel());
            data.setPortDefs(MMPortRegistry.getPortModelsForControllerId(Ref.id(cbe.getModel().id())));
            // get structure defs
            var controllerId = Ref.id(data.getControllerDef().id());
            data.setStructureDefs(StructureManager.getStructuresForController(controllerId));

            // get recipe defs
            data.setRecipeDefs(MachineRecipeManager.getRecipeForStructureIds(data.getStructureIds()));

            // run structure form audits
            var storagesMap = new HashMap<ResourceLocation, RecipeStorages>();

            for (StructureModel structureDef : data.structureDefs) {
                JsonObject json = structureDef.debugFormed(level, pos);
                json.addProperty("_path", structureDef.debugPath());
                data.addStructureState(json);
                var storages = structureDef.getStorages(level, pos);
                storagesMap.put(structureDef.id(), storages);
            }

            // run recipe run audits
            for (RecipeModel recipeDef : data.recipeDefs) {
                var storages = storagesMap.getOrDefault(recipeDef.structureId(), null);
                if (storages != null) {
                    JsonObject json = recipeDef.debugRun(level, new RecipeStateModel(), storages);
                    json.addProperty("_path", recipeDef.debugPath());
                    data.addRecipeState(json);
                } else {
                    var json = new JsonObject();
                    json.addProperty("_path", recipeDef.debugPath());
                    json.addProperty("noStorages", true);
                    json.addProperty("_comment", "No Port Storages were stored for this recipe. This could suggest a mismatch in ids or references somewhere.");
                    data.addRecipeState(json);
                }
            }
        }

        data.diags.addProperty("javaVersion", System.getProperty("java.version"));
        data.diags.addProperty("javaVendor", System.getProperty("java.vendor"));
        data.diags.addProperty("osName", System.getProperty("os.name"));
        data.diags.addProperty("osVersion", System.getProperty("os.version"));
        data.diags.addProperty("osArch", System.getProperty("os.arch"));
        data.diags.addProperty("availProcessors", Runtime.getRuntime().availableProcessors());
        data.diags.addProperty("freeMemory", Runtime.getRuntime().freeMemory());
        data.diags.addProperty("maxMemory", Runtime.getRuntime().maxMemory());
        data.diags.addProperty("totalMemory", Runtime.getRuntime().totalMemory());
        return data;
    }

    @SneakyThrows
    public static String output(CollectedDebugData data) {
        var file = getOutputFile();
        var fout = new FileOutputStream(file);
        var zip = new ZipOutputStream(fout);
        var gson = new GsonBuilder().setPrettyPrinting().create();
        for (JsonObject structureState : data.structureStates) {
            zip.putNextEntry(new ZipEntry("structure_states/" + structureState.get("_path").getAsString()));

            String string = gson.toJson(structureState);
            zip.write(string.getBytes(StandardCharsets.UTF_8));

            zip.closeEntry();
        }

        for (JsonObject recipeState : data.getRecipeStates()) {
            zip.putNextEntry(new ZipEntry("recipe_states/" + recipeState.get("_path").getAsString()));

            String string = gson.toJson(recipeState);
            zip.write(string.getBytes(StandardCharsets.UTF_8));

            zip.closeEntry();
        }

        for (StructureModel structureDef : data.structureDefs) {
            zip.putNextEntry(new ZipEntry("structures/" + structureDef.debugPath()));

            String string = gson.toJson(structureDef.getConfig());
            zip.write(string.getBytes(StandardCharsets.UTF_8));

            zip.closeEntry();
        }

        for (RecipeModel recipeDef : data.recipeDefs) {
            zip.putNextEntry(new ZipEntry("recipes/" + recipeDef.debugPath()));

            String string = gson.toJson(recipeDef.config());
            zip.write(string.getBytes(StandardCharsets.UTF_8));

            zip.closeEntry();
        }

        for (PortModel portDef : data.portDefs) {
            zip.putNextEntry(new ZipEntry("ports/" + portDef.id() + ".json"));

            String string = gson.toJson(portDef.jsonConfig());
            zip.write(string.getBytes(StandardCharsets.UTF_8));

            zip.closeEntry();
        }

        // diags
        zip.putNextEntry(new ZipEntry("system-diags.json"));
        zip.write(gson.toJson(data.diags).getBytes(StandardCharsets.UTF_8));
        zip.closeEntry();

        // controller
        zip.putNextEntry(new ZipEntry("controller.json"));
        zip.write(gson.toJson(data.controllerDef.config()).getBytes(StandardCharsets.UTF_8));
        zip.closeEntry();

        zip.close();
        return file.getAbsolutePath();
    }

    @SneakyThrows
    private static File getOutputFile(){
        var dir = FMLPaths.CONFIGDIR.get().resolve("mm/dumps");
        if (!Files.exists(dir)) {
            Files.createDirectories(dir);
        }
        var date = new Date().toInstant().toEpochMilli();
        String fName = "mmdump-" + date + ".zip";
        return dir.resolve(fName).toFile();
    }
}
