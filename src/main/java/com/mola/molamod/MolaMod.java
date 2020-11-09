package com.mola.molamod;

import com.mola.molamod.factory.CustomHandlerManager;
import com.mola.molamod.factory.CustomHandlerRegistry;
import com.mola.molamod.utils.LoggerUtil;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import org.apache.logging.log4j.Logger;

@Mod(modid = MolaMod.MODID, name = MolaMod.NAME, version = MolaMod.VERSION)
public class MolaMod
{
    public static final String MODID = "molamod";
    public static final String NAME = "Mola's Mod";
    public static final String VERSION = "1.0.0";

    private static Logger logger;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        initLogger(event);
        // 扫描包下实体
        CustomHandlerRegistry.scanningInPackage();
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        logger.info("molamola-Mod : {}", "mola模组初始化");
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
    }

    @EventHandler
    public void serverStarting(FMLServerStartingEvent event) {
        CustomHandlerManager.getCommandHandler().registerCommand(event);
    }

    /**
     * 初始化logger
     * @param event
     */
    private void initLogger(FMLPreInitializationEvent event) {
        logger = event.getModLog();
        LoggerUtil.init(logger);
    }
}
