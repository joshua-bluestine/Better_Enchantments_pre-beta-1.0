package net.josh.coolenchants.data;

import net.josh.coolenchants.IEntityDataSaver;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;

import java.util.UUID;

public class NecromancerOwnerData {
    public static void setOwener(IEntityDataSaver entity, PlayerEntity player) {
        NbtCompound nbt = entity.getPersistentData();
        //PlayerEntity player = nbt.getUuid("owner");
        nbt.putUuid("owner", player.getUuid());
    }
    public static UUID getOwner(IEntityDataSaver entity){
        NbtCompound nbt = entity.getPersistentData();
        if (nbt.contains("owner")){
            return nbt.getUuid("owner");
        }
        return null;
    }
}
