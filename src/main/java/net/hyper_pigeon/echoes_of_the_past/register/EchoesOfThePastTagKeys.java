package net.hyper_pigeon.echoes_of_the_past.register;

import net.minecraft.entity.EntityType;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;


public class EchoesOfThePastTagKeys {
    private static final Identifier BLACKLIST_ID = new Identifier("echoes_of_the_past:blacklist");
    public static final TagKey<EntityType<?>> BLACKLIST = TagKey.of(Registry.ENTITY_TYPE_KEY,BLACKLIST_ID);
}
