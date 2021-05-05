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
 * @Description: 添加等级
 * @date : 2021-05-05 12:46
 **/
@CustomCommand
public class CommandAddLevel extends CommandBase {
    @Override
    public String getName() {
        return "addLevel";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "command.addLevel.usage";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if (args.length != 1) {
            CommandUtil.reportError(sender, "参数错误，后面接等级");
            return;
        }
        server.getPlayerList().getPlayers().forEach(p -> {
            p.addExperienceLevel(Integer.valueOf(args[0]));
        });
    }
}
