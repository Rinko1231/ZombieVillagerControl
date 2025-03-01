package com.rinko1231.zombievillagercontrol.mixin;

import com.rinko1231.zombievillagercontrol.config.ZVCConfig;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.monster.ZombieVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import javax.annotation.Nullable;
import java.util.UUID;

@Mixin(ZombieVillager.class)
public abstract class ZombieVillagerMixin extends Zombie {


    public ZombieVillagerMixin(EntityType<? extends Zombie> entityType, Level level) {
        super(entityType, level);
    }
@Shadow
protected abstract void startConverting(@Nullable UUID conversionStarter, int villagerConversionTime);

    @Inject(method = "mobInteract", at = @At("HEAD"), cancellable = true)
    public void mobInteract2(Player player, InteractionHand hand, CallbackInfoReturnable<InteractionResult> cir) {
        ItemStack itemstack = player.getItemInHand(hand);
        if (itemstack.is(Items.GOLDEN_APPLE)) {
            if (this.hasEffect(MobEffects.WEAKNESS)) {
                itemstack.consume(1, player);
                if (!this.level().isClientSide) {
                    if(ZVCConfig.QuickCure.get())
                        this.startConverting(player.getUUID(), 0);
                    else this.startConverting(player.getUUID(), this.random.nextInt(2401) + 3600);
                }

                cir.setReturnValue(InteractionResult.SUCCESS);
            } else {
                cir.setReturnValue(InteractionResult.CONSUME);
            }
        } else {
            cir.setReturnValue(super.mobInteract(player, hand));
        }

    }

}
