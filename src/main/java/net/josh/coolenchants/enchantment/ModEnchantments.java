package net.josh.coolenchants.enchantment;

import net.josh.coolenchants.CoolEnchants;
import net.josh.coolenchants.enchantment.armor.boots.LavaWalkerEnchantment;
import net.josh.coolenchants.enchantment.armor.boots.SpeedBootsEnchantment;
import net.josh.coolenchants.enchantment.armor.helmet.ScouterEnchantment;
import net.josh.coolenchants.enchantment.armor.helmet.necromancy.NecroDragonEnchantment;
import net.josh.coolenchants.enchantment.armor.helmet.necromancy.NecromancyEnchantment;
import net.josh.coolenchants.enchantment.bow.*;
import net.josh.coolenchants.enchantment.sword.*;
import net.josh.coolenchants.enchantment.tools.axe.ChopEnchantment;
import net.josh.coolenchants.enchantment.armor.boots.doublejump.DoubleJumpEnchantment;
import net.josh.coolenchants.enchantment.tools.axe.TimberEnchantment;
import net.josh.coolenchants.enchantment.tools.shield.DeflectEnchantment;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import org.w3c.dom.ranges.Range;

public class ModEnchantments {



    private static Enchantment register(String name,Enchantment enchantment){
        return Registry.register(Registries.ENCHANTMENT,
                new Identifier(CoolEnchants.MOD_ID, name), enchantment);
    }

    public static void registerModEnchantments(){
        System.out.println("Registering Enchantments for " + CoolEnchants.MOD_ID);
    }

//............................SWORD......................
    public static Enchantment LIGHTNING_STRIKER = register(
            "lightning_striker",
            new LightningStrikerEnchantment(
                    Enchantment.Rarity.UNCOMMON,
                    EnchantmentTarget.WEAPON,
                    EquipmentSlot.MAINHAND));

    public static Enchantment COW_SPAWNER = register(
            "cow_spawn",
            new CowSpawnerEnchantment(
                    Enchantment.Rarity.UNCOMMON,
                    EnchantmentTarget.WEAPON,
                    EquipmentSlot.MAINHAND));

    public static Enchantment CREEPER_NULLIFIER = register(
            "creeper_null",
            new CreeperNullifierEnchantment(
                    Enchantment.Rarity.UNCOMMON,
                    EnchantmentTarget.WEAPON,
                    EquipmentSlot.MAINHAND));
    public static Enchantment DEATH_MARK = register(
            "death_mark",
            new DeathMarkEnchantment(
                    Enchantment.Rarity.UNCOMMON,
                    EnchantmentTarget.WEAPON,
                    EquipmentSlot.MAINHAND));

    public static Enchantment FLOATER = register(
            "float",
            new FloaterEnchantment(
                    Enchantment.Rarity.UNCOMMON,
                    EnchantmentTarget.WEAPON,
                    EquipmentSlot.MAINHAND));
    public static Enchantment FREEZE = register(
            "freeze",
            new FreezesMobsEnchantment(
                    Enchantment.Rarity.UNCOMMON,
                    EnchantmentTarget.WEAPON,
                    EquipmentSlot.MAINHAND));
    public static Enchantment LAVA_SPAWN = register(
            "lava_spawn",
            new LavaSpawnerEnchantment(
                    Enchantment.Rarity.UNCOMMON,
                    EnchantmentTarget.WEAPON,
                    EquipmentSlot.MAINHAND));
    public static Enchantment TARGET = register(
            "target",
            new MobBefrienderEnchantment(
                    Enchantment.Rarity.UNCOMMON,
                    EnchantmentTarget.WEAPON,
                    EquipmentSlot.MAINHAND));
    public static Enchantment MORE_XP = register(
            "more_xp",
            new MoreXPEnchantment(
                    Enchantment.Rarity.UNCOMMON,
                    EnchantmentTarget.WEAPON,
                    EquipmentSlot.MAINHAND));
    public static Enchantment POISON_ENCHANT = register(
            "poison_enchant",
            new PoisonEnchantment(
                    Enchantment.Rarity.UNCOMMON,
                    EnchantmentTarget.WEAPON,
                    EquipmentSlot.MAINHAND));
    public static Enchantment SWING_SPEED = register(
            "swing_speed",
            new SwingSpeedEnchantment(
                    Enchantment.Rarity.UNCOMMON,
                    EnchantmentTarget.WEAPON,
                    EquipmentSlot.MAINHAND));

