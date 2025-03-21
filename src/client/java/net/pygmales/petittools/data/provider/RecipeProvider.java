package net.pygmales.petittools.data.provider;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.recipe.RecipeExporter;
import net.minecraft.data.recipe.RecipeGenerator;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.pygmales.petittools.blocks.BlockRegistry;
import net.pygmales.petittools.items.ItemRegistry;

import java.util.concurrent.CompletableFuture;

public class RecipeProvider extends FabricRecipeProvider {
    public RecipeProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected RecipeGenerator getRecipeGenerator(RegistryWrapper.WrapperLookup wrapperLookup, RecipeExporter recipeExporter) {
        return new RecipeGenerator(wrapperLookup, recipeExporter) {
            @Override
            public void generate() {
                RegistryWrapper.Impl<Item> itemLookup = registries.getOrThrow(RegistryKeys.ITEM);

                createShapeless(RecipeCategory.BUILDING_BLOCKS, BlockRegistry.CREEPER_SACK_BLOCK_ITEM)
                        .input(ItemRegistry.CREEPER_SACK, 4)
                        .criterion(hasItem(ItemRegistry.CREEPER_SACK), conditionsFromItem(ItemRegistry.CREEPER_SACK))
                        .offerTo(recipeExporter);

                createShapeless(RecipeCategory.MISC, ItemRegistry.CREEPER_SACK, 4)
                        .input(BlockRegistry.CREEPER_SACK_BLOCK_ITEM)
                        .criterion(hasItem(BlockRegistry.CREEPER_SACK_BLOCK_ITEM),
                                conditionsFromItem(BlockRegistry.CREEPER_SACK_BLOCK_ITEM))
                        .offerTo(recipeExporter);

                createShaped(RecipeCategory.TOOLS, ItemRegistry.COUNTER_PICKAXE)
                        .pattern("ccc")
                        .pattern(" s ")
                        .pattern(" s ")
                        .input('c', ItemRegistry.CREEPER_SACK)
                        .input('s', Items.STICK)
                        .criterion(hasItem(ItemRegistry.CREEPER_SACK), conditionsFromItem(ItemRegistry.CREEPER_SACK))
                        .criterion(hasItem(Items.STICK), conditionsFromItem(Items.STICK))
                        .offerTo(recipeExporter);

                createShaped(RecipeCategory.DECORATIONS, BlockRegistry.SACK_FURNACE_BLOCK)
                        .pattern("ccc")
                        .pattern("c c")
                        .pattern("ccc")
                        .input('c', BlockRegistry.CREEPER_SACK_BLOCK)
                        .criterion(hasItem(BlockRegistry.CREEPER_SACK_BLOCK),
                                conditionsFromItem(BlockRegistry.CREEPER_SACK_BLOCK))
                        .offerTo(recipeExporter);
            }
        };
    }

    @Override
    public String getName() {
        return "PetitToolsRecipeProvider";
    }
}
