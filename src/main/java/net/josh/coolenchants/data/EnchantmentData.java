package net.josh.coolenchants.data;

import net.josh.coolenchants.IEntityDataSaver;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;

import java.util.UUID;

public class EnchantmentData {

    public static void setScope(IEntityDataSaver entity, boolean scope) {
        NbtCompound nbt = entity.getPersistentData();
        nbt.putBoolean("scope", scope);
    }
    public static boolean getScope(IEntityDataSaver entity){
        NbtCompound nbt = entity.getPersistentData();
        if (nbt.contains("scope")){
            return nbt.getBoolean("scope");
        }
        return false;
    }


    public static void setScouter(IEntityDataSaver entity, boolean scouter) {
        NbtCompound nbt = entity.getPersistentData();
        nbt.putBoolean("scouter", scouter);
    }
    public static boolean getScouter(IEntityDataSaver entity){
        NbtCompound nbt = entity.getPersistentData();
        if (nbt.contains("scouter")){
            return nbt.getBoolean("scouter");
        }
        return false;
    }
    public static void setBlink(IEntityDataSaver entity, boolean scouter) {
        NbtCompound nbt = entity.getPersistentData();
        nbt.putBoolean("blink", scouter);
    }
    public static boolean getBlink(IEntityDataSaver entity){
        NbtCompound nbt = entity.getPersistentData();
        if (nbt.contains("blink")){
            return nbt.getBoolean("blink");
        }
        return false;
    }

    public static void setBlinkCooldown(IEntityDataSaver entity, int cooldown) {
        NbtCompound nbt = entity.getPersistentData();
        nbt.putInt("blinkCooldown", cooldown);
    }
    public static int getBlinkCooldown(IEntityDataSaver entity){
        NbtCompound nbt = entity.getPersistentData();
        if (nbt.contains("blinkCooldown")){
            return nbt.getInt("blinkCooldown");
        }
        return 0;
    }


}
