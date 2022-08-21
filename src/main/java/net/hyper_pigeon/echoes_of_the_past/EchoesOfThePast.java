package net.hyper_pigeon.echoes_of_the_past;

import net.fabricmc.api.ModInitializer;
import net.hyper_pigeon.echoes_of_the_past.item.EchoClockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.minecraft.util.registry.Registry;

public class EchoesOfThePast implements ModInitializer {

    public static final EchoClockItem ECHO_CLOCK_ITEM = new EchoClockItem(new Item.Settings().maxCount(1).group(ItemGroup.TOOLS).maxDamage(8).
            rarity(Rarity.RARE));

    @Override
    public void onInitialize() {
        Registry.register(Registry.ITEM, new Identifier("echoes_of_the_past", "echo_clock"), ECHO_CLOCK_ITEM);
    }


}
