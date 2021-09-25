package com.mola.molamod.network;

import com.google.common.collect.Maps;
import com.mola.molamod.MolaMod;
import com.mola.molamod.annotation.CustomPacket;
import com.mola.molamod.constants.PositionEventCodeConstant;
import com.mola.molamod.handlers.CustomPacketHandler;
import com.mola.molamod.network.handler.PosEventHandler;
import com.mola.molamod.network.handler.impl.MagicBookExplosionHandler;
import com.mola.molamod.utils.LoggerUtil;
import io.netty.buffer.ByteBuf;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import org.apache.logging.log4j.Logger;

import java.util.Map;

/**
 * @author : molamola
 * @Project: forge-1.12.2-14.23.5.2847-mdk
 * @Description: 位置传输包，将客户端的位置传输到服务端
 * @date : 2021-09-25 16:31
 **/
@CustomPacket(side = {Side.SERVER}, discriminator = 0)
public class PositionSendPacket extends MessagePacket {

    private static Logger logger = LoggerUtil.getLogger();

    private Vec3d vec3d;

    private static Map<String, PosEventHandler> eventHandlerMap = Maps.newHashMap();

    static {
        CustomPacketHandler.registerHandler(PositionSendPacket.class, new Handler());
        // 注册策略
        eventHandlerMap.put(PositionEventCodeConstant.MAGIC_BOOK_EXPLOSION, new MagicBookExplosionHandler());
    }

    public PositionSendPacket() {
    }


    public PositionSendPacket(Vec3d vec3d, String eventCode) {
        this.vec3d = vec3d;
        this.compound.setDouble("x", vec3d.x);
        this.compound.setDouble("y", vec3d.y);
        this.compound.setDouble("z", vec3d.z);
        this.compound.setString("eventCode", eventCode);
    }

    public PositionSendPacket(Vec3d vec3d, String eventCode, Map<String, String> params) {
        this(vec3d, eventCode);
        for (Map.Entry<String, String> kv : params.entrySet()) {
            compound.setString(kv.getKey(), kv.getValue());
        }
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        compound = ByteBufUtils.readTag(buf);
        this.vec3d = new Vec3d(compound.getDouble("x"), compound.getDouble("y"), compound.getDouble("z"));
    }

    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeTag(buf, compound);
    }

    public static class Handler implements IMessageHandler<PositionSendPacket, IMessage> {
        @Override
        public IMessage onMessage(PositionSendPacket message, MessageContext ctx) {
            if (ctx.side == Side.SERVER) {
                logger.info("onMessage, isServer = {}, vec = {}", MolaMod.isServer(), message.vec3d);
                String eventCode = message.compound.getString("eventCode");
                PosEventHandler posEventHandler = eventHandlerMap.get(eventCode);
                if (null == posEventHandler) {
                    logger.error("posEventHandler is null, eventCode = {}", eventCode);
                    return message;
                }
                posEventHandler.handlePosEvent(message.vec3d, ctx, message.compound);
            }
            return message;
        }
    }
}
