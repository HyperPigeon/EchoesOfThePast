package net.hyper_pigeon.echoes_of_the_past.item;

import net.hyper_pigeon.echoes_of_the_past.register.EchoesOfThePastTagKeys;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Optional;
import java.util.UUID;

public class EchoClockItem extends Item {

    private static int ticksTillPositionChanges = 15;

    public EchoClockItem(Settings settings) {
        super(settings);
    }

    @SuppressWarnings("resource")
    public ActionResult useOnBlock(ItemUsageContext context)
    {
        ItemStack stack = context.getStack();
        if(!context.getWorld().isClient)
        {
            NbtCompound nbtCompound = stack.getOrCreateNbt();

            ServerWorld serverWorld = (ServerWorld) context.getWorld();
            BlockPos pos = context.getBlockPos().offset(context.getSide());
            Optional<EntityType<?>> entityType = EntityType.fromNbt(nbtCompound);

            if(entityType.isPresent() && serverWorld.getEntity(nbtCompound.getUuid("UUID")) == null){
                MobEntity mobEntity = (MobEntity) entityType.get().create(serverWorld);
                mobEntity.readCustomDataFromNbt(nbtCompound);
                mobEntity.setUuid(nbtCompound.getUuid("UUID"));
                mobEntity.refreshPositionAndAngles(pos,0,0);
                serverWorld.spawnEntity(mobEntity);

                stack.setDamage(stack.getDamage()+1);
                if(stack.getDamage() >= 8) {
                    context.getPlayer().sendToolBreakStatus(context.getHand());
                    stack.decrement(1);
                }

                return ActionResult.SUCCESS;
            }
        }

        return ActionResult.PASS;
    }

    public ActionResult useOnEntity(ItemStack stack, PlayerEntity player, LivingEntity entity, Hand hand)
    {
        if(entity instanceof MobEntity && !entity.getType().isIn(EchoesOfThePastTagKeys.BLACKLIST) && !player.getWorld().isClient()){

            entity.writeCustomDataToNbt(stack.getOrCreateNbt());
            stack.getOrCreateNbt().putString("id", getSavedEntityId(entity));
            stack.getOrCreateNbt().putUuid("UUID", entity.getUuid());
            stack.getOrCreateNbt().putBoolean("is_alive", true);
            stack.getOrCreateNbt().putFloat("position",0);

            player.setStackInHand(hand,stack);
            return ActionResult.SUCCESS;
        }
        return ActionResult.SUCCESS;
    }


    public boolean canRepair(ItemStack stack, ItemStack ingredient) {
        return (ingredient.getItem().equals(Items.ECHO_SHARD));
    }

    public boolean isDamageable(){
        return true;
    }

    private String getSavedEntityId(Entity entity) {
        EntityType<?> entityType = entity.getType();
        Identifier identifier = EntityType.getId(entityType);
        return entityType.isSaveable() && identifier != null ? identifier.toString() : null;
    }


    public static float clockHandPosition(ItemStack stack){
        if(stack.getOrCreateNbt().contains("position")) {
            float currentPosition = stack.getOrCreateNbt().getFloat("position");
            float maxPosition = stack.getOrCreateNbt().getBoolean("is_alive") ? 0.11f : 0.08f;

            if(ticksTillPositionChanges == 0) {
                if(currentPosition >= maxPosition){
                    stack.getOrCreateNbt().putFloat("position", 0);
                    ticksTillPositionChanges = 15;
                }
                else {
                    stack.getOrCreateNbt().putFloat("position", (float) (currentPosition+0.01));
                    ticksTillPositionChanges = 15;
                }
            }

            ticksTillPositionChanges--;
            return currentPosition;
        }
        return 0;
    }

    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if(stack.getOrCreateNbt().contains("UUID") && !world.isClient){
            UUID uuid = stack.getOrCreateNbt().getUuid("UUID");
            stack.getOrCreateNbt().putBoolean("is_alive", ((ServerWorld)(world)).getEntity(uuid) != null);
        }
    }






}
