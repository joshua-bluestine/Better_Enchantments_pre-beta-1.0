/*
package net.josh.coolenchants.entity.client;

import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.HostileEntity;
/*
public class NecromancerModel<T extends LivingEntity> extends BipedEntityModel<T> {
    /*
    private final ModelPart necromancer;
    private final ModelPart head;
    public NecromancerModel(ModelPart root) {
        super(root);
        this.necromancer = root.getChild(EntityModelPartNames.BODY);
        this.head = root.getChild(EntityModelPartNames.HEAD);
    }


    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData necromancer = modelPartData.addChild("necromancer", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 24.0F, 0.0F));

        ModelPartData body = modelPartData.getChild(EntityModelPartNames.BODY);

        ModelPartData head =  modelPartData.getChild(EntityModelPartNames.HEAD);
        return TexturedModelData.of(modelData, 64, 64);
    }
    @Override
    public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
        necromancer.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
    }

    public ModelPart getPart() {
        return necromancer;
    }

    @Override
    public ModelPart getHead() {
        return this.head;
    }



}

*/
