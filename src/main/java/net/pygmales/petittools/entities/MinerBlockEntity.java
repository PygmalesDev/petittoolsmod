package net.pygmales.petittools.entities;

import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.fabricmc.fabric.api.transfer.v1.item.InventoryStorage;
import net.fabricmc.fabric.api.transfer.v1.item.ItemStorage;
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.Storage;
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.loot.context.LootWorldContext;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.pygmales.petittools.PetitTools;
import net.pygmales.petittools.blocks.MinerBlock;
import net.pygmales.petittools.network.BlockPosPayload;
import net.pygmales.petittools.screenhnadler.MinerBlockScreenHandler;
import net.pygmales.petittools.util.entity.TickableBlockEntity;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MinerBlockEntity extends BlockEntity implements TickableBlockEntity, ExtendedScreenHandlerFactory<BlockPosPayload> {
    public static final Text TITLE = Text.translatable("container." + PetitTools.MOD_ID + ".miner_block");
    private int ticks = 0;
    private int workingTicks = 0;
    private BlockPos miningPos = this.pos.down();

    private final SimpleInventory minerInventory = new SimpleInventory(10) {
        @Override
        public void markDirty() {
            super.markDirty();
            update();
        }
    };
    private final InventoryStorage inventoryStorage = InventoryStorage.of(minerInventory, null);

    public MinerBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityTypeInit.MINER_BLOCK_ENTITY, pos, state);
    }

    @Override
    protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registries) {
        var data = new NbtCompound();
        data.putInt("ticks",  ticks);
        data.putInt("workingTicks", workingTicks);
        data.putLong("miningPos", miningPos.asLong());
        Inventories.writeNbt(data, minerInventory.getHeldStacks(), registries);

        nbt.put(PetitTools.MOD_ID, data);
    }

    @Override
    protected void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registries) {
        if (!nbt.contains(PetitTools.MOD_ID, NbtElement.COMPOUND_TYPE)) return;

        var data = nbt.getCompound(PetitTools.MOD_ID);
        ticks = data.contains("ticks", NbtElement.INT_TYPE) ? data.getInt("ticks") : 0;
        workingTicks = data.contains("workingTicks", NbtElement.INT_TYPE) ? data.getInt("workingTicks") : 0;
        miningPos = data.contains("miningPos", NbtElement.LONG_TYPE)
                ? BlockPos.fromLong(data.getLong("miningPos")) : pos.down();
        Inventories.readNbt(data, minerInventory.getHeldStacks(), registries);
    }

    @Override
    public void tickClientSide() {
        if (Objects.isNull(world)) return;

        if (!world.getBlockState(pos.up()).isAir() || minerInventory.getStack(0).isEmpty() || Objects.isNull(world)) return;
        world.addParticle(ParticleTypes.SMOKE, pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5,
                0, 0.05, 0);
    }

    @Override
    public void tick() {
        if (Objects.isNull(world)) return;

        var minerState = world.getBlockState(pos);
        ItemStack fuelStack = minerInventory.getStack(0);
        if (fuelStack.isEmpty() && workingTicks == 0) {
            if (minerState.get(MinerBlock.CLOSED))
                world.setBlockState(pos, minerState.with(MinerBlock.CLOSED, false));
            return;
        }

        if (workingTicks == 0) {
            fuelStack.decrement(1);
            workingTicks = 6;
        }

        if (!minerState.get(MinerBlock.CLOSED))
            world.setBlockState(pos, minerState.with(MinerBlock.CLOSED, true));

        if (ticks % 4 == 0)
            world.playSound(null, pos, SoundEvents.BLOCK_BEEHIVE_WORK,
                    SoundCategory.BLOCKS, 1.0f, 0.5f);

        if (ticks++ % 40 == 39) {
            workingTicks--;
            update();

            if (this.miningPos.getY() <= world.getBottomY())
                this.miningPos = this.pos.down();

            var state = world.getBlockState(this.miningPos);
            if (state.isAir() || state.getHardness(world, miningPos) < 0) {
                miningPos = miningPos.down();
                return;
            }

            world.playSound(null, pos, SoundEvents.BLOCK_STONE_BREAK, SoundCategory.BLOCKS, 1.0f, 1.0f);

            // Get drops from the mined block
            List<ItemStack> drops = new ArrayList<>(state.getDroppedStacks(
                    new LootWorldContext.Builder((ServerWorld) world)
                            .add(LootContextParameters.TOOL, Items.DIAMOND_PICKAXE.getDefaultStack())
                            .add(LootContextParameters.ORIGIN, miningPos.toCenterPos())
                            .addOptional(LootContextParameters.BLOCK_ENTITY, this)));

            this.world.breakBlock(miningPos, false);

            Storage<ItemVariant> upStorage = findStorage((ServerWorld) world, pos.up());
            if (Objects.nonNull(upStorage) && upStorage.supportsInsertion())
                insertDrops(drops, upStorage);
            if (!drops.isEmpty()) insertDrops(drops, inventoryStorage);
            if (!drops.isEmpty()) spawnDrops(drops, (ServerWorld) world, pos);

            this.miningPos = miningPos.down();
        }
    }

    private static Storage<ItemVariant> findStorage(ServerWorld world, BlockPos pos) {
        return ItemStorage.SIDED.find(world, pos, Direction.DOWN);
    }

    private static void insertDrops(List<ItemStack> drops, Storage<ItemVariant> storage) {
        drops.forEach(d -> {
            try (Transaction transaction = Transaction.openOuter()) {
                long inserted = storage.insert(ItemVariant.of(d), d.getCount(), transaction);
                if (inserted > 0) {
                    d.decrement((int) inserted);
                    transaction.commit();
                } else {
                    transaction.abort();
                }
            }
        });
        drops.removeIf(ItemStack::isEmpty);
    }

    private static void spawnDrops(List<ItemStack> drops, ServerWorld world, BlockPos pos) {
        drops.forEach(d -> ItemScatterer.spawn(world, pos.getX() + 0.5, pos.getY() + 1.0, pos.getZ() + 0.5, d));
    }

    @Override
    public BlockPosPayload getScreenOpeningData(ServerPlayerEntity serverPlayerEntity) {
        return new BlockPosPayload(pos);
    }

    @Override
    public Text getDisplayName() {
        return TITLE;
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new MinerBlockScreenHandler(syncId, playerInventory, this);
    }

    private void update() {
        markDirty();
        if (world != null) {
            world.updateListeners(pos, getCachedState(), getCachedState(), Block.NOTIFY_ALL);
        }
    }

    public InventoryStorage getInventoryProvider(Direction direction) {
        return inventoryStorage;
    }

    public SimpleInventory getInventory() {
        return minerInventory;
    }

    public int getWorkingTicks() {
        return workingTicks;
    }

    @Nullable
    @Override
    public Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    @Override
    public NbtCompound toInitialChunkDataNbt(RegistryWrapper.WrapperLookup registries) {
        var nbt = super.toInitialChunkDataNbt(registries);
        writeNbt(nbt, registries);
        return nbt;
    }
}