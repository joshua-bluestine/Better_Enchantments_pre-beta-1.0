package net.josh.coolenchants;
import net.josh.coolenchants.world.dimension.ModDimensions;
import net.minecraft.block.*;
import net.minecraft.client.MinecraftClient;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.*;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.packet.s2c.play.PositionFlag;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.Set;

public class ModUtils {
    public static double tester = 0;
    public static double speed = 0;
    public static double attack_speed = 0;
    public static double scroll_amount = 0;
    public static double health = 0;
    public static double mining_speed = 0;
    public static boolean activepearl = false;
    public static boolean launching = false;
    public static double launchSpeed = 0;
    public static double launchCounter = 0;

    public static boolean chronoPause = false;
    public static boolean chronoSave = false;
    public static boolean chronoPause2 = false;
    public static int chronoCounter = 0;
    public static int chronoCooldown = 0;
    public static int chronoTicker = 0;
    public static boolean challenge = false;
    public static int challengeLevel = 0;
    public static boolean deflect = false;
    public static boolean deflect2 = false;
    public static int deflectCounter = 0;
    public static int deflectCooldown = 0;
    public static boolean untouchable = false;
    public static boolean untouchable2 = false;
    public static int untouchableCounter = 0;
    public static int untouchableCooldown = 0;
    public static boolean astralBlink = false;
    public static boolean astralDimension = false;
    public static boolean dissociatedProject = false;
    public static int astralBlinkCooldown = 0;
    public static String removedEntity = "";

    public static boolean timeChange = false;
    public static boolean necromancer = false;
    public static boolean necroDragon = false;

    public static int astralY = 0;
    public static boolean astralPlane = false;
    public static boolean readyToTeleport = false;


    public static boolean goldenApple = false;
    public static boolean timeFreeze = false;
    public static boolean launch = false;
    public static boolean gravity = false;
    public static boolean floating = false;
    public static boolean fireball = false, snowball = false, witherSkull = false, dragonShooter = false, blazeShooter = false;
    public static double wolfhealth = 0;
    public static boolean bowshot = false;
    public static Item[] hoes = {Items.DIAMOND_HOE, Items.GOLDEN_HOE, Items.IRON_HOE, Items.NETHERITE_HOE, Items.STONE_HOE, Items.WOODEN_HOE};
    public static void print(PlayerEntity player, String str){
        player.sendMessage(Text.literal(str));
    }
    public static void print(PlayerEntity player, int str){
        player.sendMessage(Text.literal(String.valueOf(str)));
    }
    public static void print(PlayerEntity player, double str){
        player.sendMessage(Text.literal(String.valueOf(str)));
    }

    public static void safeTeleport(ServerWorld world, BlockPos destination, Entity player) {
        var actualDestination = findSafeSpot(world, destination);
        if (actualDestination == null) return;
        double scaledX = 0;
        double scaledZ = 0;
        if (world.getDimensionKey().equals(ModDimensions.ASTRAL_PLANE_DIM_TYPE)){
            scaledX = (int) ((actualDestination.getX() + 0.5) / 16.0);
            scaledZ = (int) ((actualDestination.getZ() + 0.5) / 16.0);
        } else {
            scaledX = (int) ((actualDestination.getX() + 0.5) * 16.0);
            scaledZ = (int) ((actualDestination.getZ() + 0.5) * 16.0);
        }

        if (scaledX > 29999984) {
            scaledX = 29999980;
        }
        if (scaledX < -29999984){
            scaledX = -29999980;
        }
        if (scaledZ > 29999984){
            scaledZ = 29999980;
        }
        if (scaledZ < -29999984){
            scaledZ = -29999980;
        }

        Vec3d lookVector = player.getRotationVector();
        double horzDistance = Math.sqrt(lookVector.x * lookVector.x + lookVector.z * lookVector.z);
        float yaw = (float) Math.toDegrees(Math.atan2(-lookVector.x, lookVector.z));
        float pitch = (float) Math.toDegrees(Math.atan2(-lookVector.y, horzDistance));

        player.teleport(
                world,
                scaledX + 0.5,
                (double) actualDestination.getY(),
                scaledZ + 0.5,
                Set.of(PositionFlag.X, PositionFlag.Y, PositionFlag.Z),
                yaw,
                pitch
        );

        /*
        Since the y position from the overworld gets saved...
        ...as nbt data from the last tp to the astral plane...
        ..., when tp-ing back to the overworld from the astral plane, this tries to keep the original y...
        ...but if it's not safe to tp to with the new x and z, it finds a new safe one
         */

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
    }
    public static BlockPos findSafeSpot(ServerWorld world, BlockPos pos) {


        for (int y = pos.getY(); y < world.getTopY(); y++) {
            BlockPos checkPos = new BlockPos(pos.getX(), y, pos.getZ());
            if (isSafeSpot(world, checkPos)) {
                return checkPos;
            }
        }
        return null;
    }

    public static boolean isSafeSpot(World world, BlockPos pos) {
        BlockState state = world.getBlockState(pos);
        return state.isAir() || !state.isOpaque();
    }

