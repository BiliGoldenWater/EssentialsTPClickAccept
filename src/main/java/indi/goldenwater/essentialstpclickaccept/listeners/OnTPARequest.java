package indi.goldenwater.essentialstpclickaccept.listeners;

import indi.goldenwater.essentialstpclickaccept.EssentialsTPClickAccept;
import indi.goldenwater.essentialstpclickaccept.utils.I18nManager;
import net.ess3.api.events.TPARequestEvent;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.chat.ComponentSerializer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;

import static indi.goldenwater.essentialstpclickaccept.utils.CheckPermissions.hasPermissions;

public class OnTPARequest implements Listener {

    @EventHandler
    public void onTPARequest(TPARequestEvent event) {
        new BukkitRunnable() {

            @Override
            public void run() {
                EssentialsTPClickAccept instance = EssentialsTPClickAccept.getInstance();
                I18nManager i18n = instance.getI18n();
                String lang = instance.getConfig().getString("lang");
                Player requester = event.getRequester().getPlayer();
                Player target = event.getTarget().getBase();

                BaseComponent[] messageToTarget = ComponentSerializer.parse(
                        i18n.getL10n(lang, "event_tpRequest_target" + (event.isTeleportHere() ? "_tpHere" : ""))
                                .replace("'", "\""));
                BaseComponent[] messageToRequester = ComponentSerializer.parse(
                        i18n.getL10n(lang, "event_tpRequest_requester")
                                .replace("'", "\""));

                if (hasPermissions(target, "essentialstpclickaccept.showcam")) {
                    target.spigot().sendMessage(messageToTarget);
                }
                if (hasPermissions(requester, "essentialstpclickaccept.showcam")) {
                    requester.spigot().sendMessage(messageToRequester);
                }
            }
        }.runTaskLaterAsynchronously(EssentialsTPClickAccept.getInstance(), 1);
    }
}
