package net.pygmales.petittools.item_groups;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.text.Text;
import net.pygmales.petittools.PetitTools;
import net.pygmales.petittools.items.ItemRegistry;

public class ItemGroups {
    public static final Text MAIN_GROUP_TITLE = Text.translatable("itemGroup." + PetitTools.MOD_ID + ".main_group");

    public static final RegistryKey<ItemGroup> MAIN_GROUP_KEY = RegistryKey.of(
            Registries.ITEM_GROUP.getKey(), PetitTools.id("main_group"));

    public static final ItemGroup MAIN_GROUP = FabricItemGroup.builder()
            .icon(() -> new ItemStack(ItemRegistry.SUSPICIOUS_SUBSTANCE))
            .displayName(MAIN_GROUP_TITLE)
            .build();

    public static void initialize() {
        Registry.register(Registries.ITEM_GROUP, ItemGroups.MAIN_GROUP_KEY, ItemGroups.MAIN_GROUP);
    }

    public static void mainAddAll(Item... items) {
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.MAIN_GROUP_KEY).register(ig -> {
            for (Item item: items) ig.add(item);
        });
    }
}
