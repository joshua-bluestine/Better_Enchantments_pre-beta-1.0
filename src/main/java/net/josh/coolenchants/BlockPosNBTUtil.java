package net.josh.coolenchants;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.util.math.BlockPos;

public class BlockPosNBTUtil {
    public static NbtCompound blockPosToTag(BlockPos pos) {
        NbtCompound tag = new NbtCompound();
        tag.putInt("x", pos.getX());
        tag.putInt("y", pos.getY());
        tag.putInt("z", pos.getZ());
        return tag;
    }

    public static void writeBlockPosToList(BlockPos pos, NbtList list) {
        list.add(blockPosToTag(pos));
    }

    public static boolean isBlockPosInList(BlockPos pos, NbtList list) {
        for (int i = 0; i < list.size(); i++) {
            NbtCompound tag = list.getCompound(i);
            int x = tag.getInt("x");
            int y = tag.getInt("y");
            int z = tag.getInt("z");
            BlockPos storedPos = new BlockPos(x, y, z);
            if (pos.equals(storedPos)) {
                return true;
            }
        }
        return false;
    }
}
