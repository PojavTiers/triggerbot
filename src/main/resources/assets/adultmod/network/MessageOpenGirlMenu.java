package com.adultmod.network;

import com.adultmod.client.gui.GuiGirlMenu;
import com.adultmod.entity.EntityGirl;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageOpenGirlMenu implements IMessage {
    private int entityId;

    public MessageOpenGirlMenu() {}
    public MessageOpenGirlMenu(int entityId) { this.entityId = entityId; }

    @Override public void toBytes(ByteBuf buf) { buf.writeInt(entityId); }
    @Override public void fromBytes(ByteBuf buf) { entityId = buf.readInt(); }

    public static class Handler implements IMessageHandler<MessageOpenGirlMenu, IMessage> {
        @Override
        public IMessage onMessage(MessageOpenGirlMenu msg, MessageContext ctx) {
            Minecraft.getMinecraft().addScheduledTask(() -> {
                Entity e = Minecraft.getMinecraft().world.getEntityByID(msg.entityId);
                if (e instanceof EntityGirl) {
                    Minecraft.getMinecraft().displayGuiScreen(new GuiGirlMenu((EntityGirl) e));
                }
            });
            return null;
        }
    }
}
