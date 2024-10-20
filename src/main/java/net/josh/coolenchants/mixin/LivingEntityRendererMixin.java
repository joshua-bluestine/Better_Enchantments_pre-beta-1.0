package net.josh.coolenchants.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

import net.josh.coolenchants.IEntityDataSaver;
import net.josh.coolenchants.data.EnchantmentData;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(LivingEntityRenderer.class)
public abstract class LivingEntityRendererMixin<T extends LivingEntity> {

//............................SCOUTER.............................
    //livingEntity.client = MinecraftClient.getInstance();

    @Inject(at = @At("HEAD"), method = "render(Lnet/minecraft/entity/LivingEntity;FFLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V")
    private void renderR(T livingEntity, float f, float g,
                         MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider,
                         int i, CallbackInfo info) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (EnchantmentData.getScouter((IEntityDataSaver) client.player)) {



            String healthText = String.format("%.1f", livingEntity.getHealth());
            Text text = Text.of(healthText);

            matrixStack.push();
            matrixStack.translate(0, livingEntity.getHeight() + 0.5, 0);
            matrixStack.multiply(client.gameRenderer.getCamera().getRotation());
            matrixStack.scale(-0.025f, -0.025f, 0.025f);

            Matrix4f matrix4f = matrixStack.peek().getPositionMatrix();
            TextRenderer textRenderer = client.textRenderer;
            textRenderer.draw(
                    text,
                    -textRenderer.getWidth(text.getString()) / 2,
                    0,
                    0xFFFFFF,
                    false,
                    matrix4f,
                    vertexConsumerProvider,
                    TextRenderer.TextLayerType.NORMAL,
                    0x000000,
                    i
            );

            matrixStack.pop();

        }
    }
    }

