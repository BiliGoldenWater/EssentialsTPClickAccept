package indi.goldenwater.essentialstpclickaccept.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;

import java.util.*;

public class CommandHandlerManager {
    private final Map<String, CommandHandler> commandHandlers = new HashMap<>();
    private final List<String> subcommands = new ArrayList<>();
    private final String mainCommandName;
    private final PluginCommand command;
    private String mainCommandReplacer = "_ _main_ _";

    CommandHandlerManager(String mainCommand) {
        mainCommandName = mainCommand;
        command = Bukkit.getPluginCommand(mainCommand);
    }

    public void register() {
        command.setExecutor((sender, command, s, args) -> {
            if (args.length == 0) { // 当无指令参数时
                CommandHandler handler = commandHandlers.get(mainCommandReplacer); // 获取名为__main__的指令处理类
                return handler != null &&
                        handler.handle(sender, mainCommandName, new String[]{}); // 如果为空则返回false 如果有则以空参数运行处理器

            } else { // 当有指令参数时
                CommandHandler handler = commandHandlers.get(args[0].toLowerCase()); // 获取指令处理

                if (handler == null) {
                    handler = commandHandlers.get(mainCommandReplacer); // 获取名为__main__的指令处理器
                    return handler != null &&
                            handler.handle(sender, mainCommandName, args); // 如果为空则返回false 如果有则带参数运行处理器
                }

                return handler.handle(sender,
                        args[0].toLowerCase(),
                        Arrays.asList(args)
                                .subList(1, args.length)
                                .toArray(new String[]{})); // 带参数运行处理
            }
        });
    }

    public void registerHandler(String subCommand, CommandHandler commandHandler) {
        if (subCommand != null && commandHandler != null) {
            commandHandlers.put(subCommand.toLowerCase(), commandHandler);
            subcommands.add(subCommand.toLowerCase());
        }
    }

    public void setMainCommandReplacer(String replacer) {
        mainCommandReplacer = replacer;
    }

    public void unregisterHandler(String subCommand) {
        commandHandlers.remove(subCommand.toLowerCase());
        subcommands.remove(subCommand.toLowerCase());
    }

    public Command getCommand() {
        return command;
    }

    public String getMainCommandReplacer() {
        return mainCommandReplacer;
    }

    public Map<String, CommandHandler> getCommandHandlers() {
        return commandHandlers;
    }

    public List<String> getSubcommands() {
        return subcommands;
    }

    public interface CommandHandler {
        boolean handle(CommandSender commandSender, String commandName, String[] args);
    }
}
