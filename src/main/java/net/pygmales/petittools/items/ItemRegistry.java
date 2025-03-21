package net.pygmales.petittools.items;

import net.fabricmc.fabric.api.registry.FuelRegistryEvents;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.pygmales.petittools.PetitTools;
import net.pygmales.petittools.item_groups.ItemGroups;
import net.pygmales.petittools.materials.ToolMaterials;

import java.util.function.Function;

import static net.pygmales.petittools.common.Const.*;

public class ItemRegistry {
    public static final Item SUSPICIOUS_SUBSTANCE = register("suspicious_substance", SuspiciousSubstance::new, new Item.Settings());
    public static final Item CREEPER_SACK = register("creeper_sack", CreeperSack::new, new Item.Settings());
    public static final Item COUNTER_PICKAXE = register("counter_pickaxe", settings ->
            new CounterPickaxe(ToolMaterials.CREEP_TOOL_MATERIAL, 1f, 1f, settings), new Item.Settings());


    public static Item register(String itemName, Function<Item.Settings, Item> itemFactory, Item.Settings settings) {
        RegistryKey<Item> key = RegistryKey.of(RegistryKeys.ITEM, PetitTools.id(itemName));
        Item item = itemFactory.apply(settings.registryKey(key));

        Registry.register(Registries.ITEM, key, item);

        return item;
    }

    public static void initialize() {
        ItemGroups.mainAddAll(
            SUSPICIOUS_SUBSTANCE, CREEPER_SACK, COUNTER_PICKAXE
        );

        // Registering items as fuel sources
        FuelRegistryEvents.BUILD.register((b, c) -> {
            b.add(SUSPICIOUS_SUBSTANCE, SUSPICIOUS_SUBSTANCE_BURN_TIME);
        });
    }
}
