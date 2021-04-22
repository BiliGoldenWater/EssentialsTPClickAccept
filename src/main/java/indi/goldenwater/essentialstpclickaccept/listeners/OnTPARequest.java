package indi.goldenwater.essentialstpclickaccept.listeners;

import indi.goldenwater.essentialstpclickaccept.EssentialsTPClickAccept;
import net.ess3.api.events.TPARequestEvent;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;

public class OnTPARequest implements Listener {

    @EventHandler
    public void onTPARequest(TPARequestEvent event) {
        new BukkitRunnable() {

            @Override
            public void run() {
                Player requester = event.getRequester().getPlayer();
                Player target = event.getTarget().getBase();

                TextComponent messageToTarget = new TextComponent();
                TextComponent messageToRequester = new TextComponent();
                TextComponent accept = new TextComponent("点击接受");
                TextComponent deny = new TextComponent("点击拒绝");
                TextComponent cancel = new TextComponent("点击取消");

                accept.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/tpaccept"));
                accept.setColor(ChatColor.GREEN);
                accept.setUnderlined(true);

                deny.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/tpdeny"));
                deny.setColor(ChatColor.RED);
                deny.setUnderlined(true);

                cancel.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/tpacancel"));
                cancel.setColor(ChatColor.RED);
                cancel.setUnderlined(true);

                messageToTarget.addExtra(accept);
                messageToTarget.addExtra("§7 | §r");
                messageToTarget.addExtra(deny);

                messageToRequester.addExtra(cancel);

                target.spigot().sendMessage(messageToTarget);
                requester.spigot().sendMessage(messageToRequester);
            }
        }.runTaskLaterAsynchronously(EssentialsTPClickAccept.getInstance(), 1);
    }
}
