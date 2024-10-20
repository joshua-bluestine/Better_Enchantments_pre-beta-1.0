package net.josh.coolenchants.data;

import net.josh.coolenchants.IEntityDataSaver;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.nbt.NbtList;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;
import java.util.List;

public class SpleefData {

    public static void setSpleef(IEntityDataSaver player, BlockPos pos) {
        NbtCompound nbt = player.getPersistentData();
        NbtCompound nbtee = NbtHelper.fromBlockPos(pos);

        nbt.put("spleef_pos", nbtee);
    }
    public static BlockPos getSpleef(IEntityDataSaver player) {
        // sync the data
        if (player.getPersistentData().contains("spleef_pos")){
            return NbtHelper.toBlockPos(player.getPersistentData().getCompound("spleef_pos"));
        }
        return null;
    }

    public static void addSpleef(IEntityDataSaver player, BlockState blockState) {
        NbtCompound data = player.getPersistentData();
        NbtList spleefList = new NbtList();
        //NbtList spleefList = data.contains("spleef_list") ? data.getList("spleef_list", 10) : new NbtList();
        if (data.contains("spleef_list")){
            spleefList = data.getList("spleef_list", 10);
        }
        NbtCompound stateNbt = NbtHelper.fromBlockState(blockState);
        spleefList.add(stateNbt);

        data.put("spleef_list", spleefList);
    }


    public static List<BlockState> getBlockStateList(IEntityDataSaver player, PlayerEntity player2) {
        List<BlockState> blockStateList = new ArrayList<>();
        NbtCompound data = player.getPersistentData();

        if (data.contains("spleef_list")) {
            NbtList spleefList = data.getList("spleef_list", 10);
            System.out.println("test1");
            for (int i = 0; i < spleefList.size(); i++) {
                System.out.println(i);
                NbtCompound stateNbt = spleefList.getCompound(i);
                BlockState blockState = NbtHelper.toBlockState(player2.getWorld().createCommandRegistryWrapper(RegistryKeys.BLOCK), stateNbt);
                blockStateList.add(blockState);
            }
        }
        return blockStateList;
    }
    public static void clearBlockStateList(IEntityDataSaver player) {
        NbtCompound data = player.getPersistentData();
        if (data.contains("spleef_list")){
            NbtList greg = data.getList("spleef_list", 10);
            greg.clear();

            data.put("spleef_list", greg);
        }
    }

    public static void clearAll(IEntityDataSaver player){
        NbtCompound data = player.getPersistentData();
        if (data.contains("spleef_list")){
            data.remove("spleef_list");
        }
        if (data.contains("spleef_pos")){
            data.remove("spleef_pos");
        }
    }
}