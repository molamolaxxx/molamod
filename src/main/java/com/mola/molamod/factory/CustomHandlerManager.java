package com.mola.molamod.factory;

import com.mola.molamod.handlers.CustomCommandHandler;
import com.mola.molamod.handlers.CustomItemHandler;

/**
 * @author : molamola
 * @Project: molamod
 * @Description: handler单例工厂
 * @date : 2020-11-03 18:35
 **/
public class CustomHandlerManager {

    /**
     * item管理
     */
    private static final CustomItemHandler itemHandler = new CustomItemHandler();

    /**
     * command管理
     */
    private static final CustomCommandHandler commandHandler = new CustomCommandHandler();

    /**
     * 获取item-handler
     * @return
     */
    public static CustomItemHandler getItemHandler() {
        return CustomHandlerManager.itemHandler;
    }

    public static CustomCommandHandler getCommandHandler() {
        return CustomHandlerManager.commandHandler;
    }
}
