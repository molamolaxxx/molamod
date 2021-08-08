package com.mola.molamod.proxy;

import com.mola.molamod.factory.CustomHandlerManager;
import com.mola.molamod.factory.CustomHandlerRegistry;
import com.mola.molamod.utils.LoggerUtil;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

/**
 * @author : molamola
 * @Project: forge-1.12.2-14.23.5.2847-mdk
 * @Description: 客户端
 * @date : 2021-08-09 00:14
 **/
public class ClientProxy extends CommonProxy{

    @Override
    public void preInit(FMLPreInitializationEvent event) {
        LoggerUtil.getLogger().info("[ClientProxy] preInit");
        super.preInit(event);
        // 扫描包下实体
        CustomHandlerRegistry.scanningInPackage();
    }

    @Override
    public void init(FMLInitializationEvent event) {
        LoggerUtil.getLogger().info("[ClientProxy] init");
        super.init(event);
    }

    @Override
    public void postInit(FMLPostInitializationEvent event) {
        LoggerUtil.getLogger().info("[ClientProxy] postInit");
        super.postInit(event);
    }
    public void serverStarting(FMLServerStartingEvent event) {
        LoggerUtil.getLogger().info("[ClientProxy] serverStarting");
        super.serverStarting(event);
        // 注册命令
        CustomHandlerManager.getCommandHandler().registerCommand(event);
    }

}
