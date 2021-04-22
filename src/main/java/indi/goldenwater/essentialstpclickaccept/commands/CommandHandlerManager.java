package indi.goldenwater.essentialstpclickaccept.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class CommandHandlerManager {
    private final PluginCommand command;
    private final Map<String, CommandHandler> commandHandlers = new HashMap<>();

    CommandHandlerManager(String mainCommand) {
        command = Bukkit.getPluginCommand(mainCommand);
    }

    public void register() {
        command.setExecutor((sender, command, s, args) -> {
            if (args.length == 0) { // 当无指令参数时
                CommandHandler handler = commandHandlers.get("__main__"); // 获取名为__main__的指令处理类
                return handler != null &&
                        handler.handle(sender, null); // 如果为空则返回false 如果有则以空参数运行处理器

            } else { // 当有指令参数时
                CommandHandler handler = commandHandlers.get(args[1]); // 获取指令处理

                if (handler == null) {
                    handler = commandHandlers.get("__main__"); // 获取名为__main__的指令处理器
                    return handler != null &&
                            handler.handle(sender, args); // 如果为空则返回false 如果有则带参数运行处理器
                }

                return handler.handle(sender,
                        Arrays.asList(args)
                                .subList(1, args.length)
                                .toArray(new String[]{})); // 带参数运行处理
            }
        });
    }

    public void setHandler(String subCommand, CommandHandler commandHandler) {
        if (subCommand != null && commandHandler != null) {
            commandHandlers.put(subCommand, commandHandler);
        }
    }
    
    public Command getCommand() {
        return command;
    }

    public interface CommandHandler {
        boolean handle(CommandSender commandSender, String[] args);
    }
}
