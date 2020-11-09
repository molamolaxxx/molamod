package com.mola.molamod.utils;

import net.minecraft.command.ICommandSender;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;

/**
 * @author : molamola
 * @Project: forge-1.12.2-14.23.5.2847-mdk
 * @Description:
 * @date : 2020-11-08 11:21
 **/
public class CommandUtil {

    public static void sendMessage(ICommandSender sender, String message) {
        sender.sendMessage(new TextComponentString(message));
    }

    public static void reportError(ICommandSender sender, String error) {
        TextComponentString errorTextComponents = new TextComponentString(error);
        Style style = new Style();
        style.setColor(TextFormatting.RED);
        errorTextComponents.setStyle(style);
        sender.sendMessage(errorTextComponents);
    }
}
