package indi.goldenwater.essentialstpclickaccept.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;

public class CommandEssentialsTPClickAccept {
    private static JavaPlugin plugin;

    public static void register(String mainCommand, JavaPlugin pluginInstance) {
        plugin = pluginInstance;
        CommandHandlerManager commandHandlerManager = new CommandHandlerManager(mainCommand);
        commandHandlerManager.register();

        commandHandlerManager.setHandler("__main__", (commandSender, args) -> {
            commandSender.sendMessage(Arrays.toString(args));
            return true;
        });

        commandHandlerManager.setHandler("help", (commandSender, args) -> {
            commandSender.sendMessage(Arrays.toString(args));
            return true;
        });
    }
}
