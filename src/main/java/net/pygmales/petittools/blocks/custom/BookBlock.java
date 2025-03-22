package net.pygmales.petittools.blocks.custom;

import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.pygmales.petittools.blocks.BlockRegistry;
import net.pygmales.petittools.entities.BlockEntityTypeInit;
import net.pygmales.petittools.util.block.HorizontalFacingBlock;
import net.pygmales.petittools.util.entity.TickableBlockEntity;
import org.jetbrains.annotations.Nullable;

public class BookBlock extends HorizontalFacingBlock implements BlockEntityProvider {
    public static final BooleanProperty CLOSED = BooleanProperty.of("closed");

    private final VoxelShape SHAPE = VoxelShapes.union(
            VoxelShapes.cuboid(0.5, 0, 0.25, 0.8125, 0.0625, 0.75),
            VoxelShapes.cuboid(0.1875, 0, 0.25, 0.5, 0.0625, 0.75)
    ).simplify();

    public BookBlock(Settings settings) {
        super(settings.noBlockBreakParticles().sounds(BlockSoundGroup.WOOL));

        setDefaultState(getDefaultState().with(CLOSED, false));
    }

    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        if (!world.isClient) {
            if (player.isSneaking()) {
                ItemStack book = new ItemStack(BlockRegistry.BOOK_BLOCK_ITEM);
                if (!player.getInventory().insertStack(book))
                    ItemScatterer.spawn(world, pos, new SimpleInventory(book));

                world.breakBlock(pos, false);

                return ActionResult.SUCCESS;
            }

            boolean closedState = state.get(CLOSED);
            world.setBlockState(pos, state.with(CLOSED, !closedState));
            world.playSound(null, pos, SoundEvents.ITEM_BOOK_PAGE_TURN, SoundCategory.AMBIENT);
        }
        return ActionResult.SUCCESS;
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return TickableBlockEntity.getTicker(world);
    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        if (!state.get(CLOSED))
            world.addParticle(ParticleTypes.ENCHANT,
                    pos.getX()+random.nextFloat(), pos.getY(),
                    pos.getZ()+random.nextFloat(), 0.0f, 0.1f, 0.0f);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(CLOSED);
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return BlockEntityTypeInit.BOOK_BLOCK_ENTITY.instantiate(pos, state);
    }

    @Override
    protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Override
    protected BlockState rotate(BlockState state, BlockRotation rotation) {
        return super.rotate(state, rotation);
    }

    @Override
    protected BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.INVISIBLE;
    }
}