    public static Enchantment DOUBLE_JUMP = register(
            "double_jump",
            new DoubleJumpEnchantment(
                    Enchantment.Rarity.UNCOMMON,
                    EnchantmentTarget.ARMOR_FEET,
                    EquipmentSlot.FEET));
    public static Enchantment SPEED_BOOST = register(
            "speed_boost",
            new SpeedBootsEnchantment(
                    Enchantment.Rarity.UNCOMMON,
                    EnchantmentTarget.ARMOR_FEET,
                    EquipmentSlot.FEET));
    public static Enchantment LAVA_WALKER = register(
            "lava_walker",
            new LavaWalkerEnchantment(
                    Enchantment.Rarity.UNCOMMON,
                    EnchantmentTarget.ARMOR_FEET,
                    EquipmentSlot.FEET));
    public static Enchantment REANIMATOR = register(
            "reanimator",
            new ReanimatorEnchantment(
                    Enchantment.Rarity.UNCOMMON,
                    EnchantmentTarget.WEAPON,
                    EquipmentSlot.MAINHAND));
    public static Enchantment LIFESTEAL = register(
            "lifesteal",
            new LifestealEnchantment(
                    Enchantment.Rarity.UNCOMMON,
                    EnchantmentTarget.WEAPON,
                    EquipmentSlot.MAINHAND));

    public static Enchantment HEAL = register(
            "heal",
            new HealEnchantment(
                    Enchantment.Rarity.UNCOMMON,
                    EnchantmentTarget.WEAPON,
                    EquipmentSlot.MAINHAND));
    public static Enchantment DINNERBONE = register(
            "dinnerbone",
            new DinnerboneEnchantment(
                    Enchantment.Rarity.UNCOMMON,
                    EnchantmentTarget.WEAPON,
                    EquipmentSlot.MAINHAND));
    public static Enchantment WITHER = register(
            "wither",
            new WitherEnchantment(
                    Enchantment.Rarity.UNCOMMON,
                    EnchantmentTarget.WEAPON,
                    EquipmentSlot.MAINHAND));
    public static Enchantment WITHER_SKULL = register(
            "wither_skull",
            new WitherSkullEnchantment(
                    Enchantment.Rarity.UNCOMMON,
                    EnchantmentTarget.WEAPON,
                    EquipmentSlot.MAINHAND));
    public static Enchantment BLAZE_SHOOTER = register(
            "blaze_fireball",
            new BlazeShooterEnchantment(
                    Enchantment.Rarity.UNCOMMON,
                    EnchantmentTarget.WEAPON,
                    EquipmentSlot.MAINHAND));
    public static Enchantment WARDEN_SHOOTER = register(
            "warden_shooter",
            new WardenShooterEnchantment(
                    Enchantment.Rarity.UNCOMMON,
                    EnchantmentTarget.WEAPON,
                    EquipmentSlot.MAINHAND));
    public static Enchantment DRAGON_SHOOTER = register(
            "dragon_fireball",
            new DragonFireBallShooterEnchantment(
                    Enchantment.Rarity.UNCOMMON,
                    EnchantmentTarget.WEAPON,
                    EquipmentSlot.MAINHAND));
    public static Enchantment SNOWBALL = register(
            "snowball",
            new SnowBallEnchantment(
                    Enchantment.Rarity.UNCOMMON,
                    EnchantmentTarget.WEAPON,
                    EquipmentSlot.MAINHAND));
    public static Enchantment FIRE_SHOOTER = register(
            "fireball",
            new FireBallShooterEnchantment(
                    Enchantment.Rarity.UNCOMMON,
                    EnchantmentTarget.WEAPON,
                    EquipmentSlot.MAINHAND));
    public static Enchantment ASTRAL_REMOVE = register(
            "astral_remove",
            new AstralRemoveEnchantment(
                    Enchantment.Rarity.UNCOMMON,
                    EnchantmentTarget.WEAPON,
                    EquipmentSlot.MAINHAND));
//....................BOWS........................

