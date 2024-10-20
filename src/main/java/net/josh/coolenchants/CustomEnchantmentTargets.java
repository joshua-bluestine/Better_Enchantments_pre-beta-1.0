/*
 * Decompiled with CFR 0.2.2 (FabricMC 7c48b8c4).
 */
package net.josh.coolenchants;

import net.minecraft.block.Block;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.*;

/*
 * Uses 'sealed' constructs - enablewith --sealed true
 */
public enum CustomEnchantmentTargets{




    HOE{
        @Override
        public boolean isAcceptableItem(Item item) {
            return item instanceof HoeItem;
        }
    }
    ,
    COOKED_FOOD{
        @Override
        public boolean isAcceptableItem(Item item) {
            return item == Items.COOKED_BEEF ||
                    item == Items.COOKED_CHICKEN ||
                    item == Items.COOKED_COD ||
                    item == Items.COOKED_MUTTON ||
                    item == Items.COOKED_RABBIT ||
                    item == Items.COOKED_PORKCHOP ||
                    item == Items.COOKED_SALMON;
        }
    }
    ;


    public abstract boolean isAcceptableItem(Item var1);
}

