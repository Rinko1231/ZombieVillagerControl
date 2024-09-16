package com.rinko1231.zombievillagercontrol.mixin;

import com.rinko1231.zombievillagercontrol.config.ZVCConfig;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.monster.ZombieVillager;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Zombie.class)
public class ZombieMixin {

    @Inject(method = "killedEntity(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/entity/LivingEntity;)Z", at = @At("HEAD"), cancellable = true)
    public void killedEntity(ServerLevel pLevel, LivingEntity pEntity, CallbackInfoReturnable<Boolean> cir) {
        if (pEntity instanceof Villager villager) {

            double infectionProbability = ZVCConfig.ZombifiedPossibility.get();

            if (pLevel.random.nextDouble() < infectionProbability && net.minecraftforge.event.ForgeEventFactory.canLivingConvert(pEntity, EntityType.ZOMBIE_VILLAGER, (timer) -> {})) {
                ZombieVillager zombievillager = villager.convertTo(EntityType.ZOMBIE_VILLAGER, false);
                if (zombievillager != null) {
                    zombievillager.finalizeSpawn(pLevel, pLevel.getCurrentDifficultyAt(zombievillager.blockPosition()), MobSpawnType.CONVERSION, new Zombie.ZombieGroupData(false, true), (CompoundTag)null);
                    zombievillager.setVillagerData(villager.getVillagerData());
                    zombievillager.setGossips(villager.getGossips().store(NbtOps.INSTANCE));
                    zombievillager.setTradeOffers(villager.getOffers().createTag());
                    zombievillager.setVillagerXp(villager.getVillagerXp());
                    net.minecraftforge.event.ForgeEventFactory.onLivingConvert(pEntity, zombievillager);
                    if (!((Zombie)(Object)this).isSilent()) {
                        pLevel.levelEvent((Player)null, 1026, ((Zombie)(Object)this).blockPosition(), 0);
                    }
                }
            }
            cir.setReturnValue(false);  // 取消原来的处理
        }
    }
}