package net.pygmales.petittools.network;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.pygmales.petittools.PetitTools;

public record TestBookPayload(String holder) implements CustomPayload {
    public static final Id<TestBookPayload> ID = new Id<>(PetitTools.id("test_book"));
    public static final PacketCodec<RegistryByteBuf, TestBookPayload> PACKET_CODEC =
            PacketCodec.tuple(PacketCodecs.STRING, TestBookPayload::holder, TestBookPayload::new);

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}
