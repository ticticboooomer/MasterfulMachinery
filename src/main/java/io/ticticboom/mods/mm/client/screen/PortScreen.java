package io.ticticboom.mods.mm.client.screen;

import com.mojang.blaze3d.vertex.PoseStack;
import io.ticticboom.mods.mm.Ref;
import io.ticticboom.mods.mm.block.entity.ControllerBlockEntity;
import io.ticticboom.mods.mm.block.entity.PortBlockEntity;
import io.ticticboom.mods.mm.client.MMClientRegistries;
import io.ticticboom.mods.mm.client.container.PortContainer;
import io.ticticboom.mods.mm.client.port.ClientPortTypeEntry;
import io.ticticboom.mods.mm.util.RenderHelper;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.MenuType;

public class PortScreen extends AbstractContainerScreen<PortContainer> {

    private PortContainer container;
    private ClientPortTypeEntry port;

    public PortScreen(PortContainer p_97741_, Inventory p_97742_, Component p_97743_) {
        super(p_97741_, p_97742_, p_97743_);
        container = p_97741_;
        port = MMClientRegistries.PORTS.get().getValue(p_97741_.be.model().port());
    }

    @Override
    protected void init() {
        this.imageWidth = 175;
        this.imageHeight = 222;
        super.init();
    }

    @Override
    protected void renderBg(PoseStack ms, float p_97788_, int x, int y) {
        this.renderBackground(ms);
        RenderHelper.useTexture(Ref.PORT_GUI);
        blit(ms, leftPos, topPos, 0, 0, 175, 254);
        port.renderScreen(container.be.storage(), this, ms, x, y);
    }

    @Override
    protected void renderLabels(PoseStack p_97808_, int p_97809_, int p_97810_) {

    }
}
