package net.pygmales.petittools.items;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;
import net.pygmales.petittools.common.TranslationKeys;

import java.util.List;

public class SuspiciousSubstance extends Item {

    public SuspiciousSubstance(Settings settings) {
        super(settings);
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        tooltip.add(TranslationKeys.SUSPICIOUS_SUBSTANCE_INFO);
    }
}
