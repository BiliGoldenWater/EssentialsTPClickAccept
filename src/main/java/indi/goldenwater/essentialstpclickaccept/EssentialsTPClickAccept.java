package indi.goldenwater.essentialstpclickaccept;

import indi.goldenwater.essentialstpclickaccept.commands.CommandEssentialsTPClickAccept;
import indi.goldenwater.essentialstpclickaccept.listeners.OnTPARequest;
import indi.goldenwater.essentialstpclickaccept.utils.I18nManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class EssentialsTPClickAccept extends JavaPlugin {
    private static EssentialsTPClickAccept instance;
    private I18nManager i18n;

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;

        i18n = new I18nManager(getDataFolder(), "lang", "en_US");
        i18n.releaseDefaultLangFile(this, "lang", "langList.json", false);

        Bukkit.getPluginManager().registerEvents(new OnTPARequest(), this);
        CommandEssentialsTPClickAccept.register("essentialstpclickaccept", this);

        getLogger().info("Enabled.");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getLogger().info("Disabled.");
    }

    public static EssentialsTPClickAccept getInstance() {
        return instance;
    }

    public I18nManager getI18n() {
        return i18n;
    }
}
