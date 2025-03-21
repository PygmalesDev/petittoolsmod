package net.pygmales.petittools.items;

import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.PickaxeItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.pygmales.petittools.common.TranslationKeys;
import net.pygmales.petittools.components.ItemComponents;

import java.util.List;

public class CounterPickaxe extends PickaxeItem {
    public CounterPickaxe(ToolMaterial material, float attackDamage, float attackSpeed, Settings settings) {
        super(material, attackDamage, attackSpeed, settings
                .component(ItemComponents.MINED_COUNT_COMPONENT, 0));
    }

    @Override
    public boolean postMine(ItemStack stack, World world, BlockState state, BlockPos pos, LivingEntity miner) {
        if (!super.postMine(stack, world, state, pos, miner))
            return false;

        int count = stack.get(ItemComponents.MINED_COUNT_COMPONENT);
        stack.set(ItemComponents.MINED_COUNT_COMPONENT, count+1);

        return true;
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        int count = stack.get(ItemComponents.MINED_COUNT_COMPONENT);
        if (count > 0)
            tooltip.add(Text.translatable(TranslationKeys.COUNTER_PICKAXE_INFO.getString(), count));
    }
}
