package com.mola.molamod;

import com.mola.molamod.proxy.ClientProxy;
import com.mola.molamod.proxy.CommonProxy;
import com.mola.molamod.utils.LoggerUtil;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
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

    public static final CreativeTabs MOLA_TAB = new MolaTab("molatab");

    @SidedProxy(clientSide = "com.mola.molamod.proxy.ClientProxy",
            serverSide = "com.mola.molamod.proxy.CommonProxy")
    private static CommonProxy proxy;

    private static Logger logger;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        initLogger(event);
        proxy.preInit(event);
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        logger.info("molamola-Mod : {}，使用proxy : {} ", "mola模组初始化", proxy.getClass().getName());
        proxy.init(event);
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        proxy.postInit(event);
    }

    @EventHandler
    public void serverStarting(FMLServerStartingEvent event) {
        proxy.serverStarting(event);
    }

    /**
     * 初始化logger
     * @param event
     */
    private void initLogger(FMLPreInitializationEvent event) {
        logger = event.getModLog();
        LoggerUtil.init(logger);
    }

    /**
     * 是否是单纯的client
     * @return
     */
    public static boolean isServer() {
        return !(proxy instanceof ClientProxy);
    }
}