    public static Enchantment EXPLODING_ARROWS = register(
            "exploding_arrows",
            new ExplodingArrowsEnchantment(
                    Enchantment.Rarity.UNCOMMON,
                    EnchantmentTarget.BOW,
                    EquipmentSlot.MAINHAND));
    public static Enchantment CREEPER_EXPLODER = register(
            "creeper_exploder",
            new CreeperExploderEnchantment(
                    Enchantment.Rarity.UNCOMMON,
                    EnchantmentTarget.BOW,
                    EquipmentSlot.MAINHAND));
    public static Enchantment SCOPE = register(
            "scope",
            new ScopeEnchantment(
                    Enchantment.Rarity.UNCOMMON,
                    EnchantmentTarget.BOW,
                    EquipmentSlot.MAINHAND));
    public static Enchantment RANGE = register(
            "range",
            new RangeEnchantment(
                    Enchantment.Rarity.UNCOMMON,
                    EnchantmentTarget.BOW,
                    EquipmentSlot.MAINHAND));
    public static Enchantment RELOAD = register(
            "reload",
            new ReloadEnchantment(
                    Enchantment.Rarity.UNCOMMON,
                    EnchantmentTarget.BOW,
                    EquipmentSlot.MAINHAND));

    public static Enchantment TREE = register(
            "tree",
            new TreeEnchantment(
                    Enchantment.Rarity.UNCOMMON,
                    EnchantmentTarget.BOW,
                    EquipmentSlot.MAINHAND));
    public static Enchantment BARRAGE = register(
            "barrage",
            new BarrageEnchantment(
                    Enchantment.Rarity.UNCOMMON,
                    EnchantmentTarget.BOW,
                    EquipmentSlot.MAINHAND));
//................TOOLS......................

