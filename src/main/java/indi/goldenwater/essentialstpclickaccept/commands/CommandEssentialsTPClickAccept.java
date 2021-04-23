package indi.goldenwater.essentialstpclickaccept.commands;

import indi.goldenwater.essentialstpclickaccept.EssentialsTPClickAccept;
import indi.goldenwater.essentialstpclickaccept.utils.I18nManager;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.Configuration;

import static indi.goldenwater.essentialstpclickaccept.utils.CheckPermissions.hasPermissions_Tips;

public class CommandEssentialsTPClickAccept {
    private static EssentialsTPClickAccept plugin;
    private static Configuration config;
    private static I18nManager i18n;
    private static String lang;
    private static String noPerm;

    public static void register(String mainCommand, EssentialsTPClickAccept pluginInstance) {
        plugin = pluginInstance;
        String permHead = "essentialstpclickaccept.command.";
        CommandHandlerManager handlerManager = new CommandHandlerManager(mainCommand);
        handlerManager.register();

        handlerManager.registerHandler("_ _main_ _", (sender, command, args) -> {
            update();
            if (!hasPermissions_Tips(sender, permHead + command, noPerm)) return true;
            HelpMessageManager.sendHelpMessage(sender, HelpMessageManager.HelpLvl.full);
            return true;
        });

        handlerManager.registerHandler("help", (sender, command, args) -> {
            update();
            if (!hasPermissions_Tips(sender, permHead + command, noPerm)) return true;

            HelpMessageManager.sendHelpMessage(sender, HelpMessageManager.HelpLvl.full);

            return true;
        });

        handlerManager.registerHandler("lang", (sender, command, args) -> {
            update();
            if (!hasPermissions_Tips(sender, permHead + command, noPerm)) return true;
            String targetLanguage = null;

            if (args.length == 0) {
                HelpMessageManager.sendHelpMessage(sender, HelpMessageManager.HelpLvl.lang); // 发送命令用法
            } else {
                targetLanguage = args[0];
            }

            i18n.setLanguage(plugin,
                    sender,
                    lang,
                    targetLanguage,
                    "lang",
                    "cmd_lang_list",
                    "cmd_lang_successSet",
                    "cmd_lang_setFail",
                    "cmd_lang_setFail_doesNotExist");

            return true;
        });

        handlerManager.registerHandler("resetlang", (sender, command, args) -> {
            update();
            if (!hasPermissions_Tips(sender, permHead + command, noPerm)) return true;

            plugin.getI18n()
                    .releaseDefaultLangFile(plugin, "lang", "langList.json", true);
            sender.sendMessage(i18n.getL10n(lang, "cmd_resetLang_resetFinish"));
            return true;
        });

        handlerManager.registerHandler("reload", (sender, command, args) -> {
            update();
            if (!hasPermissions_Tips(sender, permHead + command, noPerm)) return true;
            plugin.getI18n().reload();
            plugin.reloadConfig();

            update();
            sender.sendMessage(i18n.getL10n(lang, "cmd_reload_success"));
            return true;
        });

        TabCommandEssentialsTPClickAccept.register("essentialstpclickaccept",
                plugin,
                handlerManager,
                permHead);
    }

    public static void update() {
        config = plugin.getConfig();
        i18n = plugin.getI18n();
        lang = config.getString("lang");
        noPerm = i18n.getL10n(lang, "error_noPerm");
    }

    public static class HelpMessageManager {
        public static void sendHelpMessage(CommandSender sender, int helpLvl) {
            sender.sendMessage("EssentialsTPClickAccept by.Golden_Water");
            switch (helpLvl) {
                case HelpLvl.full:
                    sender.sendMessage(i18n.getL10n(lang, "help_usage_help"));
                    sender.sendMessage(i18n.getL10n(lang, "help_usage_lang"));
                    sender.sendMessage(i18n.getL10n(lang, "help_usage_resetLang"));
                    sender.sendMessage(i18n.getL10n(lang, "help_usage_reload"));
                    break;
                case HelpLvl.help:
                    sender.sendMessage(i18n.getL10n(lang, "help_usage_help"));
                    break;
                case HelpLvl.lang:
                    sender.sendMessage(i18n.getL10n(lang, "help_usage_lang"));
                    break;
                case HelpLvl.resetlang:
                    sender.sendMessage(i18n.getL10n(lang, "help_usage_resetLang"));
                    break;
                case HelpLvl.reload:
                    sender.sendMessage(i18n.getL10n(lang, "help_usage_reload"));
                    break;
            }
        }

        public static class HelpLvl {
            public static final int full = 0;
            public static final int help = 1;
            public static final int lang = 2;
            public static final int resetlang = 3;
            public static final int reload = 4;
        }
    }
}
