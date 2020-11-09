package com.mola.molamod.handlers;

import com.google.common.collect.Maps;
import net.minecraft.command.ICommand;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author : molamola
 * @Project: Evolution-Of-Knowledge
 * @Description: 命令管理机类
 * @date : 2020-11-04 16:59
 **/
public class CustomCommandHandler{
    private Map<String, ICommand> commandMap = Maps.newConcurrentMap();

    public void add(ICommand command) {
        commandMap.putIfAbsent(command.getName(), command);
    }

    public ICommand getCommand(String command) {
        return commandMap.get(command);
    }

    public List<ICommand> getCommandList() {
        return commandMap.entrySet().stream()
                .map(e -> e.getValue())
                .collect(Collectors.toList());
    }

    /**
     * 在总线上注册command
     * @param event
     */
    public void registerCommand(FMLServerStartingEvent event) {
        for (ICommand command : this.getCommandList()) {
            event.registerServerCommand(command);
        }
    }
}
