package net.pygmales.petittools.entities;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.pygmales.petittools.PetitTools;

public class CounterBlockEntity extends BlockEntity {
    private int counter;

    public CounterBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityTypeInit.COUNTER_BLOCK_ENTITY, pos, state);
    }

    public int getCounter() {
        return counter;
    }

    public void incrementCounter() {
        this.counter++;
        markDirty();

        if (counter%10 == 0 && this.world instanceof ServerWorld sw) {
            EntityType.PIG.spawn(sw, this.pos.up(), SpawnReason.TRIGGERED);
        }
    }

    @Override
    protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registries) {
        var data = new NbtCompound();
        data.putInt("counter",  counter);
        nbt.put(PetitTools.MOD_ID, data);
    }

    @Override
    protected void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registries) {
        if (!nbt.contains(PetitTools.MOD_ID, NbtElement.COMPOUND_TYPE)) return;

        var data = nbt.getCompound(PetitTools.MOD_ID);
        counter = data.contains("counter", NbtElement.INT_TYPE) ? data.getInt("counter") : 0;
    }
}
