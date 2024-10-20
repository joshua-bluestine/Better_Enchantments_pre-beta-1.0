package net.josh.coolenchants.mixin;

import net.josh.coolenchants.CoolEnchants;
import net.josh.coolenchants.ModUtils;
import net.josh.coolenchants.block.ModBlocks;
import net.josh.coolenchants.enchantment.ModEnchantments;
import net.josh.coolenchants.enchantment.armor.boots.LavaWalkerEnchantment;
import net.josh.coolenchants.world.dimension.ModDimensions;
import net.kyrptonaught.customportalapi.CustomPortalsMod;
import net.kyrptonaught.customportalapi.util.CustomPortalHelper;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.property.Properties;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.*;
import net.minecraft.world.biome.source.BiomeAccess;
import net.minecraft.world.border.WorldBorder;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.dimension.NetherPortal;
import org.apache.logging.log4j.core.jmx.Server;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EquipmentSlot;

import java.util.Objects;
import java.util.UUID;

@Mixin(ServerPlayerEntity.class)
public abstract class ServerPlayerEntityMixin {
/*
lava walker
hard hat
extra health
light
 */


    boolean lavaWalker = false;
    boolean hardHat = false;
    boolean extraHealth = false;
    boolean light = false;
    int ticker = 0;



    @Inject(method = "tick", at = @At("HEAD"))
    private void tick(CallbackInfo info) {
        ServerPlayerEntity player = (ServerPlayerEntity) (Object) this;
        ticker++;
        if (ticker > 100) {
            lavaWalker = ModUtils.isWearingEnchantedBoots(player, ModEnchantments.LAVA_WALKER);
            hardHat = ModUtils.isWearingEnchantedHelmet(player, ModEnchantments.HARD_HAT);
            extraHealth = getMaxEnchantmentLevel(player) > 0;
            light = ModUtils.isWearingEnchantedHelmet(player, ModEnchantments.LIGHT);
            ticker = 0;
        }



//...................LAVA WALKER..........................
        if (lavaWalker) {
            //ModUtils.forceTickCooledLavaBlocks(player, player.getServerWorld());
            LavaWalkerEnchantment.freezeWater(player, player.getWorld(),player.getBlockPos(),
                    EnchantmentHelper.getLevel(ModEnchantments.LAVA_WALKER,player.getEquippedStack(EquipmentSlot.FEET)));
        }
//.....................HARD HAT............................

        if (hardHat){

                BlockPos blockPos = player.getBlockPos();
                if (CoolEnchants.placeholder == null){
                    CoolEnchants.placeholder = blockPos;
                } else if (CoolEnchants.placeholder != blockPos &&
                        player.getWorld().getBlockState(CoolEnchants.placeholder) == ModBlocks.SEE_THROUGH.getDefaultState()){

                    player.getWorld().breakBlock(CoolEnchants.placeholder, false);
                    player.getWorld().setBlockState(CoolEnchants.placeholder, Blocks.AIR.getDefaultState(),
                            Block.NOTIFY_ALL & ~Block.NOTIFY_LISTENERS);
                }


                if (player.getWorld().isAir(blockPos)) {
                    //player.getWorld().setBlockState(blockPos, Blocks.TORCH.getDefaultState());
                    player.getWorld().setBlockState(blockPos, ModBlocks.SEE_THROUGH.getDefaultState());
                    CoolEnchants.placeholder = blockPos;
                }

        } else if (player.getWorld().getBlockState(player.getBlockPos()).equals(ModBlocks.SEE_THROUGH.getDefaultState())){
            player.getWorld().breakBlock(CoolEnchants.placeholder, false);
            player.getWorld().setBlockState(CoolEnchants.placeholder, Blocks.AIR.getDefaultState(),
                    Block.NOTIFY_ALL & ~Block.NOTIFY_LISTENERS);
        }
//...................................EXTRA HEARTS.............................
    if (extraHealth) {
        ModUtils.health = getMaxEnchantmentLevel(player) * 2;
        if (player.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH).getValue() >
                ModUtils.health + player.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH).getBaseValue()) {

            Objects.requireNonNull(player.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH)).clearModifiers();

        } else if (player.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH).getValue() <
                ModUtils.health + player.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH).getBaseValue()) {

            ModUtils.health += (player.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH).getValue() -
                    player.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH).getBaseValue());
            Objects.requireNonNull(player.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH)).clearModifiers();
            Objects.requireNonNull(player.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH)).addPersistentModifier(new EntityAttributeModifier
                    ("Health Boost", ModUtils.health,
                            EntityAttributeModifier.Operation.ADDITION));
        }
    }
//.....................LIGHT............................

        if (light){

            BlockPos blockPos = player.getBlockPos();
            if (CoolEnchants.placeholder == null){
                CoolEnchants.placeholder = blockPos;
            } else if (CoolEnchants.placeholder != blockPos &&
                    player.getWorld().getBlockState(CoolEnchants.placeholder) == ModBlocks.LIGHT_UP_BLOCK.getDefaultState()){

                player.getWorld().breakBlock(CoolEnchants.placeholder, false);
                player.getWorld().setBlockState(CoolEnchants.placeholder, Blocks.AIR.getDefaultState(),
                        Block.NOTIFY_ALL & ~Block.NOTIFY_LISTENERS);
            }


            if (player.getWorld().isAir(blockPos)) {
                //player.getWorld().setBlockState(blockPos, Blocks.TORCH.getDefaultState());
                player.getWorld().setBlockState(blockPos, ModBlocks.LIGHT_UP_BLOCK.getDefaultState());
                CoolEnchants.placeholder = blockPos;
            }

        } else if (player.getWorld().getBlockState(player.getBlockPos()).equals(ModBlocks.LIGHT_UP_BLOCK.getDefaultState())){
            player.getWorld().breakBlock(CoolEnchants.placeholder, false);
            player.getWorld().setBlockState(CoolEnchants.placeholder, Blocks.AIR.getDefaultState(),
                    Block.NOTIFY_ALL & ~Block.NOTIFY_LISTENERS);
        }
    }
    private int getMaxEnchantmentLevel(PlayerEntity player) {
        int maxLevel = 0;
        for (ItemStack stack : player.getArmorItems()) {
            int level = EnchantmentHelper.getLevel(ModEnchantments.EXTRA_HEALTH, stack);
            maxLevel += level;
        }
        return maxLevel;
    }
}
