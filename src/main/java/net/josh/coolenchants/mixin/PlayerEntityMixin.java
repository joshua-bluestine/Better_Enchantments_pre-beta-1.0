

package net.josh.coolenchants.mixin;

import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.josh.coolenchants.IEntityDataSaver;
import net.josh.coolenchants.ModUtils;
import net.josh.coolenchants.data.*;
import net.josh.coolenchants.enchantment.ModEnchantments;
import net.josh.coolenchants.enchantment.armor.boots.doublejump.ModParticleEffects;
import net.josh.coolenchants.enchantment.armor.helmet.necromancy.NecroDragonEnchantment;
import net.josh.coolenchants.enchantment.sword.LifestealEnchantment;
import net.josh.coolenchants.entity.ModEntities;
import net.josh.coolenchants.entity.custom.NecromancerEntity;
import net.josh.coolenchants.entity.custom.UndeadDragonEntity;
import net.josh.coolenchants.world.dimension.ModDimensions;
import net.kyrptonaught.customportalapi.CustomPortalsMod;
import net.kyrptonaught.customportalapi.interfaces.CustomTeleportingEntity;
import net.kyrptonaught.customportalapi.interfaces.EntityInCustomPortal;
import net.kyrptonaught.customportalapi.util.CustomTeleporter;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.*;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.Angerable;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.*;
import net.minecraft.entity.projectile.thrown.SnowballEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.network.packet.s2c.play.*;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.RegistryKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ChunkTicketType;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.*;
import net.minecraft.world.*;
import net.minecraft.world.biome.source.BiomeAccess;
import net.minecraft.world.border.WorldBorder;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.dimension.DimensionTypes;
import net.minecraft.world.dimension.NetherPortal;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.enchantment.EnchantmentHelper;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.lang.reflect.Field;
import java.util.*;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin {
    /*
astral project (part 1)
astral teleport (part 1)
lightning striker
lightning rod
lifesteal
untouchable
chrono pause pt 1
necromancy
chronomancy
crush
chrono pause pt 2
jump boost (second part is in clientplayerentitymixin)
speed boost
gravity
astral project (part 2)
astral teleport (part 2)
barrage (part two, first part is in bowitemmixin)
wither immune
regen
photosynthesis
swift strike
astral project (part 3)
astral teleport (part 3)
.
.
.
USE A TICKER LIKE WHAT IM DOING TO CHECK ENCHANTMENTS EVERY 100 (or 10 for weapons) TICKS
INSTEAD OF CHECKING THEM EVERY TICK
OH MY LORD IT RAN SO MUCH BETTER
.
.
.
*/

    @Shadow public abstract boolean shouldDamagePlayer(PlayerEntity player);

    boolean astralProject = false;
    boolean astralTeleport = false;
    boolean lifesteal = false;
    boolean crush = false;
    boolean jumpBoost = false; // second part is in ClientPlayerEntityMixin
    boolean gravity = false;
    boolean barrage = false; // part two, first part is in BowItemMixin
    boolean witherImmune = false;
    boolean regen = false;
    boolean photosynthesis = false;
    boolean swiftStrike = false;
    boolean speedBoost = false;
    boolean timeChange = false;
    boolean spleef = false;
    boolean astralBlink = false;
    boolean astralPlane = false;
    boolean astralDimension = false;

    int astralBlinkCooldown = 0;
    int spleefCounter = -1;

    int ticker = 0;
    int ticker2 = 0;
    int astral = 0;
    int photoDelay = 0;
    private static Position chronoPos;
    private static boolean chronoTrigger = false;

    private static boolean necromancer = false;
    private static boolean necroDragon = false;
    private static int necroDragonLevel = 0;
    private static int necroLevel = 0;

    private static boolean wardenShooter = false;

//...........................ASTRAL PROJECT (MORE CODE AFTER THE OTHER ENCHANTMENTS)..............................
    private float scroll = 1.62f;
    private float target = 1.62f;
    private int regenDelay = 0;
    float falling = 0;
    int shooterCooldown = 0;
    private double height = 100000;
    private boolean dissociated = false;
    private boolean dissociatedProject1 = false;
    private boolean dissociatedProject = false;
    private boolean floating = false;
    private boolean astralDataSaved = false;
    ArrayList<Integer> ename = new ArrayList<Integer>();
    Random random = new Random();

    @Inject(method = "damage", at = @At("HEAD"), cancellable = true)
    private void onDamage(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        PlayerEntity player = (PlayerEntity) (Object) this;
//..........................ASTRAL PROJECT && ASTRAL TELEPORT PT 1...........................
        if (source.isOf(DamageTypes.IN_WALL)) {
            if ((astralTeleport) ||
                    (astralProject && player.getPose() == EntityPose.CROUCHING)) {
                cir.setReturnValue(false);
            }
        }
//..........................LIGHTNING STRIKER......................
        if (source.isOf(DamageTypes.LIGHTNING_BOLT)) {
            if (ModUtils.isHoldingEnchantedWeapon(player, ModEnchantments.LIGHTNING_STRIKER)) {
                cir.setReturnValue(false);
            }
        }
//..........................LIGHTNING ROD......................
        if (source.isOf(DamageTypes.LIGHTNING_BOLT)) {
            if (ModUtils.isWearingEnchantedHelmet(player, ModEnchantments.LIGHTNING_ROD)) {
                player.setStatusEffect(new StatusEffectInstance(StatusEffects.STRENGTH,500,2),null);
                cir.setReturnValue(false);
            }
        }
    }


    @Inject(method = "tick", at = @At("HEAD"))
    private void tick(CallbackInfo info) {
        PlayerEntity player = (PlayerEntity) (Object) this;
        World world2 = player.getWorld();
        if (!player.getWorld().isClient()) {
            if (shooterCooldown > 0) {
                shooterCooldown--;
            }
            ticker++;
            ticker2++;
            if (ticker > 100) {
                astralProject = ModUtils.isWearingEnchantedHelmet(player, ModEnchantments.ASTRAL_PROJECT);
                astralTeleport = ModUtils.isWearingEnchantedHelmet(player, ModEnchantments.ASTRAL_TELEPORT);
                ModUtils.chronoPause = ModUtils.isWearingEnchantedHelmet(player, ModEnchantments.CHRONO_PAUSE);
                ModUtils.chronoSave = ModUtils.isWearingEnchantedHelmet(player, ModEnchantments.CHRONOMANCY);
                necromancer = ModUtils.isWearingEnchantedHelmet(player, ModEnchantments.NECROMANCY);
                necroLevel = EnchantmentHelper.getLevel(ModEnchantments.NECROMANCY, player.getEquippedStack(EquipmentSlot.HEAD));
                necroDragon = ModUtils.isWearingEnchantedHelmet(player, ModEnchantments.NECRO_DRAGON);
                necroDragonLevel = EnchantmentHelper.getLevel(ModEnchantments.NECRO_DRAGON, player.getEquippedStack(EquipmentSlot.HEAD));

                ModUtils.challenge = ModUtils.isWearingEnchantedHelmet(player, ModEnchantments.CHALLENGE);

                ModUtils.untouchable = ModUtils.isWearingEnchantedArmor(player, ModEnchantments.UNTOUCHABLE);
                crush = ModUtils.isWearingEnchantedBoots(player, ModEnchantments.CRUSH);
                speedBoost = ModUtils.isWearingEnchantedBoots(player, ModEnchantments.SPEED_BOOST);
                jumpBoost = ModUtils.isWearingEnchantedBoots(player, ModEnchantments.JUMP_BOOST); // second part is in ClientPlayerEntityMixin
                gravity = ModUtils.isWearingEnchantedBoots(player, ModEnchantments.GRAVITY);
                witherImmune = ModUtils.isWearingEnchantedArmor(player, ModEnchantments.WITHER_IMMUNE);
                regen = ModUtils.isWearingEnchantedArmor(player, ModEnchantments.REGEN);
                photosynthesis = ModUtils.isWearingEnchantedArmor(player, ModEnchantments.PHOTO);
                timeChange = ModUtils.isWearingEnchantedArmor(player, ModEnchantments.TIME_CHANGE);
                astralPlane = ModUtils.isWearingEnchantedArmor(player, ModEnchantments.ASTRAL_PLANE);
                ModUtils.astralDimension = (player.getWorld().getRegistryKey() == ModDimensions.ASTRAL_PLANE_LEVEL_KEY);
                System.out.println(astralDimension);
                ModUtils.deflect = ModUtils.isWearingEnchantedArmor(player, ModEnchantments.DEFLECT);
                ModUtils.astralBlink = ModUtils.isWearingEnchantedHelmet(player, ModEnchantments.ASTRAL_BLINK);
                astralBlink = ModUtils.astralBlink;
                ModUtils.astralPlane = ModUtils.isWearingEnchantedHelmet(player, ModEnchantments.ASTRAL_PLANE);
                //ModUtils.dissociatedProject = (ModUtils.isWearingEnchantedHelmet(player, ModEnchantments.ASTRAL_PROJECT) && player.getMainHandStack().isEmpty());
                dissociatedProject1 = (ModUtils.isWearingEnchantedHelmet(player, ModEnchantments.ASTRAL_PROJECT) && player.getMainHandStack().isEmpty());

                ticker = 0;
                if (!necromancer && !necroDragon) {
                    ThirstData.setThirst(((IEntityDataSaver) player), 0);
                    ThirstData.setAmount(((IEntityDataSaver) player), 0);
                    ThirstData.setDragonAmount(((IEntityDataSaver) player), 0);
                }
                if (necromancer) {
                    ThirstData.setAmount(((IEntityDataSaver) player), necroLevel);
                }
                if (necroDragon) {
                    ThirstData.setDragonAmount(((IEntityDataSaver) player), necroDragonLevel);
                }


            }
            if (ticker2 > 10) {
                ModUtils.timeFreeze = ModUtils.isHoldingEnchantedWeapon(player, ModEnchantments.FREEZE);
                ModUtils.snowball = ModUtils.isHoldingEnchantedWeapon(player, ModEnchantments.SNOWBALL);
                ModUtils.fireball = ModUtils.isHoldingEnchantedWeapon(player, ModEnchantments.FIRE_SHOOTER);
                ModUtils.witherSkull = ModUtils.isHoldingEnchantedWeapon(player, ModEnchantments.WITHER_SKULL);
                ModUtils.dragonShooter = ModUtils.isHoldingEnchantedWeapon(player, ModEnchantments.DRAGON_SHOOTER);
                ModUtils.blazeShooter = ModUtils.isHoldingEnchantedWeapon(player, ModEnchantments.BLAZE_SHOOTER);
                ModUtils.goldenApple = ModUtils.isHoldingEnchantedWeapon(player, ModEnchantments.GOLDEN_APPLE);
                barrage = ModUtils.isHoldingEnchantedWeapon(player, ModEnchantments.BARRAGE); // part two, first part is in BowItemMixin
                spleef = ModUtils.isHoldingEnchantedWeapon(player, ModEnchantments.SPLEEF); // part two, first part is in BowItemMixin
                lifesteal = ModUtils.isHoldingEnchantedWeapon(player, ModEnchantments.LIFESTEAL);
                swiftStrike = ModUtils.isHoldingEnchantedWeapon(player, ModEnchantments.SWING_SPEED);
                wardenShooter = ModUtils.isHoldingEnchantedWeapon(player, ModEnchantments.WARDEN_SHOOTER);
                ticker2 = 0;
                //player.sendMessage(Text.literal("GANG"));

            }
            if (dissociatedProject1){
                ModUtils.dissociatedProject = (player.getMainHandStack().isEmpty());
            } else {
                ModUtils.dissociatedProject = false;
            }

            if (astralBlink) {
                ModUtils.astralBlinkCooldown = astralBlinkCooldown;
                EnchantmentData.setBlink(((IEntityDataSaver) player), astralBlink);
                EnchantmentData.setBlinkCooldown(((IEntityDataSaver) player), astralBlinkCooldown);
            }
        } else {

        }


        UseItemCallback.EVENT.register((user, world, hand) -> {
            ItemStack itemStack = user.getStackInHand(hand);
            if (ModUtils.snowball) {
                if (!user.getWorld().isClient()) {
                    world.playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.ENTITY_SNOWBALL_THROW, SoundCategory.NEUTRAL, 0.5f, 0.4f / (world.getRandom().nextFloat() * 0.4f + 0.8f));
                    SnowballEntity snowballEntity = new SnowballEntity(world, user);
                    snowballEntity.setVelocity(user, user.getPitch(), user.getYaw(), 0.0f, 1.5f, 1.0f);
                    world.spawnEntity(snowballEntity);
                    return TypedActionResult.success(itemStack);
                }
            } else if (ModUtils.blazeShooter) {
                if (!user.getWorld().isClient()) {
                    if (shooterCooldown == 0) {
                        shooterCooldown = 20;
                        world.playSound(user.getX() + 0.5, user.getY() + 0.5, user.getZ() + 0.5, SoundEvents.ENTITY_BLAZE_BURN, SoundCategory.HOSTILE, 1.0f + world.random.nextFloat(), world.random.nextFloat() * 0.7f + 0.3f, false);
                        SmallFireballEntity blazeFireBall = new SmallFireballEntity(EntityType.SMALL_FIREBALL, world);
                        blazeFireBall.setVelocity(user, user.getPitch(), user.getYaw(), 1.0f, 3f, 1.0f);
                        blazeFireBall.setPos(user.getX(), user.getY() + 1, user.getZ());
                        world.spawnEntity(blazeFireBall);
                        user.getItemCooldownManager().set(user.getMainHandStack().getItem(), 20);
                        return TypedActionResult.success(itemStack);
                    }
                }
            } else if (ModUtils.dragonShooter) {
                if (!user.getWorld().isClient()) {
                    if (shooterCooldown == 0) {
                        shooterCooldown = 40;
                        world.playSound(user.getX() + 0.5, user.getY() + 0.5, user.getZ() + 0.5, SoundEvents.ENTITY_ENDER_DRAGON_SHOOT, SoundCategory.HOSTILE, 1.0f + world.random.nextFloat(), world.random.nextFloat() * 0.7f + 0.3f, false);
                        DragonFireballEntity dragonFireball = new DragonFireballEntity(EntityType.DRAGON_FIREBALL, world);
                        dragonFireball.setVelocity(user, user.getPitch(), user.getYaw(), 1.0f, 3f, 1.0f);
                        dragonFireball.setPos(user.getX(), user.getY() + 1, user.getZ());
                        world.spawnEntity(dragonFireball);
                        user.getItemCooldownManager().set(user.getMainHandStack().getItem(), 40);
                        return TypedActionResult.success(itemStack);
                    }
                }
            } else if (ModUtils.witherSkull) {
                if (!user.getWorld().isClient()) {
                    if (shooterCooldown == 0) {
                        shooterCooldown = 30;
                        WitherSkullEntity skull = new WitherSkullEntity(EntityType.WITHER_SKULL, world);
                        skull.setVelocity(user, user.getPitch(), user.getYaw(), 1.0f, 3f, 1.0f);
                        skull.setPos(user.getX(), user.getY() + 1, user.getZ());
                        world.spawnEntity(skull);
                        user.getItemCooldownManager().set(user.getMainHandStack().getItem(), 30);
                        return TypedActionResult.success(itemStack);
                    }
                }
            } else if (ModUtils.fireball) {
                if (!user.getWorld().isClient()) {
                    if (shooterCooldown == 0) {
                        shooterCooldown = 30;
                        FireballEntity fireball = new FireballEntity(EntityType.FIREBALL, world);

                        fireball.setVelocity(user, user.getPitch(), user.getYaw(), 1.0f, 3f, 1.0f);
                        fireball.setPos(user.getX(), user.getY() + 1, user.getZ());
                        world.spawnEntity(fireball);

                        user.getItemCooldownManager().set(user.getMainHandStack().getItem(), 30);
                        return TypedActionResult.success(itemStack);
                    }
                }
            } else if (wardenShooter) {
                if (!player.getWorld().isClient()) {


                    if (shooterCooldown == 0) {
                        shooterCooldown = 100;
                        HitResult hitResult = player.raycast(16, 0.0F, false);
                        HitResult hitResult2 = player.raycast(4.5, 0.0F, false);

                        if (ModUtils.safeClick(player, hitResult2, world, true)) {
                            Vec3d vec3d = player.getPos().add(0.0, 1.6f, 0.0);
                            Vec3d vec3d2 = hitResult.getPos().subtract(vec3d);
                            Vec3d vec3d3 = vec3d2.normalize();

                            ServerWorld serverWorld = (ServerWorld) player.getWorld();
                            for (int i = 1; i < MathHelper.floor(vec3d2.length()) + 7; ++i) {
                                Vec3d vec3d4 = vec3d.add(vec3d3.multiply(i));
                                serverWorld.spawnParticles(ParticleTypes.SONIC_BOOM, vec3d4.x, vec3d4.y, vec3d4.z, 1, 0.0, 0.0, 0.0, 0.0);
                            }

                            Vec3d startPos = vec3d;
                            Vec3d endPos = vec3d.add(vec3d3.multiply(vec3d2.length() + 1.0)); // Extend length a bit for the box
                            Box box = new Box(startPos, endPos).expand(0.5); // Expand the box a little to cover a wider area
                            List<Entity> entities = serverWorld.getEntitiesByClass(Entity.class, box, entity -> entity instanceof LivingEntity);
                            for (Entity entity : entities) {
                                if (entity instanceof LivingEntity) {
                                    if (!(entity instanceof PlayerEntity)) {
                                        LivingEntity target2 = (LivingEntity) entity;


                                        for (int i = 1; i < MathHelper.floor(vec3d2.length()) + 7; ++i) {
                                            Vec3d vec3d4 = vec3d.add(vec3d3.multiply(i));
                                            serverWorld.spawnParticles(ParticleTypes.SONIC_BOOM, vec3d4.x, vec3d4.y, vec3d4.z, 1, 0.0, 0.0, 0.0, 0.0);
                                        }

                                        player.playSound(SoundEvents.ENTITY_WARDEN_SONIC_BOOM, 3.0f, 1.0f);
                                        entity.damage(serverWorld.getDamageSources().sonicBoom(player), 10.0f);

                                        double d = 0.5 * (1.0 - target2.getAttributeValue(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE));
                                        double e = 2.5 * (1.0 - target2.getAttributeValue(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE));
                                        entity.addVelocity(vec3d3.getX() * e, vec3d3.getY() * d, vec3d3.getZ() * e);
                                    }
                                }
                            }
                            user.getItemCooldownManager().set(user.getMainHandStack().getItem(), 100);
                            return TypedActionResult.success(itemStack);
                        }
                    }
                }
            }


            return TypedActionResult.pass(itemStack);
        });
        if (MinecraftClient.getInstance().options.useKey.isPressed()) {
            if (player.getMainHandStack().isEmpty()) {
                if (!player.getWorld().isClient()) {
                    if (ModUtils.safeClick(player, player.raycast(4f, 1, false), player.getWorld(), false)) {
//...........................DEFLECT PT 1..................................
                        if (ModUtils.deflect) {
                            if (!player.getWorld().isClient()) {
                                if (ModUtils.deflectCooldown == 0) {
                                    ModUtils.deflect2 = true;
                                    ModUtils.deflectCounter = 100;
                                    ModUtils.deflectCooldown = 400;
                                }
                            }
                        }
//...........................UNTOUCHABLE PT 1..................................
                        if (ModUtils.untouchable) {
                            if (!player.getWorld().isClient()) {

                                if (ModUtils.untouchableCooldown == 0) {
                                    ModUtils.untouchable2 = true;
                                    ModUtils.untouchableCounter = 100;
                                    ModUtils.untouchableCooldown = 400;
                                }
                            }
                        }
//...........................CHRONO PAUSE PT 1................................
                        if (ModUtils.chronoPause) {
                            if (ModUtils.chronoCooldown == 0) {
                                ModUtils.chronoPause2 = true;
                                ModUtils.chronoCounter = 100;
                                ModUtils.chronoCooldown = 500;
                            }
                        }
//.............................ASTRAL BLINK...................................
                        if (ModUtils.astralBlink) {
                            if (astralBlinkCooldown == 0) {

                                Vec3d direction = player.getRotationVector();
                                Vec3d newPosition = player.getPos().add(direction.multiply(5.0));
                                BlockPos pos = new BlockPos((int) newPosition.x, (int) newPosition.y, (int) newPosition.z);
                                if (!world2.getBlockState(pos).isAir()) {
                                    pos = pos.up();
                                    newPosition = newPosition.add(0, 1, 0);
                                }
                                if (!world2.getBlockState(pos).isAir()) {
                                    pos = pos.up();
                                    newPosition = newPosition.add(0, 1, 0);
                                }

                                if (world2.getBlockState(pos).isAir()) {
                                    ServerWorld serverWorld = (ServerWorld) player.getWorld();
                                    Random random = new Random();
                                    for (int i = 1; i < 20; ++i) {
                                        serverWorld.spawnParticles(ParticleTypes.GLOW,
                                                player.getParticleX(1.0D),
                                                player.getY() + 1,
                                                player.getParticleZ(1.0D),
                                                1,
                                                random.nextGaussian() * 0.2D,
                                                random.nextGaussian() * 0.2D,
                                                random.nextGaussian() * 0.2D,
                                                0.0);
                                    }
                                    player.requestTeleport(newPosition.x, newPosition.y, newPosition.z);

                                    if (player.getWorld().getBlockState(player.getBlockPos()).isFullCube(player.getWorld(), player.getBlockPos())) {
                                        player.requestTeleport(player.getX(), player.getY() + 1, player.getZ());
                                    }

                                    for (int i = 1; i < 20; ++i) {
                                        serverWorld.spawnParticles(ParticleTypes.GLOW,
                                                player.getParticleX(1.0D),
                                                player.getY() + 1,
                                                player.getParticleZ(1.0D),
                                                1,
                                                random.nextGaussian() * 0.2D,
                                                random.nextGaussian() * 0.2D,
                                                random.nextGaussian() * 0.2D,
                                                0.0);
                                    }
                                    astralBlinkCooldown = 200;
                                }
                            }
                        }
                    }
                }


//................................TIME CHANGE................................
                if (timeChange) {
                    ServerWorld serverWorld = (ServerWorld) player.getWorld();
                    if (world2.getTimeOfDay() > 13000) {
                        serverWorld.setTimeOfDay(0);
                    } else {
                        serverWorld.setTimeOfDay(13000);
                    }

                }

//...........................NECROMANCY...............................
                if (necromancer) {
                    if (((IEntityDataSaver) player).getPersistentData().getInt("thirst")
                            < EnchantmentHelper.getLevel(ModEnchantments.NECROMANCY, player.getEquippedStack(EquipmentSlot.HEAD))) {
                        NecromancerEntity greg = new NecromancerEntity(ModEntities.NECROMANCER, world2);
                        greg.setPos(player.getX(), player.getY() + 1, player.getZ());

                        greg.setPlayerCreated(true);
                        NecromancerOwnerData.setOwener((IEntityDataSaver) greg, player);
                        world2.spawnEntity(greg);

                        ThirstData.addThirst(((IEntityDataSaver) player), 1);

                    }
                }
//...........................NECRO DRAGON...............................
                if (necroDragon) {
                    if (((IEntityDataSaver) player).getPersistentData().getInt("thirst")
                            < EnchantmentHelper.getLevel(ModEnchantments.NECRO_DRAGON, player.getEquippedStack(EquipmentSlot.HEAD))) {
                        UndeadDragonEntity greg = new UndeadDragonEntity(ModEntities.UNDEAD_DRAGON, world2);
                        greg.setPos(player.getX(), player.getY(), player.getZ());
                        NecromancerOwnerData.setOwener((IEntityDataSaver) greg, player);
                        world2.spawnEntity(greg);

                        ThirstData.addThirst(((IEntityDataSaver) player), 1);
                    }
                }
            }
        }
//...............................ASTRAL PLANE.......................
        if (ModUtils.astralPlane) {
            if (!ModUtils.astralDimension) {
                if (!player.getWorld().isClient()) {

                    World world = player.getWorld();
                    RegistryKey<World> registryKey = world.getRegistryKey() == ModDimensions.ASTRAL_PLANE_LEVEL_KEY ? World.OVERWORLD : ModDimensions.ASTRAL_PLANE_LEVEL_KEY;
                    ServerWorld serverWorld = ((ServerWorld) world).getServer().getWorld(registryKey);

                    AstralPositionData.setY((IEntityDataSaver) player, player.getBlockY());

                    Vec3d currentPosition = player.getPos();
                    Vec3d astralPosition = new Vec3d(currentPosition.x, 0, currentPosition.z);
                    BlockPos portalPos = new BlockPos((int) astralPosition.x, (int) astralPosition.y, (int) astralPosition.z);

                    //Makes particles before teleport so other players see it
                    for (int i = 1; i < 40; ++i) {
                        serverWorld.spawnParticles(ParticleTypes.GLOW,
                                player.getParticleX(1.0D),
                                player.getY() + 1,
                                player.getParticleZ(1.0D),
                                1,
                                random.nextGaussian() * 0.2D,
                                random.nextGaussian() * 0.2D,
                                random.nextGaussian() * 0.2D,
                                0.0);
                    }

                    //ACTUAL TELEPORTER METHOD
                    ModUtils.safeTeleport(serverWorld, portalPos, player);


                    //Makes particles after teleport so you see it
                    for (int i = 1; i < 40; ++i) {
                        serverWorld.spawnParticles(ParticleTypes.GLOW,
                                player.getParticleX(1.0D),
                                player.getY() + 1,
                                player.getParticleZ(1.0D),
                                1,
                                random.nextGaussian() * 0.2D,
                                random.nextGaussian() * 0.2D,
                                random.nextGaussian() * 0.2D,
                                0.0);
                    }
                    ModUtils.astralDimension = true;
                }
                }
        } else {
            if (ModUtils.astralDimension) {
                if (!player.getWorld().isClient()) {
                    World world = player.getWorld();
                    RegistryKey<World> registryKey = world.getRegistryKey() == World.OVERWORLD ? ModDimensions.ASTRAL_PLANE_LEVEL_KEY : World.OVERWORLD;
                    ServerWorld serverWorld = ((ServerWorld) world).getServer().getWorld(registryKey);
                    Vec3d currentPosition = player.getPos();
                    ModUtils.astralY = AstralPositionData.getY((IEntityDataSaver) player);


                    Vec3d astralPosition = new Vec3d(currentPosition.x, ModUtils.astralY, currentPosition.z);
                    BlockPos portalPos = new BlockPos((int) astralPosition.x, ModUtils.astralY, (int) astralPosition.z);
                    System.out.println("2: " + ModUtils.astralY);

                    for (int i = 1; i < 40; ++i) {
                        serverWorld.spawnParticles(ParticleTypes.GLOW,
                                player.getParticleX(1.0D),
                                player.getY() + 1,
                                player.getParticleZ(1.0D),
                                1,
                                random.nextGaussian() * 0.2D,
                                random.nextGaussian() * 0.2D,
                                random.nextGaussian() * 0.2D,
                                0.0);
                    }

                    ModUtils.safeTeleport(serverWorld, portalPos, player);
                    if (player.getWorld().getBlockState(player.getBlockPos().down()).isAir()){
                        for (int i = 1; i < 100; i++){
                            if (!player.getWorld().getBlockState(player.getBlockPos().down(i)).isAir()){
                                player.teleport(player.getX(), player.getBlockPos().down(i).up().getY(), player.getZ());
                                break;
                            }
                            if (!player.getWorld().getBlockState(player.getBlockPos().up(i)).isAir()){
                                player.teleport(player.getX(), player.getBlockPos().up(i).up().getY(), player.getZ());
                                break;
                            }
                        }
                    }

                    for (int i = 1; i < 40; ++i) {
                        serverWorld.spawnParticles(ParticleTypes.GLOW,
                                player.getParticleX(1.0D),
                                player.getY() + 1,
                                player.getParticleZ(1.0D),
                                1,
                                random.nextGaussian() * 0.2D,
                                random.nextGaussian() * 0.2D,
                                random.nextGaussian() * 0.2D,
                                0.0);
                    }

                    astralDataSaved = false;
                    ModUtils.astralDimension = false;
                }
            }
        }

//...............................DEFLECT............................
        if (ModUtils.deflect) {
            if (!player.getWorld().isClient()) {
                if (ModUtils.deflectCounter > 0) {
                    ModUtils.deflectCounter--;
                } else {
                    ModUtils.deflect2 = false;
                }
                if (ModUtils.deflectCooldown > 0) {
                    ModUtils.deflectCooldown--;
                }

                if (ModUtils.deflect2) {
                    Box boundingBox = new Box(
                            player.getX() - 2.0, // minX
                            player.getY() - 2.0, // minY
                            player.getZ() - 2.0, // minZ
                            player.getX() + 2.0, // maxX
                            player.getY() + 2.0, // maxY
                            player.getZ() + 2.0  // maxZ
                    );

                    player.getWorld().getEntitiesByClass(ProjectileEntity.class, boundingBox, e -> true).forEach(projectile -> {
                        if (boundingBox.contains(projectile.getPos())) {
                            Vec3d direction = projectile.getVelocity();
                            Vec3d newDirection = new Vec3d(-direction.x, -direction.y, -direction.z);
                            projectile.setVelocity(newDirection.normalize().multiply(direction.length()));
                        }
                    });

                    for (int i = 1; i < 3; ++i) {
                        ((ServerWorld) player.getWorld()).spawnParticles(ParticleTypes.CLOUD,
                                player.getParticleX(2.0D),
                                player.getY()+player.getWorld().random.nextDouble()*2,
                                player.getParticleZ(2.0D),
                                1,
                                random.nextGaussian() * 0.2D,
                                random.nextGaussian() * 0.2D,
                                random.nextGaussian() * 0.2D,
                                0.0);
                    }
                }
            }
        }
//................................SPLEEF....................................
        if (spleef) {
            if (!player.getWorld().isClient()) {
                if (spleefCounter == -1) {
                    SpleefData.clearAll((IEntityDataSaver) player);
                    spleefCounter = 0;
                }
                if (SpleefData.getSpleef((IEntityDataSaver) player) != null) {
                    spleefCounter++;
                    if (spleefCounter > 50) {
                        System.out.println(SpleefData.getSpleef((IEntityDataSaver) player));

                        List<BlockState> states = SpleefData.getBlockStateList((IEntityDataSaver) player, player);
                        spleefCounter = 0;
                        for (BlockPos pos = SpleefData.getSpleef((IEntityDataSaver) player);
                             pos.getY() > SpleefData.getSpleef((IEntityDataSaver) player).getY() - 50;
                             pos = pos.down()) {
                            if (pos.getY() == SpleefData.getSpleef((IEntityDataSaver) player).getY()) {
                                System.out.println("test05");
                                spleefCounter = 0;
                            }
                            player.getWorld().setBlockState(pos, states.get(spleefCounter));
                            spleefCounter++;
                        }
                        spleefCounter = 0;
                        SpleefData.clearBlockStateList((IEntityDataSaver) player);
                        SpleefData.clearAll((IEntityDataSaver) player);
                    }
                }
            }
        }


//................................LIFESTEAL..................................
        World world = player.getWorld();
        if (LifestealEnchantment.counter == 1) {
            if (LifestealEnchantment.xless) {
                LifestealEnchantment.xv = -(world.random.nextGaussian() * 0.04D);
            } else {
                LifestealEnchantment.xv = world.random.nextGaussian() * 0.04D;
            }
            if (LifestealEnchantment.zless) {
                LifestealEnchantment.zv = -(world.random.nextGaussian() * 0.04D);
            } else {
                LifestealEnchantment.zv = world.random.nextGaussian() * 0.04D;
            }
            int particleCount = (int) 5 * (EnchantmentHelper.getLevel(ModEnchantments.LIFESTEAL, player.getMainHandStack()));

            for (int i = 1; i < (int) 5 * (EnchantmentHelper.getLevel(ModEnchantments.LIFESTEAL, player.getMainHandStack())); ++i) {
                ((ServerWorld) world).spawnParticles(ParticleTypes.HEART,
                        LifestealEnchantment.johnson.getParticleX(1.0D),
                        LifestealEnchantment.johnson.getY() + 1,
                        LifestealEnchantment.johnson.getParticleZ(1.0D),
                        1,
                        LifestealEnchantment.xv,
                        0,
                        LifestealEnchantment.zv,
                        0.0);
            }
            LifestealEnchantment.counter = 0;
        }


//............................UNTOUCHABLE...............................
        if (!player.getWorld().isClient()) {

            if (ModUtils.untouchable) {
                if (ModUtils.untouchableCounter > 0) {
                    ModUtils.untouchableCounter--;
                } else {
                    ModUtils.untouchable2 = false;
                }
                if (ModUtils.untouchableCooldown > 0) {
                    ModUtils.untouchableCooldown--;
                }
                if (ModUtils.untouchable2) {
                    BlockPos playerPos = player.getBlockPos();
                    Vec3d pos1 = new Vec3d(playerPos.getX() - 4, playerPos.getY(), playerPos.getZ() - 4);
                    Vec3d pos2 = new Vec3d(playerPos.getX() + 4, playerPos.getY() + 2, playerPos.getZ() + 4);

                    Box areaBox = new Box(pos1, pos2);
                    ServerWorld worlds = (ServerWorld) player.getWorld();
                    List<Entity> entities = worlds.getOtherEntities(null, areaBox, entity -> entity instanceof LivingEntity && entity != player);
                    for (Entity entity : entities) {

                        //Kept the conditions seperate for readability
                        //"Pushable" just determines whether the mob is chill so no pets die
                        boolean pushable = false;
                        if (entity instanceof MobEntity ||
                                (entity instanceof Angerable && !(entity instanceof TameableEntity))) {
                            pushable = true;
                        }
                        if (entity instanceof TameableEntity) {
                            if (((TameableEntity) entity).getOwner() == null) {
                                pushable = true;
                            }
                        }

                        if (pushable) {
                            double distanceToPlayer = entity.squaredDistanceTo(player.getX(), player.getY(), player.getZ());
                            double minDistanceSquared = 10.0;

                            if (distanceToPlayer < minDistanceSquared) {
                                double dz, dx = 0;
                                if (player.getX() > entity.getX()) {
                                    dx = player.getX() - entity.getX();
                                } else {
                                    dx = entity.getX() - player.getX();
                                }
                                if (player.getZ() > entity.getZ()) {
                                    dz = player.getZ() - entity.getZ();
                                } else {
                                    dz = entity.getZ() - player.getZ();
                                }
                                double angle = Math.atan2(dz, dx);

                                double newX, newZ = 0;
                                if (player.getX() > entity.getX()) {
                                    newX = player.getX() - Math.cos(angle) * Math.sqrt(minDistanceSquared);
                                } else {
                                    newX = player.getX() + Math.cos(angle) * Math.sqrt(minDistanceSquared);
                                }

                                if (player.getZ() > entity.getZ()) {
                                    newZ = player.getZ() - Math.sin(angle) * Math.sqrt(minDistanceSquared);
                                } else {
                                    newZ = player.getZ() + Math.sin(angle) * Math.sqrt(minDistanceSquared);
                                }
                                entity.refreshPositionAndAngles(newX, entity.getY(), newZ, entity.getYaw(), entity.getPitch());
                            }
                        }
                    }
                    for (int i = 1; i < 3; ++i) {
                        ((ServerWorld) player.getWorld()).spawnParticles(ParticleTypes.CLOUD,
                                player.getParticleX(2.0D),
                                player.getY()+player.getWorld().random.nextDouble()*2,
                                player.getParticleZ(2.0D),
                                1,
                                random.nextGaussian() * 0.2D,
                                random.nextGaussian() * 0.2D,
                                random.nextGaussian() * 0.2D,
                                0.0);
                    }
                }
            }


//...........................CHRONO PAUSE PT 2...............................
            if (ModUtils.chronoPause) {
                if (ModUtils.chronoCounter > 0) {
                    ModUtils.chronoCounter--;
                } else {
                    ModUtils.chronoPause2 = false;
                }
                if (ModUtils.chronoCooldown > 0) {
                    ModUtils.chronoCooldown--;
                }
            }
//............................CHRONOMANCY..............................
            if (ModUtils.chronoSave) {
                if (MinecraftClient.getInstance().options.useKey.isPressed() && player.getMainHandStack().isEmpty()) {
                    chronoTrigger = true;
                    if (ModUtils.chronoTicker < 100) {
                        ModParticleEffects.play(
                                player,
                                (ServerWorld) player.getWorld(),
                                null,
                                ParticleTypes.ENCHANT,
                                (int) Math.ceil((float) ModUtils.chronoTicker / 8),
                                20,
                                0,
                                20,
                                0);
                        if (ModUtils.chronoTicker < 0) {
                            ModUtils.chronoTicker = 0;
                        }
                        ModUtils.chronoTicker++;
                    } else {
                        ModParticleEffects.play(
                                player,
                                (ServerWorld) player.getWorld(),
                                null,
                                ParticleTypes.ENCHANT,
                                (int) Math.ceil((float) ModUtils.chronoTicker / 8),
                                20,
                                0,
                                20,
                                0,
                                0);
                    }
                } else {

                    if (chronoTrigger) {
                        if (ModUtils.chronoTicker < 100) {
                            if (chronoPos != null) {
                                ModParticleEffects.play(
                                        player,
                                        (ServerWorld) player.getWorld(),
                                        null,
                                        ParticleTypes.ENCHANT,
                                        60,
                                        20,
                                        0,
                                        20,
                                        0);
                                player.fallDistance = 0;
                                if (player instanceof ServerPlayerEntity && player.hasVehicle()) {
                                    ((ServerPlayerEntity) player).requestTeleportAndDismount(chronoPos.getX(), chronoPos.getY(), chronoPos.getZ());
                                } else {
                                    player.requestTeleport(chronoPos.getX(), chronoPos.getY(), chronoPos.getZ());
                                }
                                ModParticleEffects.play(
                                        player,
                                        (ServerWorld) player.getWorld(),
                                        null,
                                        ParticleTypes.ENCHANT,
                                        60,
                                        20,
                                        0,
                                        20,
                                        0);
                            }
                        } else if (ModUtils.chronoTicker == 100) {
                            ModParticleEffects.play(
                                    player,
                                    (ServerWorld) player.getWorld(),
                                    null,
                                    ParticleTypes.ENCHANT,
                                    (int) Math.ceil((float) ModUtils.chronoTicker / 5),
                                    20,
                                    20,
                                    20,
                                    20,
                                    1);
                            player.sendMessage(Text.literal("time saved"));
                            chronoPos = player.getPos();
                        }
                    }
                    chronoTrigger = false;
                    ModUtils.chronoTicker = 0;

                }

            }


//............................CRUSH.....................................

            if (crush) {
                falling = 1;
                int clevel = EnchantmentHelper.getLevel(ModEnchantments.CRUSH, player.getEquippedStack(EquipmentSlot.FEET));
                BlockPos playerPos = player.getBlockPos();
                BlockPos targetPos = player.getBlockPos();
                Box area = new Box(targetPos.add(-clevel, -1, -clevel), targetPos.add(clevel, 0, clevel));
                List<LivingEntity> entities = ((ServerWorld) player.getWorld()).getEntitiesByClass(LivingEntity.class, area, e -> e != player);

                if (player.fallDistance > 1) {
                    for (LivingEntity entity : entities) {
                        if (!(entity instanceof PlayerEntity) && !(entity instanceof WolfEntity)) {
                            if (!ename.contains(entity.getId())) {
                                ename.add(entity.getId());
                                entity.damage(entity.getDamageSources().magic(), player.fallDistance * ((float) clevel) / 2);
                                //1 kills at 45 blocks, 2x2 box
                                //2 kills at 20 blocks, 4x4 box
                                //3 kills at 15 blocks, 6x6 box

                                falling = 0;
                            }
                        }
                    }
                }

                if (falling == 0) {
                    player.fallDistance = 0;
                }
                if (player.isOnGround()) {
                    if (!ename.isEmpty()) {
                        ename.clear();
                    }
                }

                if (clevel > 1 && falling == 0) {
                    for (int ii = 0; ii < ename.size(); ii++) {

                        ModParticleEffects.play(player, (LivingEntity) player.getWorld().getEntityById(ename.get(ii)), SoundEvents.BLOCK_ANVIL_FALL, ParticleTypes.CRIT, 10, 2, 20, 20, 20);
                    }
                }
            }
        }
//..........................CHRONO PAUSE PART 2.....................
        if (ModUtils.chronoPause2) {
            ModParticleEffects.play(
                    player,
                    null,
                    ParticleTypes.ENCHANT,
                    5,
                    20,
                    0,
                    20,
                    0);
        }



//............................JUMP BOOST............................
        if (jumpBoost) {
            int jumplevel = EnchantmentHelper.getLevel(ModEnchantments.JUMP_BOOST, player.getEquippedStack(EquipmentSlot.FEET));
            StatusEffectInstance blart = new StatusEffectInstance(StatusEffects.JUMP_BOOST, 10, jumplevel, false, false);
            if (jumplevel > 0 && !player.getStatusEffects().contains(blart)) {
                player.setStatusEffect(blart, null);
            }
        }
//............................SPEED BOOST............................
        if (speedBoost) {
            int speedLevel = EnchantmentHelper.getLevel(ModEnchantments.SPEED_BOOST, player.getEquippedStack(EquipmentSlot.FEET));
            StatusEffectInstance blart = new StatusEffectInstance(StatusEffects.SPEED, 10, speedLevel, false, false);
            if (speedLevel > 0 && !player.getStatusEffects().contains(blart)) {
                player.setStatusEffect(blart, null);
            }
        }
//........................GRAVITY.................................
        if (gravity) {
            if (!player.getWorld().isClient()) {
                ServerPlayerEntity serverPlayer = (ServerPlayerEntity) player;
                if (serverPlayer.getWorld().getBlockState(serverPlayer.getBlockPos().down()).isAir() &&
                        serverPlayer.getWorld().getBlockState(serverPlayer.getBlockPos().down().down()).isAir()) {
                    floating = true;
                    if (!MinecraftClient.getInstance().options.sneakKey.isPressed()) {
                        if (height == 100000) {
                            height = serverPlayer.getY();
                        } else {

                                serverPlayer.setNoGravity(true);
                                //player.setPos(player.getX(), height, player.getZ());
                                serverPlayer.setVelocity(player.getVelocity().x, 0, player.getVelocity().z);
                                //serverPlayer.addVelocity(0, -player.getVelocity().y, 0);
                                //System.out.println(player.getVelocity().y);
                                if (serverPlayer.getVelocity().y != 0) {
                                    //serverPlayer.addVelocity(0, -serverPlayer.getVelocity().y, 0);
                                    serverPlayer.requestTeleport(serverPlayer.getX(), height, serverPlayer.getZ());
                                }

                                //player.setPosition(player.getX(), height, player.getZ());

                                serverPlayer.getServerWorld().spawnParticles(ParticleTypes.SOUL_FIRE_FLAME,
                                        player.getParticleX(1.0D),
                                        player.getY(),
                                        player.getParticleZ(1.0D),
                                        1,
                                        random.nextGaussian() * 0.2D,
                                        random.nextGaussian() * 0.2D,
                                        random.nextGaussian() * 0.2D,
                                        0.0);


                                serverPlayer.fallDistance = 0;

                        }
                    } else {
                        player.setNoGravity(false);
                    }
                } else {
                    player.setNoGravity(false);
                    floating = false;
                    height = 100000;
                }
            }
        }
//.........................ASTRAL BLINK, ASTRAL PROJECT && ASTRAL TELEPORT...............................
        if (!world.isClient()){
        if (astralBlinkCooldown > 0) {
            astralBlinkCooldown--;
        }



        if (astralTeleport) {
            if (player.getMainHandStack().isEmpty()) {
                dissociated = true;
                if (player.getWorld().getBlockState(player.getBlockPos().down()).isAir() &&
                        player.getWorld().getBlockState(player.getBlockPos().down().down()).isAir()) {
                    floating = true;
                } else {
                    floating = false;
                }
                if (MinecraftClient.getInstance().options.sneakKey.isPressed() && !floating) {
                    //player.sendMessage(Text.literal("uhhh"));
                    target -= 0.2f;
                } else if (MinecraftClient.getInstance().options.sprintKey.isPressed()) {
                    target += 0.2f;
                }

                if (MinecraftClient.getInstance().mouse.wasRightButtonClicked() && astral == 0 && target != 1.62f) {
                    astral = 1;

                    BlockPos pos = new BlockPos((int) player.getX(), (int) (target - 1.62f + player.getY()), (int) player.getZ());
                    scroll = target;
                    if (player.getWorld().getBlockState(pos).isSolidBlock(player.getWorld(), pos) &&
                            !player.getWorld().getBlockState(pos.up()).isSolidBlock(player.getWorld(), pos.up())) {

                        ModParticleEffects.play(player,
                                null,
                                ParticleTypes.GLOW,
                                40,
                                1,
                                20,
                                20,
                                20);

                        player.setPos(player.getX(), target - 1.62f + player.getY() + 1, player.getZ());
                        ModParticleEffects.play(player,
                                null,
                                ParticleTypes.GLOW,
                                40,
                                1,
                                20,
                                20,
                                20);
                    } else {

                        ModParticleEffects.play(player,
                                null,
                                ParticleTypes.GLOW,
                                40,
                                1,
                                20,
                                20,
                                20);
                        player.setPos(player.getX(), target - 1.62f + player.getY(), player.getZ());
                        ModParticleEffects.play(player,
                                null,
                                ParticleTypes.GLOW,
                                40,
                                1,
                                20,
                                20,
                                20);
                    }


                    scroll = 1.62f;
                    player.calculateDimensions();

                }
                if (MinecraftClient.getInstance().mouse.wasLeftButtonClicked() && astral == 0 && target != 1.62f){
                    astral = 1;
                }
                if (astral == 1) {
                    astral = 0;
                    scroll = 1.62f;
                    target = 1.62f;
                } else {
                    scroll = target;
                }
                player.calculateDimensions();


            } else {
                target = 1.62f;
                scroll = 1.62f;
                dissociated = false;
            }
        } else {
            dissociated = false;
        }



}


//.....................WITHER IMMUNE....................................
            if (witherImmune){
                if (player.hasStatusEffect(StatusEffects.WITHER)){
                    player.removeStatusEffect(StatusEffects.WITHER);
                }
            }
//.....................REGEN......................................
            if (regen) {
                if (getMaxEnchantmentLevel(player) > 0) {
                    if (regenDelay >= (400 / getMaxEnchantmentLevel(player))) {
                        if (player.getHealth() < player.getMaxHealth() && player.getHealth() > 0) {
                            //player.setHealth(player.getHealth() + 1);
                            player.heal(1);
                        }
                        regenDelay = 0;
                    }
                    regenDelay++;
                }
            }
//...........................PHOTO.................................
            if (photosynthesis) {

                if (EnchantmentHelper.getLevel(ModEnchantments.PHOTO, player.getEquippedStack(EquipmentSlot.HEAD)) == 1) {
                    if (player.getWorld().getRegistryKey().getValue().hashCode() == World.OVERWORLD.getValue().hashCode() &&
                            player.getWorld().getTime() < 12000) {
                        if (player.getHungerManager().getFoodLevel() < 20 && photoDelay >= 200) {
                            player.getHungerManager().add(1, 0.3f);
                            photoDelay = 0;
                        }
                        photoDelay++;
                    }
                    } else if (EnchantmentHelper.getLevel(ModEnchantments.PHOTO, player.getEquippedStack(EquipmentSlot.HEAD)) == 2) {
                        if (player.getWorld().getRegistryKey().getValue().hashCode() == World.OVERWORLD.getValue().hashCode()) {
                            if (player.getHungerManager().getFoodLevel() < 20 && photoDelay >= 200) {
                              player.getHungerManager().add(1, 0.3f);
                                photoDelay = 0;
                          }
                            photoDelay++;
                        }
                    } else {
                        if (player.getHungerManager().getFoodLevel() < 20 && photoDelay >= 200) {
                            player.getHungerManager().add(1, 0.3f);
                            photoDelay = 0;
                        }
                    photoDelay++;
                    }
                }

    }
