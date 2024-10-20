
package net.josh.coolenchants.client;

import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.josh.coolenchants.CoolEnchants;
import net.josh.coolenchants.IEntityDataSaver;
import net.josh.coolenchants.ModUtils;
import net.josh.coolenchants.data.ThirstData;
import net.josh.coolenchants.enchantment.ModEnchantments;
import net.minecraft.client.MinecraftClient;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.Drawable;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;

public class NecromancerHudOverlay implements HudRenderCallback {
    private static final Identifier FILLED_THIRST = new Identifier(CoolEnchants.MOD_ID,
            "textures/thirst/filled_thirst.png");
    private static final Identifier EMPTY_THIRST = new Identifier(CoolEnchants.MOD_ID,
            "textures/thirst/empty_thirst.png");
    private static int counter = 0;
    private static boolean necro = false;

    @Override
    public void onHudRender(DrawContext matrixStack, float tickDelta) {
        MinecraftClient client = MinecraftClient.getInstance();

        if (necro) {
            counter++;
            if (counter > 50) {
                counter = 0;
                if(ThirstData.getAmount((IEntityDataSaver) client.player) <= 0){
                    necro = true;
                }
            }

                int x = 0;
                int y = 0;
                if (client != null) {
                    int width = client.getWindow().getScaledWidth();
                    int height = client.getWindow().getScaledHeight();

                    x = width / 2;
                    y = height;
                }

                RenderSystem.setShader(GameRenderer::getPositionTexProgram);
                RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
                RenderSystem.setShaderTexture(0, EMPTY_THIRST);
                for (int i = 0; i < ((IEntityDataSaver) ((PlayerEntity) client.player)).getPersistentData().getInt("thirst"); i++) {
                    matrixStack.drawTexture(EMPTY_THIRST, x + 78 - (i * 9), y - 54, 0, 0, 12, 12,
                            12, 12);
                }

                RenderSystem.setShaderTexture(0, FILLED_THIRST);
                for (int i = 0; i < EnchantmentHelper.getLevel(ModEnchantments.NECROMANCY, client.player.getEquippedStack(EquipmentSlot.HEAD)); i++) {
                    if (((IEntityDataSaver) client.player).getPersistentData().getInt("thirst") / 10 >= i
                            && ((IEntityDataSaver) client.player).getPersistentData().getInt("thirst") > 0) {
                        matrixStack.drawTexture(FILLED_THIRST, x + 78 - (i * 9), y - 54, 0, 0, 12, 12,
                                12, 12);
                    } else {
                        break;
                    }

                }
        } else {
            counter++;
            if (counter > 50) {
                counter = 0;
                if(ThirstData.getAmount((IEntityDataSaver) client.player) > 0){
                    necro = true;
                }
            }
        }

    }
}

