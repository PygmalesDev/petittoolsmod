package net.pygmales.petittools.items;

import net.fabricmc.fabric.api.registry.FuelRegistryEvents;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.pygmales.petittools.PetitTools;
import net.pygmales.petittools.item_groups.ItemGroups;
import net.pygmales.petittools.items.custom.TestBookItem;
import net.pygmales.petittools.materials.ToolMaterials;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

import static net.pygmales.petittools.common.Const.*;

public class ItemRegistry {
    public static final List<Item> ITEMS = new ArrayList<>();

    public static final Item RAW_AZURITE_ORE;
    public static final Item AZURITE_INGOT;
    public static final Item SUSPICIOUS_SUBSTANCE;
    public static final Item CREEPER_SACK;
    public static final Item COUNTER_PICKAXE;
    public static final Item TEST_BOOK;

    public static Item register(String itemName, Function<Item.Settings, Item> itemFactory, Item.Settings settings) {
        RegistryKey<Item> key = RegistryKey.of(RegistryKeys.ITEM, PetitTools.id(itemName));
        Item item = itemFactory.apply(settings.registryKey(key));

        ITEMS.add(item);
        ItemGroups.mainAddAll(item);
        Registry.register(Registries.ITEM, key, item);

        return item;
    }

    public static void initialize() {
        FuelRegistryEvents.BUILD.register((b, c) -> b.add(SUSPICIOUS_SUBSTANCE, SUSPICIOUS_SUBSTANCE_BURN_TIME));
    }

    static {
        RAW_AZURITE_ORE = register("raw_azurite_ore", Item::new, new Item.Settings());
        AZURITE_INGOT = register("azurite_ingot", Item::new, new Item.Settings());
        SUSPICIOUS_SUBSTANCE = register("suspicious_substance", SuspiciousSubstance::new, new Item.Settings());
        CREEPER_SACK = register("creeper_sack", CreeperSack::new, new Item.Settings());
        COUNTER_PICKAXE = register("counter_pickaxe", settings ->
                new CounterPickaxe(ToolMaterials.CREEP_TOOL_MATERIAL, 1f, 1f, settings), new Item.Settings());
        TEST_BOOK = register("test_book", TestBookItem::new, new Item.Settings());
    }
}
