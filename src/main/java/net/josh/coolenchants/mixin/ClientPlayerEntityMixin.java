package net.josh.coolenchants.mixin;

import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.josh.coolenchants.CoolEnchants;
import net.josh.coolenchants.IEntityDataSaver;
import net.josh.coolenchants.ModUtils;
import net.josh.coolenchants.data.AstralPositionData;
import net.josh.coolenchants.data.EnchantmentData;
import net.josh.coolenchants.enchantment.ModEnchantments;
import net.josh.coolenchants.enchantment.armor.boots.LavaWalkerEnchantment;
import net.josh.coolenchants.enchantment.armor.boots.doublejump.ModParticleEffects;
import net.josh.coolenchants.enchantment.sword.DeathMarkEnchantment;
import net.josh.coolenchants.enchantment.sword.DeathMarkEffect;
import net.minecraft.client.MinecraftClient;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ElytraItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.network.ClientPlayerEntity;

import java.util.List;
import java.util.Objects;
import java.util.Random;

@Mixin(ClientPlayerEntity.class)
public abstract class ClientPlayerEntityMixin {
/*
swift swim
speed boost
lava walker
double jump
jump boost (part 2. first part is in playerentitymixin)
slime boots
death mark
 */
    private boolean swiftSwim = false;
    private boolean speedBoost = false;
    private boolean lavaWalker = false;
    private boolean doubleJump = false;
    private boolean jumpBoost = false; // Part 2 from ClientPlayerEntityMixin should be considered separately
    private boolean slimeBoots = false;
    private boolean deathMark = false;
    private float slimeBootsFall = 0;
    private int ticker = 0;
    int ticker2 = 0;

    private int jumpCount = 0;
    private boolean jumpedLastTick = false;
    private int counter = 0;
    private double yvel = 0;

    private boolean wearingUsableElytra(ClientPlayerEntity player) {
        ItemStack chestItemStack = player.getEquippedStack(EquipmentSlot.CHEST);
        return chestItemStack.getItem() == Items.ELYTRA && ElytraItem.isUsable(chestItemStack);
    }

    private boolean canJump(ClientPlayerEntity player) {
        return !wearingUsableElytra(player) && !player.isFallFlying() && !player.hasVehicle()
                && !player.isTouchingWater() && !player.hasStatusEffect(StatusEffects.LEVITATION);
    }
    @Shadow public abstract boolean damage(DamageSource source, float amount);

    @Shadow protected abstract void applyDamage(DamageSource source, float amount);