//...........................SWIFT STRIKE (SWING SPEED)..............................
    @Inject(method = "getAttackCooldownProgress", at = @At("HEAD"), cancellable = true)
    private void getAttackCooldownProgress(float baseTime, CallbackInfoReturnable<Float> info) {
        PlayerEntity player = (PlayerEntity)(Object)this;
        if (player.getMainHandStack().getItem() instanceof SwordItem &&
                EnchantmentHelper.getLevel(ModEnchantments.SWING_SPEED, player.getMainHandStack()) > 0) {
            info.setReturnValue(Math.min(1.0f, player.getAttackCooldownProgressPerTick() * baseTime * 1.5f));
        }
    }

    private int getMaxEnchantmentLevel(PlayerEntity player) {
        int maxLevel = 0;
        for (ItemStack stack : player.getArmorItems()) {
            int level = EnchantmentHelper.getLevel(ModEnchantments.REGEN, stack);
            maxLevel += level;
        }
        return maxLevel;
    }
//............................ASTRAL PROJECT && ASTRAL TELEPORT...................
    /**
     * @author
     * @reason
     */
    @Overwrite
    public float getActiveEyeHeight(EntityPose pose, EntityDimensions dimensions) {
        if (dissociated) {
            return scroll;
        } else {
            switch (pose) {
                case SWIMMING:
                case FALL_FLYING:
                case SPIN_ATTACK:
                    return 0.4f;
                case CROUCHING:
                    if (ModUtils.dissociatedProject) {
                        return 20.27f;
                    } else {
                        return 1.27f;
                    }
            }
            return 1.62f;
        }
    }

    @Unique
    private static void handleShooter(EntityType<? extends ProjectileEntity> entityType, PlayerEntity user, World world, int cooldown) {
        if (!world.isClient()) {
            ProjectileEntity projectileEntity = entityType.create(world);
            if (projectileEntity != null) {
                projectileEntity.setOwner(user);
                projectileEntity.setVelocity(user, user.getPitch(), user.getYaw(), 1.0f, 3f, 1.0f);
                projectileEntity.setPos(user.getX(), user.getY() + 1, user.getZ());
                world.spawnEntity(projectileEntity);
            }
            user.getItemCooldownManager().set(user.getMainHandStack().getItem(), cooldown);
        }
    }

}