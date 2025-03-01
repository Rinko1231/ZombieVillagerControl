package com.rinko1231.zombievillagercontrol.config;


import net.neoforged.neoforge.common.ModConfigSpec;

public class ZVCConfig
{
    public static ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();
    public static ModConfigSpec CONFIG;

    public static ModConfigSpec.DoubleValue ZombifiedPossibility;

    public static ModConfigSpec.BooleanValue QuickCure;

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
