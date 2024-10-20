// Example: NecromancerEntityRenderer.java
package net.josh.coolenchants.entity.client;

import net.josh.coolenchants.CoolEnchants;
import net.josh.coolenchants.entity.custom.NecromancerEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.*;
import net.minecraft.client.render.entity.model.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.util.Identifier;

public class NecromancerRenderer extends MobEntityRenderer<NecromancerEntity, IronGolemEntityModel<NecromancerEntity>> {
    //private final NecromancerModel<NecromancerEntity> model;

    public NecromancerRenderer(EntityRendererFactory.Context context) {
        super(context, new IronGolemEntityModel<>(context.getPart(EntityModelLayers.ZOMBIE)), 1);
        //this.model = new NecromancerModel<>(context.getPart(EntityModelLayers.ZOMBIE));
    }

    @Override
    public Identifier getTexture(NecromancerEntity entity) {
        return new Identifier(CoolEnchants.MOD_ID, "textures/entity/necromancer.png");
    }


    @Override
    public void render(NecromancerEntity mobEntity, float f, float g, MatrixStack matrixStack,
                       VertexConsumerProvider vertexConsumerProvider, int i) {

            matrixStack.scale(1f, 1f, 1f);
        //ZombificationFeatureRenderer.renderModel(mobEntity.(), getTexture(mobEntity), matrixStack, vertexConsumerProvider, i, mobEntity, -1);

        super.render(mobEntity, f, g, matrixStack, vertexConsumerProvider, i);
    }
}