    public static Enchantment CHOP = register(
            "chop",
            new ChopEnchantment(
                    Enchantment.Rarity.UNCOMMON,
                    EnchantmentTarget.DIGGER,
                    EquipmentSlot.MAINHAND));
    public static Enchantment TIMBER = register(
            "timber",
            new TimberEnchantment(
                    Enchantment.Rarity.UNCOMMON,
                    EnchantmentTarget.DIGGER,
                    EquipmentSlot.MAINHAND));
    public static Enchantment EXCAVATE = register(
            "excavate",
            new net.josh.coolenchants.enchantment.tools.pickaxe.ExcavateEnchantment(
                    Enchantment.Rarity.UNCOMMON,
                    EnchantmentTarget.DIGGER,
                    EquipmentSlot.MAINHAND));
    public static Enchantment FILL = register(
            "fill",
            new net.josh.coolenchants.enchantment.tools.shovel.FillEnchantment(
                    Enchantment.Rarity.UNCOMMON,
                    EnchantmentTarget.DIGGER,
                    EquipmentSlot.MAINHAND));
    public static Enchantment SPLEEF = register(
            "spleef",
            new net.josh.coolenchants.enchantment.tools.shovel.SpleefEnchantment(
                    Enchantment.Rarity.UNCOMMON,
                    EnchantmentTarget.DIGGER,
                    EquipmentSlot.MAINHAND));
    public static Enchantment TUNNEL = register(
            "tunnel",
            new net.josh.coolenchants.enchantment.tools.shovel.TunnelEnchantment(
                    Enchantment.Rarity.UNCOMMON,
                    EnchantmentTarget.DIGGER,
                    EquipmentSlot.MAINHAND));
    public static Enchantment GOLDEN_APPLE = register(
            "golden_apple",
            new net.josh.coolenchants.enchantment.tools.axe.GoldenAppleEnchantment(
                    Enchantment.Rarity.UNCOMMON,
                    EnchantmentTarget.DIGGER,
                    EquipmentSlot.MAINHAND));


//.................HOE..................
public static Enchantment SCYTHE = register(
        "scythe",
        new net.josh.coolenchants.enchantment.tools.hoe.ScytheEnchantment(
                Enchantment.Rarity.UNCOMMON,
                EnchantmentTarget.BREAKABLE,
                EquipmentSlot.MAINHAND));
    public static Enchantment FORAGE = register(
            "forage",
            new net.josh.coolenchants.enchantment.tools.hoe.ForageEnchantment(
                    Enchantment.Rarity.UNCOMMON,
                    EnchantmentTarget.BREAKABLE,
                    EquipmentSlot.MAINHAND));
    public static Enchantment HARVEST = register(
            "harvest",
            new net.josh.coolenchants.enchantment.tools.hoe.HarvestEnchantment(
                    Enchantment.Rarity.UNCOMMON,
                    EnchantmentTarget.BREAKABLE,
                    EquipmentSlot.MAINHAND));
    public static Enchantment GREEN_THUMB = register(
            "green_thumb",
            new net.josh.coolenchants.enchantment.tools.hoe.GreenThumbEnchantment(
                    Enchantment.Rarity.UNCOMMON,
                    EnchantmentTarget.BREAKABLE,
                    EquipmentSlot.MAINHAND));

//..............ARMOR.................
        public static Enchantment DEFLECT = register(
            "deflect",
            new DeflectEnchantment(
                    Enchantment.Rarity.UNCOMMON,
                    EnchantmentTarget.ARMOR,
                    EquipmentSlot.CHEST));
        public static Enchantment TIME_CHANGE = register(
        "time_change",
            new TimeChangerEnchantment(
                Enchantment.Rarity.UNCOMMON,
                EnchantmentTarget.ARMOR,
                EquipmentSlot.CHEST));
        public static Enchantment CLIMB = register(
            "climb",
            new net.josh.coolenchants.enchantment.armor.boots.WallCrawlerEnchantment(
                    Enchantment.Rarity.UNCOMMON,
                    EnchantmentTarget.ARMOR_FEET,
                    EquipmentSlot.FEET));
        public static Enchantment SWIFT_SWIM = register(
            "swift_swim",
            new net.josh.coolenchants.enchantment.armor.helmet.SwiftSwimEnchantment(
                    Enchantment.Rarity.UNCOMMON,
                    EnchantmentTarget.ARMOR_HEAD,
                    EquipmentSlot.HEAD));
        public static Enchantment CHALLENGE = register(
            "challenge",
            new net.josh.coolenchants.enchantment.armor.helmet.ChallengeEnchantment(
                    Enchantment.Rarity.UNCOMMON,
                    EnchantmentTarget.ARMOR_HEAD,
                    EquipmentSlot.HEAD));
        public static Enchantment HARD_HAT = register(
            "hard_hat",
            new net.josh.coolenchants.enchantment.armor.helmet.HardHatEnchantment(
                    Enchantment.Rarity.UNCOMMON,
                    EnchantmentTarget.ARMOR_HEAD,
                    EquipmentSlot.HEAD));
    public static Enchantment LIGHT = register(
            "light",
            new net.josh.coolenchants.enchantment.armor.helmet.LightEnchantment(
                    Enchantment.Rarity.UNCOMMON,
                    EnchantmentTarget.ARMOR_HEAD,
                    EquipmentSlot.HEAD));
    public static Enchantment PERSUADE = register(
            "persuade",
            new net.josh.coolenchants.enchantment.armor.helmet.PersuadeEnchantment(
                    Enchantment.Rarity.UNCOMMON,
                    EnchantmentTarget.ARMOR,
                    EquipmentSlot.CHEST));


