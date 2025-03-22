package net.pygmales.petittools.entities;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.pygmales.petittools.blocks.custom.BookBlock;
import net.pygmales.petittools.util.entity.TickableBlockEntity;

import java.util.Objects;

public class BookBlockEntity extends BlockEntity implements TickableBlockEntity {
    private float bookPadding = 0;
    private float upperPartAngle;

    public BookBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityTypeInit.BOOK_BLOCK_ENTITY, pos, state);
    }

    public float getUpperPartAngle() {
        return upperPartAngle;
    }

    public boolean getBookClosedState() {
        if (Objects.isNull(world) || world.getBlockState(pos).isAir()) return false;
        return world.getBlockState(pos).get(BookBlock.CLOSED);
    }

    public void setUpperPartAngle(float upperPartAngle) {
        this.upperPartAngle = upperPartAngle;
    }

    public float getBookPadding() {
        return bookPadding;
    }

    public void setBookPadding(float bookPadding) {
        this.bookPadding = bookPadding;
    }

    @Override
    public void tick() {

    }

    @Override
    public void tickClientSide() {
    }
}
