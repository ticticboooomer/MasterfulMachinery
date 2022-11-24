package io.ticticboom.mods.mm.client.port.energy;

import com.mojang.blaze3d.vertex.PoseStack;
import io.ticticboom.mods.mm.Ref;
import io.ticticboom.mods.mm.client.port.ClientPortTypeEntry;
import io.ticticboom.mods.mm.client.screen.PortScreen;
import io.ticticboom.mods.mm.ports.base.PortStorage;
import io.ticticboom.mods.mm.ports.item.ItemPortStorage;
import io.ticticboom.mods.mm.util.RenderHelper;
import net.minecraft.world.phys.Vec2;

public class ItemClientPortTypeEntry extends ClientPortTypeEntry {
    @Override
    public void renderScreen(PortStorage storage, PortScreen screen, PoseStack ms, int mx, int my) {
        var storg = (ItemPortStorage) storage;
        RenderHelper.useTexture(Ref.SLOT_PARTS);
        Vec2 start = storg.getSlotStart();
        for (var x = 0; x < storg.config.slotCols(); x++) {
            for (var y = 0; y < storg.config.slotRows(); y++) {
                screen.blit(ms, screen.getGuiLeft() + (int) start.x + (x * 18) - 1, screen.getGuiTop() + (int) start.y + (y * 18) - 1, 0, 26, 18, 18);
            }
        }
    }
}