    public static Enchantment EXTRA_HEALTH = register(
            "health_boost",
            new net.josh.coolenchants.enchantment.armor.ExtraHealthEnchantment(
                    Enchantment.Rarity.UNCOMMON,
                    EnchantmentTarget.ARMOR,
                    EquipmentSlot.CHEST));


    public static Enchantment PHOTO = register(
            "photo",
            new net.josh.coolenchants.enchantment.armor.helmet.PhotosynthesisEnchantment(
                    Enchantment.Rarity.UNCOMMON,
                    EnchantmentTarget.ARMOR,
                    EquipmentSlot.CHEST));
    public static Enchantment REGEN = register(
            "regen",
            new net.josh.coolenchants.enchantment.armor.RegenEnchantment(
                    Enchantment.Rarity.UNCOMMON,
                    EnchantmentTarget.ARMOR,
                    EquipmentSlot.CHEST));
    public static Enchantment WITHER_IMMUNE = register(
            "wither_immune",
            new net.josh.coolenchants.enchantment.armor.WitherImmuneEnchantment(
                    Enchantment.Rarity.UNCOMMON,
                    EnchantmentTarget.ARMOR,
                    EquipmentSlot.CHEST));
    public static Enchantment SLIME_BOOTS = register(
            "slime_boots",
            new net.josh.coolenchants.enchantment.armor.boots.SlimeBootsEnchantment(
                    Enchantment.Rarity.UNCOMMON,
                    EnchantmentTarget.ARMOR_FEET,
                    EquipmentSlot.FEET));
    public static Enchantment ASTRAL_PROJECT = register(
            "astral_project",
            new net.josh.coolenchants.enchantment.armor.helmet.AstralProjectEnchantment(
                    Enchantment.Rarity.UNCOMMON,
                    EnchantmentTarget.ARMOR_HEAD,
                    EquipmentSlot.HEAD));
    public static Enchantment ASTRAL_TELEPORT = register(
            "astral_teleport",
            new net.josh.coolenchants.enchantment.armor.helmet.AstralTeleportEnchantment(
                    Enchantment.Rarity.UNCOMMON,
                    EnchantmentTarget.ARMOR_HEAD,
                    EquipmentSlot.HEAD));
    public static Enchantment ASTRAL_BLINK = register(
            "astral_blink",
            new net.josh.coolenchants.enchantment.armor.helmet.AstralBlinkEnchantment(
                    Enchantment.Rarity.UNCOMMON,
                    EnchantmentTarget.ARMOR_HEAD,
                    EquipmentSlot.HEAD));
    public static Enchantment ASTRAL_PLANE = register(
            "astral_plane",
            new net.josh.coolenchants.enchantment.armor.helmet.AstralPlaneEnchantment(
                    Enchantment.Rarity.UNCOMMON,
                    EnchantmentTarget.ARMOR_HEAD,
                    EquipmentSlot.HEAD));


    public static Enchantment NECROMANCY = register(
            "necromancy",
            new NecromancyEnchantment(
                    Enchantment.Rarity.UNCOMMON,
                    EnchantmentTarget.ARMOR_HEAD,
                    EquipmentSlot.HEAD));
    public static Enchantment NECRO_DRAGON = register(
            "necro_dragon",
            new NecroDragonEnchantment(
                    Enchantment.Rarity.UNCOMMON,
                    EnchantmentTarget.ARMOR_HEAD,
                    EquipmentSlot.HEAD));
    public static Enchantment SCOUTER = register(
            "scouter",
            new ScouterEnchantment(
                    Enchantment.Rarity.UNCOMMON,
                    EnchantmentTarget.ARMOR_HEAD,
                    EquipmentSlot.HEAD));


