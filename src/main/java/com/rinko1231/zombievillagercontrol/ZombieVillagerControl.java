package com.rinko1231.zombievillagercontrol;


import com.rinko1231.zombievillagercontrol.config.ZVCConfig;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.fml.common.Mod;

@Mod("zombievillagercontrol")
public class ZombieVillagerControl {

    // 构造函数 - 这个是模组的启动入口
    public ZombieVillagerControl(ModContainer modContainer) {
            //NeoForge.EVENT_BUS.register(new QuickCure());
            modContainer.registerConfig(ModConfig.Type.COMMON, ZVCConfig.CONFIG);
        }

}
