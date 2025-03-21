package net.pygmales.petittools.data.provider;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableTextContent;
import net.pygmales.petittools.PetitTools;
import net.pygmales.petittools.blocks.BlockRegistry;
import net.pygmales.petittools.common.TranslationKeys;
import net.pygmales.petittools.entities.MinerBlockEntity;
import net.pygmales.petittools.item_groups.ItemGroups;
import net.pygmales.petittools.items.ItemRegistry;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

public class EnUsTranslationProvider extends FabricLanguageProvider {
    public EnUsTranslationProvider(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(dataOutput, "en_us", registryLookup);
    }

    private static void addText(@NotNull TranslationBuilder builder, @NotNull Text text, @NotNull String value) {
        if (text.getContent() instanceof TranslatableTextContent textContent)
            builder.add(textContent.getKey(), value);

        else PetitTools.LOGGER.warn("Failed to generate translation for text: {}", text.getString());
    }

    @Override
    public void generateTranslations(RegistryWrapper.WrapperLookup wrapperLookup, TranslationBuilder translationBuilder) {
        translationBuilder.add(ItemRegistry.SUSPICIOUS_SUBSTANCE, "Suspicious Substance");
        addText(translationBuilder, TranslationKeys.SUSPICIOUS_SUBSTANCE_INFO, "Strange mass with properties similar to charcoal");

        translationBuilder.add(ItemRegistry.COUNTER_PICKAXE, "Counter Pickaxe");
        addText(translationBuilder, TranslationKeys.COUNTER_PICKAXE_INFO, "Mined %1$s blocks");

        translationBuilder.add(ItemRegistry.CREEPER_SACK, "Creeper Sack");
        addText(translationBuilder, TranslationKeys.CREEPER_SACK_INFO, "Nasty but edible");

        translationBuilder.add(BlockRegistry.CREEPER_SACK_BLOCK_ITEM, "Creeper Sack Block");
        translationBuilder.add(BlockRegistry.COUNTER_BLOCK_ITEM, "Counter Block");
        translationBuilder.add(BlockRegistry.MINER_BLOCK_ITEM, "Miner Block");
        translationBuilder.add(BlockRegistry.SACK_FURNACE_BLOCK_ITEM, "Sack Furnace");

        addText(translationBuilder, ItemGroups.MAIN_GROUP_TITLE, "Petit Tools");
        addText(translationBuilder, MinerBlockEntity.TITLE, "Miner Block");
    }
}
