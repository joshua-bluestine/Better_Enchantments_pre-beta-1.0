package net.josh.coolenchants.data;

import net.josh.coolenchants.IEntityDataSaver;
import net.minecraft.nbt.NbtCompound;

public class WolfData {


    public static int getHealth(IEntityDataSaver player) {
        return player.getPersistentData().getInt("wolf_health_level");
    }
    public static int setHealth(IEntityDataSaver player, int amount) {
        NbtCompound nbt = player.getPersistentData();
        nbt.putInt("wolf_health_level", amount);
        return amount;
    }
    public static int getCurrentHealth(IEntityDataSaver player) {
        return player.getPersistentData().getInt("wolf_health");
    }
    public static int setCurrentHealth(IEntityDataSaver player, int amount) {
        NbtCompound nbt = player.getPersistentData();
        nbt.putInt("wolf_health", amount);
        return amount;
    }

    public static int getAttack(IEntityDataSaver player) {
        return player.getPersistentData().getInt("wolf_attack_level");
    }
    public static int setAttack(IEntityDataSaver player, int amount) {
        NbtCompound nbt = player.getPersistentData();
        nbt.putInt("wolf_attack_level", amount);
        return amount;
    }
    public static int addHealth(IEntityDataSaver player, int amount) {
        NbtCompound nbt = player.getPersistentData();
        int thirst = nbt.getInt("wolf_health_level");

        thirst += amount;


        nbt.putInt("wolf_health_level", thirst);
        return thirst;
    }

    public static int removeHealth(IEntityDataSaver player, int amount) {
        NbtCompound nbt = player.getPersistentData();
        int thirst = nbt.getInt("wolf_health_level");
        if(thirst - amount < 0) {
            thirst = 0;
        } else {
            thirst -= amount;
        }

        nbt.putInt("wolf_health_level", thirst);
        // syncThirst(thirst, (ServerPlayerEntity) player);
        return thirst;
    }
    public static int addAttack(IEntityDataSaver player, int amount) {
        NbtCompound nbt = player.getPersistentData();
        int thirst = nbt.getInt("wolf_attack_level");
        thirst += amount;


        nbt.putInt("wolf_attack_level", thirst);
        return thirst;
    }

    public static int removeAttack(IEntityDataSaver player, int amount) {
        NbtCompound nbt = player.getPersistentData();
        int thirst = nbt.getInt("wolf_attack_level");
        if(thirst - amount < 0) {
            thirst = 0;
        } else {
            thirst -= amount;
        }

        nbt.putInt("wolf_attack_level", thirst);
        // syncThirst(thirst, (ServerPlayerEntity) player);
        return thirst;
    }


}