package net.pygmales.petittools.common;

import net.minecraft.item.Item;
import net.minecraft.text.Text;
import net.pygmales.petittools.items.ItemRegistry;

public class TranslationKeys {
    public static final Text SUSPICIOUS_SUBSTANCE_INFO = getInfoKey(ItemRegistry.SUSPICIOUS_SUBSTANCE);
    public static final Text CREEPER_SACK_INFO = getInfoKey(ItemRegistry.CREEPER_SACK);
    public static final Text COUNTER_PICKAXE_INFO = getInfoKey(ItemRegistry.COUNTER_PICKAXE);

    private static Text getInfoKey(Item registeredItem) {
        return Text.translatable(registeredItem.getTranslationKey() + ".item");
    }
}
