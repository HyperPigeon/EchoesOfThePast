package net.hyper_pigeon.echoes_of_the_past.mixin;

import net.hyper_pigeon.echoes_of_the_past.EchoesOfThePast;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = {VillagerEntity.class, WanderingTraderEntity.class, AllayEntity.class,
        HorseEntity.class, AbstractDonkeyEntity.class,
        ParrotEntity.class, WolfEntity.class})
public class EchoClockInteractionMixin {

    @Inject(method = "interactMob", at = @At(value = "HEAD"), cancellable = true)
    public void echoClockInteraction(PlayerEntity player, Hand hand, CallbackInfoReturnable<ActionResult> cir)
    {
        ItemStack stack = player.getStackInHand(hand);
        World world = player.getEntityWorld();
        if(!world.isClient)
        {
            if(stack.getItem() == EchoesOfThePast.ECHO_CLOCK_ITEM)
            {
                player.swingHand(hand);
                cir.setReturnValue(ActionResult.PASS);
            }
        }
    }
}