    @Inject(method = "tickMovement", at = @At("HEAD"))
    private void tickMovement(CallbackInfo info) {

        ClientPlayerEntity player = (ClientPlayerEntity) (Object) this;

        ticker++;
        ticker2++;
        if (ticker > 100) {
            swiftSwim = ModUtils.isWearingEnchantedBoots(player, ModEnchantments.SWIFT_SWIM);
            speedBoost = ModUtils.isWearingEnchantedBoots(player, ModEnchantments.SPEED_BOOST);
            lavaWalker = ModUtils.isWearingEnchantedBoots(player, ModEnchantments.LAVA_WALKER);
            doubleJump = ModUtils.isWearingEnchantedBoots(player, ModEnchantments.DOUBLE_JUMP); // Assuming Double Jump is on boots
            jumpBoost = ModUtils.isWearingEnchantedBoots(player, ModEnchantments.JUMP_BOOST); // Part 2 needs client-side check
            slimeBoots = ModUtils.isWearingEnchantedBoots(player, ModEnchantments.SLIME_BOOTS);
            EnchantmentData.setScouter((IEntityDataSaver) player, ModUtils.isWearingEnchantedHelmet(player, ModEnchantments.SCOUTER));
            ticker = 0;

        }
        if (ticker2 > 10){

            EnchantmentData.setScope((IEntityDataSaver) player, ModUtils.isHoldingEnchantedWeapon(player, ModEnchantments.SCOPE));
            deathMark = ModUtils.isHoldingEnchantedWeapon(player, ModEnchantments.DEATH_MARK);
            ticker2 = 0;
        }

        EntityAttributeModifier SPEED;
        EntityAttributeModifier ATTACK_SPEED;
        EntityAttributeModifier SWIM = new EntityAttributeModifier
                ("Swim Boost", ModUtils.mining_speed,
                        EntityAttributeModifier.Operation.ADDITION);

//.....................important section that every jump-based enchantment uses................
        if (player.input.jumping && !player.getAbilities().flying) {
            CoolEnchants.jumping = true;
        } else {
            CoolEnchants.jumping = false;
        }


//........................SWIFT SWIM..............................
        if (swiftSwim) {
            if (player.isSwimming()) {

                double level = EnchantmentHelper.getLevel(ModEnchantments.SWIFT_SWIM, player.getEquippedStack(EquipmentSlot.HEAD));
                double speedIncrease = 1 + (0.03 * level);


                player.setVelocity(
                        player.getVelocity().x * speedIncrease,
                        player.getVelocity().y * speedIncrease,
                        player.getVelocity().z * speedIncrease
                );
            }
        }
//..............................SPEED BOOST............................
        /*
        if (speedBoost){
            if(Objects.requireNonNull(
                    player.getAttributeInstance(
                            EntityAttributes.GENERIC_MOVEMENT_SPEED)).getValue() ==
                    Objects.requireNonNull(player.getAttributeInstance(
                            EntityAttributes.GENERIC_MOVEMENT_SPEED)).getBaseValue()) {

                ModUtils.speed = 0.1*EnchantmentHelper.getLevel(ModEnchantments.SPEED_BOOST,player.getEquippedStack(EquipmentSlot.FEET));
                SPEED = new EntityAttributeModifier
                        ("Speed Boost", ModUtils.speed,
                                EntityAttributeModifier.Operation.ADDITION);

                Objects.requireNonNull(player.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED)).addPersistentModifier(SPEED);
            } else if (player.isSprinting() && ModUtils.isWearingEnchantedBoots(
                    player, ModEnchantments.SPEED_BOOST)){
                if (ModUtils.tester == 0) {
                    ModUtils.speed = 0.1 * EnchantmentHelper.getLevel(ModEnchantments.SPEED_BOOST, player.getEquippedStack(EquipmentSlot.FEET));
                    SPEED = new EntityAttributeModifier
                            ("Speed Boost", ModUtils.speed,
                                    EntityAttributeModifier.Operation.ADDITION);

                    Objects.requireNonNull(player.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED)).addPersistentModifier(SPEED);
                    ModUtils.tester = 1;
                }
            } else if (!player.isSprinting() && ModUtils.isWearingEnchantedBoots(
                    player, ModEnchantments.SPEED_BOOST) && ModUtils.tester == 1) {
                ModUtils.tester = 0;
            } else if (!ModUtils.isWearingEnchantedBoots(player,ModEnchantments.SPEED_BOOST) &&
                    Objects.requireNonNull(player.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED)).getValue() >
                            Objects.requireNonNull(player.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED)).getBaseValue() &&
                    !player.isSprinting()) {
                SPEED = new EntityAttributeModifier
                        ("Speed Boost", -ModUtils.speed,
                                EntityAttributeModifier.Operation.ADDITION);
                Objects.requireNonNull(player.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED)).addPersistentModifier(SPEED);
            }
        }
*/

//....................DOUBLE JUMP..................
        if (doubleJump || jumpBoost) {
            if (player.isOnGround() || player.isClimbing()) {

                jumpCount = EnchantmentHelper.getEquipmentLevel(ModEnchantments.DOUBLE_JUMP, player);
            } else if (!jumpedLastTick && jumpCount > 0 && player.getVelocity().y < 0) {
                if (player.input.jumping && !player.getAbilities().flying) {
                    if (canJump(player)) {
                        --jumpCount;
                        player.jump();
                        Random random = new Random();
                        ModParticleEffects.play(player, SoundEvents.ENTITY_RABBIT_JUMP, ParticleTypes.CLOUD, 5, 0, random.nextGaussian() * 0.02D, random.nextGaussian() * 0.02D, random.nextGaussian() * 0.02D);

//.............................JUMP BOOST (PART TWO)..........................
                        int john = EnchantmentHelper.getLevel(ModEnchantments.JUMP_BOOST, player.getEquippedStack(EquipmentSlot.FEET));
                        player.addVelocity((player.getVelocity().x / 5) * john, (player.getVelocity().x / 5) * john, (player.getVelocity().z / 5) * john);
                    }
                }
            }
        }
        jumpedLastTick = player.input.jumping;
//...................SLIME BOOTS.................................
        if (slimeBoots) {
            if (!MinecraftClient.getInstance().options.sneakKey.isPressed()) {
                if ((player.getWorld().getBlockState(player.getBlockPos().down()).
                        isFullCube(player.getWorld(), player.getBlockPos().down()) &&
                        !player.isOnGround() &&
                        counter == 0 && player.getVelocity().y < -0.5 && player.getVelocity().y > -1) ||
                        (player.getWorld().getBlockState(player.getBlockPos().down().down()).
                                isFullCube(player.getWorld(), player.getBlockPos().down().down()) &&
                                !player.isOnGround() &&
                                counter == 0 && player.getVelocity().y < -1 && player.getVelocity().y > -2) ||
                        (player.getWorld().getBlockState(player.getBlockPos().down().down().down()).
                                isFullCube(player.getWorld(), player.getBlockPos().down().down().down()) &&
                                !player.isOnGround() &&
                                counter == 0 && player.getVelocity().y < -2 && player.getVelocity().y > -3) ||
                        (player.getWorld().getBlockState(player.getBlockPos().down().down().down().down()).
                                isFullCube(player.getWorld(), player.getBlockPos().down().down().down().down()) &&
                                !player.isOnGround() &&
                                counter == 0 && player.getVelocity().y < -3 && player.getVelocity().y > -4)) {
                    counter = 1;
                    if (slimeBootsFall < player.fallDistance){
                        slimeBootsFall = player.fallDistance;
                    }
                    yvel = player.getVelocity().y;

                }
                if (player.isOnGround() && counter == 1) {
                    if (slimeBootsFall > 3) {
                        player.addVelocity(0, yvel * (-0.85), 0);
                        Random random = new Random();
                        ModParticleEffects.play(player, SoundEvents.BLOCK_SLIME_BLOCK_FALL, ParticleTypes.ITEM_SLIME, 20,0,random.nextGaussian() * 0.02D, random.nextGaussian() * 0.02D, random.nextGaussian() * 0.02D);
                    }
                    counter = 0;
                    slimeBootsFall = 0;
                }
            }
        }
//............................DEATH MARK....................................
        if (deathMark) {
            if (DeathMarkEnchantment.duration > 0 && DeathMarkEnchantment.michael.getType() != EntityType.ENDER_DRAGON &&
                    DeathMarkEnchantment.michael.getType() != EntityType.WARDEN &&
                    DeathMarkEnchantment.michael.getType() != EntityType.ELDER_GUARDIAN &&
                    DeathMarkEnchantment.michael.getType() != EntityType.WITHER) {
                DeathMarkEnchantment.duration -= 1;
                if (((LivingEntity) DeathMarkEnchantment.michael).getHealth() <= 0) {
                    DeathMarkEnchantment.duration = 0;
                }
                if (DeathMarkEnchantment.duration == 0) {
                    DeathMarkEffect.play((LivingEntity) DeathMarkEnchantment.michael, player);
                    PacketByteBuf passedData = new PacketByteBuf(Unpooled.buffer());
                    passedData.writeUuid(player.getUuid());
                    //ClientPlayNetworking.send(passedData);

                    DeathMarkEnchantment.onDeath(DeathMarkEnchantment.michael);
                }
            }
        }
    }
}