package net.pygmales.petittools.components;

import com.mojang.serialization.Codec;
import net.minecraft.component.ComponentType;
import net.minecraft.component.type.ConsumableComponent;
import net.minecraft.component.type.ConsumableComponents;
import net.minecraft.component.type.FoodComponent;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.consume.ApplyEffectsConsumeEffect;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.pygmales.petittools.PetitTools;
import net.pygmales.petittools.common.Const;

public class ItemComponents {
    public static FoodComponent foodComponent(int nutrition, float saturation) {
        return new FoodComponent.Builder()
                .nutrition(nutrition)
                .saturationModifier(saturation)
                .build();
    }


    public static ConsumableComponent poisonFoodConsumableComponent(int duration) {
        return ConsumableComponents.food()
                .consumeEffect(new ApplyEffectsConsumeEffect(new StatusEffectInstance(StatusEffects.POISON, duration * Const.TICK, 1), 1.0f))
                .build();
    }

    public static final ComponentType<Integer> MINED_COUNT_COMPONENT = Registry.register(
            Registries.DATA_COMPONENT_TYPE,
            Identifier.of(PetitTools.MOD_ID, "mined_count"),
            ComponentType.<Integer>builder().codec(Codec.INT).build()
    );
}
