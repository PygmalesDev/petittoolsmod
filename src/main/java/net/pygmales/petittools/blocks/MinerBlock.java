package net.pygmales.petittools.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.pygmales.petittools.util.block.HorizontalFacingBlock;
import net.pygmales.petittools.entities.BlockEntityTypeInit;
import net.pygmales.petittools.entities.MinerBlockEntity;
import net.pygmales.petittools.util.entity.TickableBlockEntity;
import org.jetbrains.annotations.Nullable;

public class MinerBlock extends HorizontalFacingBlock implements BlockEntityProvider {
    public static final BooleanProperty CLOSED = BooleanProperty.of("closed");

    public MinerBlock(Settings settings) {
        super(settings.luminance(state -> state.get(CLOSED) ? 10 : 0));

        setDefaultState(getDefaultState().with(CLOSED, false));
    }

    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        if (world.getBlockEntity(pos) instanceof MinerBlockEntity minerEntity) {
            if (!world.isClient) player.openHandledScreen(minerEntity);
        }

        return ActionResult.SUCCESS;
    }

    @Override
    protected void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        super.onStateReplaced(state, world, pos, newState, moved);
        if (newState.isAir() || state.get(CLOSED) == newState.get(CLOSED)) return;

        if (newState.get(CLOSED))
            world.playSound(null, pos, SoundEvents.BLOCK_BARREL_CLOSE, SoundCategory.BLOCKS);
        else
            world.playSound(null, pos, SoundEvents.BLOCK_BARREL_OPEN, SoundCategory.BLOCKS);
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return BlockEntityTypeInit.MINER_BLOCK_ENTITY.instantiate(pos, state);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(CLOSED);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return TickableBlockEntity.getTicker(world);
    }

    @Override
    public BlockState onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        if (world.getBlockEntity(pos) instanceof MinerBlockEntity e) {
            ItemScatterer.spawn(world, pos, e.getInventory());
        }

        return super.onBreak(world, pos, state, player);
    }
}
