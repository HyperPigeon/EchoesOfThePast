package net.hyper_pigeon.echoes_of_the_past.register;

import net.minecraft.entity.EntityType;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;


public class EchoesOfThePastTagKeys {
    private static final Identifier BLACKLIST_ID = new Identifier("echoes_of_the_past:blacklist");
    public static final TagKey<EntityType<?>> BLACKLIST = TagKey.of(RegistryKeys.ENTITY_TYPE,BLACKLIST_ID);
}
