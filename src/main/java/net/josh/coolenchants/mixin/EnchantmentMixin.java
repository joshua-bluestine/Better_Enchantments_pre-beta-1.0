package net.josh.coolenchants.mixin;

import net.josh.coolenchants.enchantment.misc.KibbleEnchantment;
import net.josh.coolenchants.enchantment.tools.axe.ChopEnchantment;
import net.josh.coolenchants.enchantment.tools.hoe.ForageEnchantment;
import net.josh.coolenchants.enchantment.tools.hoe.GreenThumbEnchantment;
import net.josh.coolenchants.enchantment.tools.hoe.HarvestEnchantment;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(Enchantment.class)
public class EnchantmentMixin {
    Enchantment john = (Enchantment) (Object) this;
    /**
     * @author
     * @reason
     */
    @Overwrite
    public boolean isAcceptableItem(ItemStack stack) {
        if (john instanceof ForageEnchantment ||
                john instanceof GreenThumbEnchantment ||
                john instanceof HarvestEnchantment ||
                john instanceof KibbleEnchantment ||
                john instanceof ChopEnchantment){
            return john.isAcceptableItem(stack);
        }
        return john.target.isAcceptableItem(stack.getItem());
       // return true;
    }

}
