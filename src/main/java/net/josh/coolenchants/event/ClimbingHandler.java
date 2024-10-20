package net.josh.coolenchants.event;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.josh.coolenchants.CoolEnchants;
import net.josh.coolenchants.ModUtils;
import net.josh.coolenchants.enchantment.ModEnchantments;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Position;
import net.minecraft.world.World;

public class ClimbingHandler {
    public static Position counter;
    public static int yes = 0;
    public static int yes2 = 0;
    public static void register() {
        ServerTickEvents.END_SERVER_TICK.register(server -> {
            for (PlayerEntity player : server.getPlayerManager().getPlayerList()) {
                if (player != null && !player.isSpectator()) {
                        checkAndApplyClimbing(player);
                }
            }
        });
    }

    private static void checkAndApplyClimbing(PlayerEntity player) {
        if (ModUtils.isWearingEnchantedBoots(player, ModEnchantments.CLIMB) && !player.getAbilities().creativeMode) {
            if (EnchantmentHelper.getLevel(ModEnchantments.CLIMB,player.getEquippedStack(EquipmentSlot.FEET)) == 2) {
                World world = player.getWorld();
                BlockPos pos = player.getBlockPos();
                Direction facing = player.getHorizontalFacing();
                BlockPos frontPos = pos.offset(facing);
                if (yes == 1) {
                    yes2 = 0;
                    if (!world.getBlockState(pos.offset(Direction.EAST)).isAir() &&
                            world.getBlockState(pos.offset(Direction.EAST)).isFullCube(world, pos.offset(Direction.EAST))) {
                        if (CoolEnchants.jumping) {
                            if (!player.isSneaking()) {
                                player.setVelocity(player.getVelocity().x, 0.2, player.getVelocity().z);
                                player.velocityModified = true;
                                yes2 = 1;
                            } else {
                                player.setVelocity(player.getVelocity().x, -0.2, player.getVelocity().z);
                                player.velocityModified = true;
                                yes2 = 1;
                            }
                        } player.fallDistance = 0;
                    }
                    if (!world.getBlockState(pos.offset(Direction.WEST)).isAir() &&
                            world.getBlockState(pos.offset(Direction.WEST)).isFullCube(world, pos.offset(Direction.WEST))) {
                        if (CoolEnchants.jumping) {
                            if (!player.isSneaking()) {
                                player.setVelocity(player.getVelocity().x, 0.2, player.getVelocity().z);
                                player.velocityModified = true;
                                yes2 = 1;
                            } else {
                                player.setVelocity(player.getVelocity().x, -0.2, player.getVelocity().z);
                                player.velocityModified = true;
                                yes2 = 1;
                            }
                        } player.fallDistance = 0;
                    }
                    if (!world.getBlockState(pos.offset(Direction.NORTH)).isAir() &&
                            world.getBlockState(pos.offset(Direction.NORTH)).isFullCube(world, pos.offset(Direction.NORTH))) {
                        if (CoolEnchants.jumping) {
                            if (!player.isSneaking()) {
                                player.setVelocity(player.getVelocity().x, 0.2, player.getVelocity().z);
                                player.velocityModified = true;
                                yes2 = 1;
                            } else {
                                player.setVelocity(player.getVelocity().x, -0.2, player.getVelocity().z);
                                player.velocityModified = true;
                                yes2 = 1;
                            }
                        } player.fallDistance = 0;
                    }
                    if (!world.getBlockState(pos.offset(Direction.SOUTH)).isAir() &&
                            world.getBlockState(pos.offset(Direction.SOUTH)).isFullCube(world, pos.offset(Direction.SOUTH))) {
                        if (CoolEnchants.jumping) {
                            if (!player.isSneaking()) {
                                player.setVelocity(player.getVelocity().x, 0.2, player.getVelocity().z);
                                player.velocityModified = true;
                                yes2 = 1;
                            } else {
                                player.setVelocity(player.getVelocity().x, -0.2, player.getVelocity().z);
                                player.velocityModified = true;
                                yes2 = 1;
                            }
                        } player.fallDistance = 0;
                    }
                    if (yes2 == 0) {
                        yes = 0;
                    }
                } else {
                    counter = player.getPos();
                    yes = 1;
                }
            } else {
                World world = player.getWorld();
                BlockPos pos = player.getBlockPos();
                Direction facing = player.getHorizontalFacing();
                BlockPos frontPos = pos.offset(facing);
                if (yes == 1) {
                    yes2 = 0;
                    if (!world.getBlockState(pos.offset(Direction.EAST)).isAir() &&
                            world.getBlockState(pos.offset(Direction.EAST)).isFullCube(world, pos.offset(Direction.EAST))) {
                        if (CoolEnchants.jumping && world.getBlockState(pos.offset(Direction.DOWN)).isAir()) {
                            if (!player.isSneaking()) {
                                player.setVelocity(player.getVelocity().x, -0.1, player.getVelocity().z);
                                player.velocityModified = true;
                                yes2 = 1;
                            } else {
                                player.setVelocity(player.getVelocity().x, 0, player.getVelocity().z);
                                player.velocityModified = true;
                                yes2 = 1;
                            }
                        } player.fallDistance = 0;
                    }
                    if (!world.getBlockState(pos.offset(Direction.WEST)).isAir() &&
                            world.getBlockState(pos.offset(Direction.WEST)).isFullCube(world, pos.offset(Direction.WEST))) {
                        if (CoolEnchants.jumping && world.getBlockState(pos.offset(Direction.DOWN)).isAir()) {
                            if (!player.isSneaking()) {
                                player.setVelocity(player.getVelocity().x, -0.1, player.getVelocity().z);
                                player.velocityModified = true;
                                yes2 = 1;
                            } else {
                                player.setVelocity(player.getVelocity().x, 0, player.getVelocity().z);
                                player.velocityModified = true;
                                yes2 = 1;
                            }
                        } player.fallDistance = 0;
                    }
                    if (!world.getBlockState(pos.offset(Direction.NORTH)).isAir() &&
                            world.getBlockState(pos.offset(Direction.NORTH)).isFullCube(world, pos.offset(Direction.NORTH))) {
                        if (CoolEnchants.jumping && world.getBlockState(pos.offset(Direction.DOWN)).isAir()) {
                            if (!player.isSneaking()) {
                                player.setVelocity(player.getVelocity().x, -0.1, player.getVelocity().z);
                                player.velocityModified = true;
                                yes2 = 1;
                            } else {
                                player.setVelocity(player.getVelocity().x, 0, player.getVelocity().z);
                                player.velocityModified = true;
                                yes2 = 1;
                            }
                        } player.fallDistance = 0;
                    }
                    if (!world.getBlockState(pos.offset(Direction.SOUTH)).isAir() &&
                            world.getBlockState(pos.offset(Direction.SOUTH)).isFullCube(world, pos.offset(Direction.SOUTH))) {
                        if (CoolEnchants.jumping && world.getBlockState(pos.offset(Direction.DOWN)).isAir()) {
                            if (!player.isSneaking()) {
                                player.setVelocity(player.getVelocity().x, -0.1, player.getVelocity().z);
                                player.velocityModified = true;
                                yes2 = 1;
                            } else {
                                player.setVelocity(player.getVelocity().x, 0, player.getVelocity().z);
                                player.velocityModified = true;
                                yes2 = 1;
                            }
                        } player.fallDistance = 0;
                    } if (yes2 == 0) {
                        yes = 0;
                    }
                } else {
                    counter = player.getPos();
                    yes = 1;
                }
            }
        }
    }

    private static boolean isMovingTowards(PlayerEntity player, Direction direction, Position pos) {
        if (player.getPos().x > pos.getX() && direction == Direction.EAST) {
            return true;
        } else if (player.getPos().x < pos.getX() && direction == Direction.WEST) {
            return true;
        }
        if (player.getPos().z > pos.getZ() && direction == Direction.SOUTH) {
            return true;
        } else if (player.getPos().z < pos.getZ() && direction == Direction.NORTH) {
            return true;
        }
        return false;
    }
}
