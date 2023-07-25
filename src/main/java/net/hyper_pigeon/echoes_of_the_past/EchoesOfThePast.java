package net.hyper_pigeon.echoes_of_the_past;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.hyper_pigeon.echoes_of_the_past.item.EchoClockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;

public class EchoesOfThePast implements ModInitializer {

    public static final EchoClockItem ECHO_CLOCK_ITEM = new EchoClockItem(new Item.Settings().maxCount(1).maxDamage(8).
            rarity(Rarity.RARE));

    @Override
    public void onInitialize() {
        Registry.register(Registries.ITEM, new Identifier("echoes_of_the_past", "echo_clock"), ECHO_CLOCK_ITEM);
        ItemGroupEvents
                .modifyEntriesEvent(ItemGroups.TOOLS)
                .register((itemGroup) -> itemGroup.add(EchoesOfThePast.ECHO_CLOCK_ITEM));
    }


}
