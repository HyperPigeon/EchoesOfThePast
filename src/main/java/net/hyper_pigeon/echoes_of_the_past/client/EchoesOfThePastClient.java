package net.hyper_pigeon.echoes_of_the_past.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.hyper_pigeon.echoes_of_the_past.EchoesOfThePast;
import net.hyper_pigeon.echoes_of_the_past.item.EchoClockItem;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class EchoesOfThePastClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ModelPredicateProviderRegistry.register(EchoesOfThePast.ECHO_CLOCK_ITEM,
                new Identifier("echoes_of_the_past","position"),
                (stack, world, entity, seed) -> EchoClockItem.clockHandPosition(stack));

        ModelPredicateProviderRegistry.register(EchoesOfThePast.ECHO_CLOCK_ITEM,
                new Identifier("echoes_of_the_past","is_alive"),
                (stack, world, entity, seed) -> {
                return !stack.getOrCreateNbt().contains("is_alive") ? 0f : (stack.getOrCreateNbt().getBoolean("is_alive") ? 0f : 1f);
                });

        ModelPredicateProviderRegistry.register(EchoesOfThePast.ECHO_CLOCK_ITEM,
                new Identifier("echoes_of_the_past","broken"),
                (stack, world, entity, seed) -> {
                    return stack.getDamage() < 8 ? 0f : 1f;
                });

    }
}
