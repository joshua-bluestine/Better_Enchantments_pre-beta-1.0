package net.josh.coolenchants.data;

import net.josh.coolenchants.IEntityDataSaver;
import net.minecraft.nbt.NbtCompound;

public class ThirstData {

    public static int setAmount(IEntityDataSaver player, int amount) {
        NbtCompound nbt = player.getPersistentData();
        nbt.putInt("necro_amount", amount);
        return amount;
    }
    public static int getAmount(IEntityDataSaver player) {
        return player.getPersistentData().getInt("necro_amount");
    }
    public static int setDragonAmount(IEntityDataSaver player, int amount) {
        NbtCompound nbt = player.getPersistentData();
        nbt.putInt("necro_dragon_amount", amount);
        return amount;
    }
    public static int getDragonAmount(IEntityDataSaver player) {
        return player.getPersistentData().getInt("necro_dragon_amount");
    }

    public static int addThirst(IEntityDataSaver player, int amount) {
        NbtCompound nbt = player.getPersistentData();
        int thirst = nbt.getInt("thirst");
        if(thirst + amount >= 10) {
            thirst = 10;
        } else {
            thirst += amount;
        }

        nbt.putInt("thirst", thirst);
        return thirst;
    }
    public static int setThirst(IEntityDataSaver player, int amount) {
        NbtCompound nbt = player.getPersistentData();
        nbt.putInt("thirst", amount);
        return amount;
    }
    public static int getThirst(IEntityDataSaver player) {
        return player.getPersistentData().getInt("thirst");
    }

    public static int removeThirst(IEntityDataSaver player, int amount) {
        NbtCompound nbt = player.getPersistentData();
        int thirst = nbt.getInt("thirst");
        if(thirst - amount < 0) {
            thirst = 0;
        } else {
            thirst -= amount;
        }

        nbt.putInt("thirst", thirst);
        // syncThirst(thirst, (ServerPlayerEntity) player);
        return thirst;
    }


}