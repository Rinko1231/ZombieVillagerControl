package com.rinko1231.zombievillagercontrol;

import com.rinko1231.zombievillagercontrol.config.ZVCConfig;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.monster.ZombieVillager;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;

public class QuickCure {
/*
    @SubscribeEvent
    public void onZombieVillagerCure(EntityTickEvent.Pre event) {
        if (event.getEntity() instanceof ZombieVillager zombieVillager) {
            if (zombieVillager.isAlive() && zombieVillager.isConverting()) {
                if (!zombieVillager.level().isClientSide && ZVCConfig.QuickCure.get()) {
                    zombieVillager.villagerConversionTime =0;
                }
            }
        }
    }
*/

}
