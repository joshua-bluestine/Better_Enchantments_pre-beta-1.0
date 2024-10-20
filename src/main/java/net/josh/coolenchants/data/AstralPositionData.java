package net.josh.coolenchants.data;

import net.josh.coolenchants.IEntityDataSaver;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;

import java.util.UUID;

public class AstralPositionData {
    public static void setY(IEntityDataSaver entity, int y) {
        NbtCompound nbt = entity.getPersistentData();
        nbt.putInt("astralpos", y);
    }
    public static int getY(IEntityDataSaver entity){
        NbtCompound nbt = entity.getPersistentData();
        if (nbt.contains("astralpos")){
            return nbt.getInt("astralpos");
        }
        return 0;
    }
}
