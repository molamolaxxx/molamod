package com.mola.molamod.proxy;

import com.mola.molamod.factory.CustomHandlerManager;
import com.mola.molamod.factory.CustomHandlerRegistry;
import com.mola.molamod.utils.LoggerUtil;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

/**
 * 公共代理，服务端客户端都使用
 */
public class CommonProxy {

    public void preInit(FMLPreInitializationEvent event) {
        LoggerUtil.getLogger().info("[CommonProxy] preInit");
        // 扫描包下实体
        CustomHandlerRegistry.scanningInPackage();
    }

    public void init(FMLInitializationEvent event) {
        LoggerUtil.getLogger().info("[CommonProxy] init");
    }

    public void postInit(FMLPostInitializationEvent event) {
        LoggerUtil.getLogger().info("[CommonProxy] postInit");
    }

    public void serverStarting(FMLServerStartingEvent event) {
        LoggerUtil.getLogger().info("[CommonProxy] serverStarting");
        // 注册命令
        CustomHandlerManager.getCommandHandler().registerCommand(event);
    }
}
