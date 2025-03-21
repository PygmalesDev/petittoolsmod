package net.pygmales.petittools.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.pygmales.petittools.entities.BlockEntityTypeInit;
import net.pygmales.petittools.entities.CounterBlockEntity;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class CounterBlock extends Block implements BlockEntityProvider {
    public CounterBlock(Settings settings) {
        super(settings);
    }

    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        if (!world.isClient && Objects.nonNull(player)) {
            if (world.getBlockEntity(pos) instanceof CounterBlockEntity counterEntity) {
                if (player.isSneaking()) {
                    player.sendMessage(Text.of(counterEntity.getCounter() + ""), true);
                } else {
                    counterEntity.incrementCounter();
                }
            }
        }

        return ActionResult.SUCCESS;
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return BlockEntityTypeInit.COUNTER_BLOCK_ENTITY.instantiate(pos, state);
    }
}
