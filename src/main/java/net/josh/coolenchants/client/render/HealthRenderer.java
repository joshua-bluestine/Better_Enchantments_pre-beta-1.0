/*
package net.josh.coolenchants.client.render;

import net.fabricmc.fabric.api.client.rendering.v1.LivingEntityFeatureRendererRegistrationCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.joml.Matrix4f;


public class HealthRenderer<T extends LivingEntity> extends LivingEntityRenderer<T> {

    private final MinecraftClient client;
    private final TextRenderer textRenderer;

    public HealthRenderer(EntityRendererFactory.Context context) {
        super(context);
        this.client = MinecraftClient.getInstance();
        this.textRenderer = client.textRenderer;
    }



    //@Override
    //public void render(T entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
        // Render the mob first
        //renderHealthAboveHead(entity, matrices, vertexConsumers, light);
        //super.render(entity, yaw, tickDelta, matrices, vertexConsumers, light);
        // Render the health text
    //}



    public void renderHealthAboveHead(T entity, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
        // Prepare the health text
        String healthText = String.format("%.1f", entity.getHealth());
        Text text = Text.of(healthText);

        // Push the matrix stack
        matrices.push();

        // Translate to the position above the entity's head
        matrices.translate(0, entity.getHeight() + 0.5, 0);

        // Apply camera rotation
        matrices.multiply(client.gameRenderer.getCamera().getRotation());

        // Scale the text
        matrices.scale(-0.025f, -0.025f, 0.025f);

        // Get the matrix entry
        Matrix4f matrix4f = matrices.peek().getPositionMatrix();

        // Draw the text
        textRenderer.draw(
                text,
                -textRenderer.getWidth(text.getString()) / 2, // Center the text
                0,
                0xFFFFFF, // Text color
                false, // No shadow
                matrix4f,
                vertexConsumers,
                TextRenderer.TextLayerType.NORMAL,
                0x000000, // Background color (black)
                light
        );

        // Pop the matrix stack
        matrices.pop();
    }

    @Override
    public Identifier getTexture(Entity entity) {
        return null;
    }
}
*/