    public static Enchantment CHRONOMANCY = register(
            "chronomancy",
            new net.josh.coolenchants.enchantment.armor.helmet.ChronomancyEnchantment(
                    Enchantment.Rarity.UNCOMMON,
                    EnchantmentTarget.ARMOR_HEAD,
                    EquipmentSlot.HEAD));
    public static Enchantment CHRONO_PAUSE = register(
            "chrono_pause",
            new net.josh.coolenchants.enchantment.armor.helmet.ChronoPauseEnchantment(
                    Enchantment.Rarity.UNCOMMON,
                    EnchantmentTarget.ARMOR_HEAD,
                    EquipmentSlot.HEAD));

    public static Enchantment JUMP_BOOST = register(
            "jump_boost",
            new net.josh.coolenchants.enchantment.armor.boots.JumpBoostEnchantment(
                    Enchantment.Rarity.UNCOMMON,
                    EnchantmentTarget.ARMOR_FEET,
                    EquipmentSlot.FEET));
    public static Enchantment GRAVITY = register(
            "gravity",
            new net.josh.coolenchants.enchantment.armor.boots.GravityEnchantment(
                    Enchantment.Rarity.UNCOMMON,
                    EnchantmentTarget.ARMOR_FEET,
                    EquipmentSlot.FEET));
    public static Enchantment CRUSH = register(
            "crush",
            new net.josh.coolenchants.enchantment.armor.boots.CrushEnchantment(
                    Enchantment.Rarity.UNCOMMON,
                    EnchantmentTarget.ARMOR_FEET,
                    EquipmentSlot.FEET));
    public static Enchantment LIGHTNING_ROD = register(
            "lightning_rod",
            new net.josh.coolenchants.enchantment.armor.helmet.LightningRodEnchantment(
                    Enchantment.Rarity.UNCOMMON,
                    EnchantmentTarget.ARMOR_HEAD,
                    EquipmentSlot.HEAD));
    public static Enchantment UNTOUCHABLE = register(
            "untouchable",
            new net.josh.coolenchants.enchantment.armor.GojoEnchantment(
                    Enchantment.Rarity.UNCOMMON,
                    EnchantmentTarget.ARMOR,
                    EquipmentSlot.CHEST));
    public static Enchantment SAFE_PEARL = register(
            "safepearl",
            new net.josh.coolenchants.enchantment.armor.boots.SafePearlEnchantment(
                    Enchantment.Rarity.UNCOMMON,
                    EnchantmentTarget.ARMOR_FEET,
                    EquipmentSlot.FEET));

//.......................MISC.................................

public static Enchantment KIBBLE = register(
        "kibble",
        new net.josh.coolenchants.enchantment.misc.KibbleEnchantment(
                Enchantment.Rarity.UNCOMMON,
                EnchantmentTarget.BREAKABLE,
                EquipmentSlot.MAINHAND));
public static Enchantment INFINITE_POTION = register(
        "infinite_potion",
        new net.josh.coolenchants.enchantment.misc.InfinitePotionEnchantment(
                Enchantment.Rarity.UNCOMMON,
                EnchantmentTarget.BREAKABLE,
                EquipmentSlot.MAINHAND));

    public static Enchantment NUKE = register(
            "nuke",
            new NukeEnchantment(
                    Enchantment.Rarity.UNCOMMON,
                    EnchantmentTarget.BOW,
                    EquipmentSlot.MAINHAND));
    public static Enchantment TNT_RAIN = register(
            "tnt_rain",
            new TntRainEnchantment(
                    Enchantment.Rarity.UNCOMMON,
                    EnchantmentTarget.BOW,
                    EquipmentSlot.MAINHAND));
    public static Enchantment DEATH = register(
            "death",
            new Sharpness10billionEnchantment(
                    Enchantment.Rarity.UNCOMMON,
                    EnchantmentTarget.WEAPON,
                    EquipmentSlot.MAINHAND));


}
