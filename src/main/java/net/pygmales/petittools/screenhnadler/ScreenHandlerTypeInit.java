package net.pygmales.petittools.screenhnadler;

import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.pygmales.petittools.PetitTools;
import net.pygmales.petittools.network.BlockPosPayload;
import net.pygmales.petittools.network.TestBookPayload;

public class ScreenHandlerTypeInit {
    public static final ScreenHandlerType<MinerBlockScreenHandler> MINER_BLOCK = register("miner_block", MinerBlockScreenHandler::new, BlockPosPayload.PACKET_CODEC);
    public static final ScreenHandlerType<TestBookScreenHandler> TEST_BOOK = register("test_book", TestBookScreenHandler::new, TestBookPayload.PACKET_CODEC);

    public static <T extends ScreenHandler, D extends CustomPayload>ExtendedScreenHandlerType<T, D>
            register(String name,
                     ExtendedScreenHandlerType.ExtendedFactory<T, D> factory,
                     PacketCodec<? super RegistryByteBuf, D> codec) {
        return Registry.register(Registries.SCREEN_HANDLER, PetitTools.id(name), new ExtendedScreenHandlerType<>(factory, codec));
    }

    public static void initialize() {}
}
