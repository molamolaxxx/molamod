package com.mola.molamod.eventbus;

import com.mola.molamod.MolaMod;
import com.mola.molamod.factory.CustomHandlerManager;
import com.mola.molamod.handlers.CustomItemHandler;
import com.mola.molamod.utils.LoggerUtil;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.apache.logging.log4j.Logger;

/**
 * @author : molamola
 * @Project: molamod
 * @Description: 注册总线
 * @date : 2020-11-03 18:42
 **/
@Mod.EventBusSubscriber(modid = MolaMod.MODID)
public class MolaEventBusSubscriber {

    private static Logger logger = LoggerUtil.getLogger();

    @SubscribeEvent
    public static void onItemRegister(RegistryEvent.Register<Item> event) {
        CustomItemHandler itemHandler = CustomHandlerManager.getItemHandler();

        // subscribe
        logger.info("[onItemRegister] 开始注册物品");
        CustomHandlerManager.getItemHandler().registerItemsAndRendering(event);
    }
}
