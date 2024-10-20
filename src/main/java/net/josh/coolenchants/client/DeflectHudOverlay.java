package net.josh.coolenchants.client;

import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.josh.coolenchants.CoolEnchants;
import net.josh.coolenchants.ModUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.util.Identifier;

public class DeflectHudOverlay implements HudRenderCallback {
    private static final Identifier FILLED_THIRST = new Identifier(CoolEnchants.MOD_ID,
            "textures/thirst/filled_thirst.png");
    private static final Identifier EMPTY_THIRST = new Identifier(CoolEnchants.MOD_ID,
            "textures/thirst/empty_thirst.png");


    @Override
    public void onHudRender(DrawContext matrixStack, float tickDelta) {
        if (ModUtils.deflect) {
            int x = 0;
            int y = 0;
            MinecraftClient client = MinecraftClient.getInstance();
            if (client != null) {
                int width = client.getWindow().getScaledWidth();
                int height = client.getWindow().getScaledHeight();

                x = width/2;
                y = height;
            }

            RenderSystem.setShader(GameRenderer::getPositionTexProgram);
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
            RenderSystem.setShaderTexture(0, EMPTY_THIRST);
            for (int i = 0; i < 9; i++) {
                matrixStack.drawTexture(EMPTY_THIRST, x + 78 - (i * 9), y - 54, 0, 0, 12, 12,
                        12, 12);
            }

            RenderSystem.setShaderTexture(0, FILLED_THIRST);
            for (int i = 0; i < 9; i++) {
                if (ModUtils.deflectCooldown / 55 >= i && ModUtils.deflectCooldown > 0) {
                    matrixStack.drawTexture(FILLED_THIRST, x + 78 - (i * 9), y - 54, 0, 0, 12, 12,
                            12, 12);
                } else {
                    break;
                }
            }
        }
    }

}