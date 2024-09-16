package com.rinko1231.zombievillagercontrol;

import com.mojang.logging.LogUtils;
import com.rinko1231.zombievillagercontrol.config.ZVCConfig;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.monster.ZombieVillager;
import net.minecraftforge.common.MinecraftForge;

import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;

import net.minecraftforge.fml.config.ModConfig;
import org.slf4j.Logger;

import static com.rinko1231.zombievillagercontrol.config.ZVCConfig.CONFIG;


@Mod("zombievillagercontrol")
public class ZombieVillagerControl {

    private static final Logger LOGGER = LogUtils.getLogger();

    // 构造函数 - 这个是模组的启动入口
    public ZombieVillagerControl() {
        // 注册事件总线 (Event Bus)
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, CONFIG);
        MinecraftForge.EVENT_BUS.addListener(this::onZombieVillagerCure);
    }

    public void onZombieVillagerCure(LivingEvent.LivingTickEvent event) {
        if (event.getEntity() instanceof ZombieVillager zombieVillager) {
            if (zombieVillager.isAlive() && zombieVillager.isConverting()) {
                if (!zombieVillager.level().isClientSide && ZVCConfig.QuickCure.get()) {
                    zombieVillager.finishConversion((ServerLevel) zombieVillager.level());
                }
            }
        }
    }
}
