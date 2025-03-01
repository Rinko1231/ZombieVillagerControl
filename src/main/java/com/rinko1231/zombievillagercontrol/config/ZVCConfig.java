package com.rinko1231.zombievillagercontrol.config;

import net.minecraftforge.common.ForgeConfigSpec;


public class ZVCConfig
{
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static ForgeConfigSpec CONFIG;

    public static ForgeConfigSpec.DoubleValue ZombifiedPossibility;

    public static ForgeConfigSpec.BooleanValue QuickCure;

    static
    {
        BUILDER.push("Zombie Villager Control Config");

        ZombifiedPossibility = BUILDER
                .comment("Possibility of villagers being zombified when being killed by a zombie or zombie villager.")
                .comment("Note: the vanilla difficulty is Ignored.")
                .defineInRange("ZombifiedPossibility", 1.0, 0, 1);

        QuickCure = BUILDER
                .comment("If enabled, zombified villager will be cured instantly.")
                .define("Enable QuickCure", false);

        CONFIG = BUILDER.build();
    }
}
