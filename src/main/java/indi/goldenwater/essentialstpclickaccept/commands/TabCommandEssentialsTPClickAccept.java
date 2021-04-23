package indi.goldenwater.essentialstpclickaccept.commands;

import indi.goldenwater.essentialstpclickaccept.EssentialsTPClickAccept;

import java.util.ArrayList;
import java.util.List;

import static indi.goldenwater.essentialstpclickaccept.utils.CheckPermissions.hasPermissions;

public class TabCommandEssentialsTPClickAccept {
    public static void register(String commandName, EssentialsTPClickAccept pluginInstance,
                                CommandHandlerManager handlerManager, String permHead) {
        pluginInstance.getCommand(commandName).setTabCompleter((sender, command, s, args) -> {
            List<String> completions = new ArrayList<>();

            if (args.length == 1) {
                List<String> subcommands = handlerManager.getSubcommands();
                for (String subcommand : subcommands) {
                    if (hasPermissions(sender, permHead + subcommand) &&
                            subcommand.toLowerCase().startsWith(args[0].toLowerCase())) {
                        completions.add(subcommand);
                    }
                }
            } else if (args.length == 2) {
                if (args[0].equals("lang") && hasPermissions(sender, permHead + "lang")) {
                    List<String> languages = pluginInstance.getI18n().getLanguageList();
                    for (String language : languages) {
                        if (language.toLowerCase().startsWith(args[1].toLowerCase())) {
                            completions.add(language);
                        }
                    }
                }
            }

            return completions;
        });
    }
}
