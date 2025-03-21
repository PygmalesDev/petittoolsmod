package net.pygmales.petittools.blocks;

import net.minecraft.block.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.pygmales.petittools.util.block.HorizontalFacingBlock;
import net.pygmales.petittools.items.ItemRegistry;

public class SackFurnace extends HorizontalFacingBlock {
    public static final BooleanProperty LIT = BooleanProperty.of("lit");
    public SackFurnace(Settings settings) {
        super(settings.luminance(state -> state.get(LIT) ? 15 : 0));

        setDefaultState(getDefaultState().with(LIT, false));
    }

    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        if (!player.getAbilities().allowModifyWorld)
            return ActionResult.PASS;

        ItemStack mainHandStack = player.getMainHandStack();

        if (!state.get(LIT)) {
            if (mainHandStack.getItem() == ItemRegistry.SUSPICIOUS_SUBSTANCE) {
                mainHandStack.decrementUnlessCreative(1, player);
                world.playSoundAtBlockCenter(pos, SoundEvents.ITEM_FIRECHARGE_USE,
                        SoundCategory.BLOCKS, 1.0f, 1.0f, true);
                world.setBlockState(pos, state.with(LIT, true));


                Vec3d offsetXZ = getOffsetXZ(state.get(FACING));
                world.addParticle(ParticleTypes.SMOKE,
                        pos.getX()+offsetXZ.x, pos.getY() + 0.3, pos.getZ()+offsetXZ.z,
                        0.0f, 0.1f, 0.0f);
                world.addParticle(ParticleTypes.CAMPFIRE_COSY_SMOKE,
                        pos.getX()+offsetXZ.x, pos.getY(), pos.getZ()+offsetXZ.z,
                        0.0f, 0.1f, 0.0f);
            }
        } else {
            world.playSoundAtBlockCenter(pos, SoundEvents.BLOCK_FIRE_EXTINGUISH,
                    SoundCategory.BLOCKS, 1.0f, 1.0f, true);
            world.setBlockState(pos, state.with(LIT, false));
            world.addParticle(ParticleTypes.ASH, pos.getX(), pos.getY(), pos.getZ(),
                    0.0f, 0.3f, 0.0f);
        }

        return ActionResult.SUCCESS;
    }

    @Override
    protected void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        super.scheduledTick(state, world, pos, random);
    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        if (state.get(LIT)) {
            if (random.nextInt(15) == 1) {
                world.playSoundAtBlockCenter(pos, SoundEvents.ENTITY_CREEPER_HURT,
                        SoundCategory.AMBIENT, 0.5f, 1.0f, true);
            }

            if (random.nextInt(4) != 1) return;

            Vec3d offsetXZ = getOffsetXZ(state.get(FACING));
            for (int i = 0; i < random.nextInt(3); i++) {
                double offsetY = 0.3 + random.nextBetween(0, 3) * 0.1;

                double x = pos.getX() + offsetXZ.x;
                double y = pos.getY() + offsetY;
                double z = pos.getZ() + offsetXZ.z;

                switch (state.get(FACING)) {
                    case SOUTH -> x += random.nextBetween(-4, 4) * 0.1;
                    case NORTH -> x = x;
                    case WEST -> z = z;
                    case EAST -> z += random.nextBetween(-4, 4) * 0.1;
                }

                world.addParticle(ParticleTypes.WHITE_SMOKE, x, y, z,
                        0.0f, 0.02f, 0.0f);
                world.addParticle(ParticleTypes.FLAME, x, y, z,
                        0.0f, 0.0f, 0.0f);
            }
        }
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(LIT);
    }
}
