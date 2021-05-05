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
 * @Description:
 * @date : 2021-05-02 12:32
 **/
@CustomCommand
public class MyTestCommand extends CommandBase {

    @Override
    public String getName() {
        return "myTest";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "commands.myTest.usage";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        CommandUtil.sendMessage(sender, this.getClass().getName());
    }
}
