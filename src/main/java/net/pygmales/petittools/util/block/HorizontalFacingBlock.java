package net.pygmales.petittools.util.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class HorizontalFacingBlock extends Block {
    public static final EnumProperty<Direction> FACING = EnumProperty.of("facing", Direction.class);

    public HorizontalFacingBlock(Settings settings) {
        super(settings);
        setDefaultState(getDefaultState().with(FACING, Direction.NORTH));
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        if (Objects.isNull(placer)) return;

        world.setBlockState(pos, state.with(FACING, placer.getHorizontalFacing().getOpposite()));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(FACING);
    }

    protected Vec3d getOffsetXZ(Direction direction) {
        return switch (direction) {
            case SOUTH -> new Vec3d(0.5, 0, 1.05);
            case NORTH -> new Vec3d(0.5, 0, 0);
            case WEST  -> new Vec3d(0, 0, 0.5);
            case EAST  -> new Vec3d(1.05, 0, 0.5);
            default    -> new Vec3d(0, 0, 0);
        };
    }
}
