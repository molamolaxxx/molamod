package com.mola.molamod.handlers;

import com.google.common.collect.Maps;
import com.mola.molamod.MolaMod;
import com.mola.molamod.network.MessagePacket;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.relauncher.Side;
import org.apache.logging.log4j.core.util.Assert;

import java.util.Map;

/**
 * @author : molamola
 * @Project: forge-1.12.2-14.23.5.2847-mdk
 * @Description: 网络包注册器
 * @date : 2021-09-25 16:58
 **/
public class CustomPacketHandler {

    /**
     * 网络事件handler注册器
     */
    private static Map<Class<? extends MessagePacket>, IMessageHandler> handlerMap = Maps.newHashMap();

    /**
     * 注册数据包
     * @param requestMessageType
     * @param discriminator
     * @param side handler作用在那一端
     */
    public void registerPacket(Class requestMessageType, int discriminator, Side side) {
        IMessageHandler handler = handlerMap.get(requestMessageType);
        Assert.requireNonEmpty(handler, "handler不能为空");
        MolaMod.getNetwork().registerMessage(handler, requestMessageType, discriminator, side);
    }

    /**
     * 注册处理器
     * @param clazz
     * @param handler
     */
    public static void registerHandler(Class<? extends MessagePacket> clazz, IMessageHandler handler) {
        handlerMap.put(clazz, handler);
    }
}