    public static EntityHitResult raycastEntity(PlayerEntity player, double maxDistance) {
        Entity cameraEntity = MinecraftClient.getInstance().cameraEntity;
        if (cameraEntity != null) {
            Vec3d cameraPos = player.getCameraPosVec(1.0f);
            Vec3d rot = player.getRotationVec(1.0f);
            Vec3d rayCastContext = cameraPos.add(rot.x * maxDistance, rot.y * maxDistance, rot.z * maxDistance);
            Box box = cameraEntity.getBoundingBox().stretch(rot.multiply(maxDistance)).expand(1d, 1d, 1d);
            return ProjectileUtil.raycast(cameraEntity, cameraPos, rayCastContext, box, (entity -> !entity.isSpectator() && entity.canHit()), maxDistance);
        }
        return null;
    }
    public static boolean safeClick(PlayerEntity player, HitResult hitResult, World world, boolean weapon){

        /*
        Since the built-in item use method has a LOT of issues, including only running client-side...
        ...and not working with an empty hand, this is a custom method that checks...
        ...to make sure the right-click is valid and not going to cause two actions at once
         */


        if (player.getMainHandStack().isEmpty()) {
            if (hitResult.getType() == HitResult.Type.BLOCK) {
                BlockHitResult blockHitResult = (BlockHitResult) hitResult;
                BlockPos blockPos = blockHitResult.getBlockPos();
                Block block = world.getBlockState(blockPos).getBlock();
                if (block instanceof DoorBlock ||
                    block instanceof FenceGateBlock){
                    return false;
                }
                if (world.getBlockEntity(blockPos) != null){
                    return false;
                }
            }
            if (hitResult.getType() == HitResult.Type.ENTITY){
                EntityHitResult entityHitResult = (EntityHitResult) hitResult;
                if ((entityHitResult.getEntity() instanceof TameableEntity && ((TameableEntity) entityHitResult.getEntity()).isTamed()) ||
                entityHitResult.getEntity() instanceof InteractionObserver ||
                entityHitResult.getEntity() instanceof Saddleable){
                    return false;
                }
            }
            return true;
        } else {
            if (!weapon) {
                return false;
            } else {
                if (hitResult.getType() == HitResult.Type.BLOCK) {
                    BlockHitResult blockHitResult = (BlockHitResult) hitResult;
                    BlockPos blockPos = blockHitResult.getBlockPos();
                    Block block = world.getBlockState(blockPos).getBlock();
                    if (block instanceof DoorBlock ||
                            block instanceof FenceGateBlock){
                        return false;
                    }
                    if (world.getBlockEntity(blockPos) != null){
                        return false;
                    }
                }
                if (hitResult.getType() == HitResult.Type.ENTITY){
                    EntityHitResult entityHitResult = (EntityHitResult) hitResult;
                    if ((entityHitResult.getEntity() instanceof TameableEntity && ((TameableEntity) entityHitResult.getEntity()).isTamed()) ||
                            entityHitResult.getEntity() instanceof InteractionObserver ||
                            entityHitResult.getEntity() instanceof Saddleable){
                        return false;
                    }
                }
                return true;
            }
        }
    }
    public static boolean inInventory(DefaultedList<ItemStack> mainInventory, Enchantment enchantment, Item... shit) {
        for (Item hoe : shit) {
            for (ItemStack stack : mainInventory) {
                if (stack.getItem() == hoe && EnchantmentHelper.getLevel(enchantment, stack) > 0) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean isHoldingEnchantedWeapon(PlayerEntity player, Enchantment enchantment) {
        return !player.getEquippedStack(EquipmentSlot.MAINHAND).isEmpty()
                && EnchantmentHelper.getLevel
                (enchantment, player.getEquippedStack(EquipmentSlot.MAINHAND)) > 0;
    }
    public static boolean isHoldingEnchantedWeaponInEitherHand(PlayerEntity player, Enchantment enchantment) {
        return (!player.getEquippedStack(EquipmentSlot.MAINHAND).isEmpty()
                && EnchantmentHelper.getLevel
                (enchantment, player.getEquippedStack(EquipmentSlot.MAINHAND)) > 0) ||
                (!player.getEquippedStack(EquipmentSlot.OFFHAND).isEmpty()
                && EnchantmentHelper.getLevel
                (enchantment, player.getEquippedStack(EquipmentSlot.OFFHAND)) > 0);
    }


    public static boolean isWearingEnchantedBoots(PlayerEntity player, Enchantment enchantment) {
        return !player.getEquippedStack(EquipmentSlot.FEET).isEmpty()
                && EnchantmentHelper.getLevel
                (enchantment, player.getEquippedStack(EquipmentSlot.FEET)) > 0;
    }
    public static boolean isWearingEnchantedHelmet(PlayerEntity player, Enchantment enchantment) {
        return !player.getEquippedStack(EquipmentSlot.HEAD).isEmpty()
                && EnchantmentHelper.getLevel
                (enchantment, player.getEquippedStack(EquipmentSlot.HEAD)) > 0;
    }
    public static boolean isWearingEnchantedArmor(PlayerEntity player, Enchantment enchantment) {
        PlayerInventory inventory = player.getInventory();
        for (ItemStack stack : inventory.armor) {
            if (stack != null && stack.hasEnchantments() &&
                    EnchantmentHelper.getLevel(enchantment, stack) > 0) {
                return true;
            }
        }
        return false;
    }
}