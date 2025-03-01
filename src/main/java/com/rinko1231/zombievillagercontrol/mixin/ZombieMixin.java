package com.rinko1231.zombievillagercontrol.mixin;

import com.rinko1231.zombievillagercontrol.config.ZVCConfig;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.monster.ZombieVillager;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.neoforged.neoforge.event.EventHooks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Zombie.class)
public class ZombieMixin  extends Monster {

    protected ZombieMixin(EntityType<? extends Monster> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(method = "killedEntity(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/entity/LivingEntity;)Z", at = @At("HEAD"), cancellable = true)
    public void killedEntity2(ServerLevel pLevel, LivingEntity pEntity, CallbackInfoReturnable<Boolean> cir) {
        if (pEntity instanceof Villager villager) {

            double infectionProbability = ZVCConfig.ZombifiedPossibility.get();

            if (pLevel.random.nextDouble() < infectionProbability && EventHooks.canLivingConvert(villager, EntityType.ZOMBIE_VILLAGER, (timer) -> {
            })) {
                ZombieVillager zombievillager = villager.convertTo(EntityType.ZOMBIE_VILLAGER, false);
                if (zombievillager != null) {
                    zombievillager.finalizeSpawn((ServerLevelAccessor) this.level(),this.level().getCurrentDifficultyAt(zombievillager.blockPosition()), MobSpawnType.CONVERSION, new Zombie.ZombieGroupData(false, true));
                    zombievillager.setVillagerData(villager.getVillagerData());
                    zombievillager.setGossips((Tag)villager.getGossips().store(NbtOps.INSTANCE));
                    zombievillager.setTradeOffers(villager.getOffers().copy());
                    zombievillager.setVillagerXp(villager.getVillagerXp());
                    EventHooks.onLivingConvert(pEntity, zombievillager);
                    if (!this.isSilent()) {
                        this.level().levelEvent((Player)null, 1026, this.blockPosition(), 0);
                    }
                }
            }
            cir.setReturnValue(false);  // 取消原来的处理
        }
    }

}