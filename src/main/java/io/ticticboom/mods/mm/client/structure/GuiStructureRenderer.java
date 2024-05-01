package io.ticticboom.mods.mm.client.structure;

import io.ticticboom.mods.mm.client.AutoTransform;
import io.ticticboom.mods.mm.structure.StructureModel;
import net.minecraft.client.gui.GuiGraphics;

import java.util.List;

public class GuiStructureRenderer {
    private final StructureModel model;
    private final List<PositionedCyclingBlockRenderer> parts;
    private final GuiStructureLayout guiLayout;

    private AutoTransform mouseTransform;


    public GuiStructureRenderer(StructureModel model) {
        this.model = model;
        mouseTransform = new AutoTransform(model);
        guiLayout = new GuiStructureLayout(model.layout());
        parts = guiLayout.createBlockRenderers();
        parts.add(model.controllerUiRenderer());
        for (PositionedCyclingBlockRenderer part : parts) {
            part.part.setInterval(30);
        }
    }

    public void render(GuiGraphics gfx, int mouseX, int mouseY) {
        mouseTransform.run(mouseX, mouseY);
        for (PositionedCyclingBlockRenderer part : parts) {
            part.part.tick();
            GuiBlockRenderer next = part.part.next();
            next.render(gfx, mouseX, mouseY, mouseTransform);
        }
    }

    public void resetTransforms() {
        mouseTransform.reset();
    }

}