package net.pygmales.petittools.items;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;
import net.pygmales.petittools.common.TranslationKeys;
import net.pygmales.petittools.components.ItemComponents;

import java.util.List;

public class CreeperSack extends Item {
    public CreeperSack(Settings settings) {
        super(settings.food(
                ItemComponents.foodComponent(3, 4.5f),
                ItemComponents.poisonFoodConsumableComponent(3))
        );
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        tooltip.add(TranslationKeys.CREEPER_SACK_INFO);
    }
}
