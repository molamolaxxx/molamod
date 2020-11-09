package com.mola.molamod.commands;

import com.mola.molamod.annotation.CustomCommand;
import com.mola.molamod.utils.CommandUtil;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;

/**
 * @author : molamola
 * @Project: forge-1.12.2-14.23.5.2847-mdk
 * @Description: 发送命令消息
 * @date : 2020-11-08 10:15
 **/
@CustomCommand
public class CommandSendMessage extends CommandBase {

    @Override
    public String getName() {
        return "molatest";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "commands.molatest.usage";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        CommandUtil.sendMessage(sender, "这是一条测试信息");
    }
}
