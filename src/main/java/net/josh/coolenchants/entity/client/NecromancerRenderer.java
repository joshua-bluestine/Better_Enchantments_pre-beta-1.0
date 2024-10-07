// Example: NecromancerEntityRenderer.java
package net.josh.coolenchants.entity.client.renderer;

import net.josh.coolenchants.entity.custom.NecromancerEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.ZombieEntityRenderer;
import net.minecraft.client.render.entity.model.ZombieEntityModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.util.Identifier;

public class NecromancerRenderer extends MobEntityRenderer<NecromancerEntity>, ZombieEntityModel<ZombieEntity> {


    public NecromancerRenderer(EntityRendererFactory.Context context) {
        super(context, new ZombieEntityModel<>());
    }


    @Override
    public Identifier getTexture(Entity entity) {
        // Return the texture of the zombie
        return new Identifier("minecraft", "textures/entity/zombie/zombie.png");
    }
}