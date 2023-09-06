package xyz.iffyspeak.groupmanager.Tools;

import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class EventListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent _e)
    {
        Player p = _e.getPlayer();
        if (Globals.All.GameAdministrators.contains(_e.getPlayer().getUniqueId()))
        {
            p.playerListName(MiniMessage.miniMessage().deserialize("<red><bold>ADMINISTRATOR </bold></red> " + p.getName()));
            p.sendPlayerListFooter(MiniMessage.miniMessage().deserialize("\n\n<red><bold>ADMINISTRATOR MODE</bold></red>\n\n"));
            p.sendPlayerListFooter(MiniMessage.miniMessage().deserialize("\n\n<red><bold>ON-DUTY</bold></red>\n<red>Go on. Do your job.</red>\n\n"));
        } else
        {
            PrefixedPlayer pp = new PrefixedPlayer(p, Globals.Prefixes.getRandomPrefix());
            Globals.Prefixes.prefixedPlayers.add(pp);
            p.playerListName(MiniMessage.miniMessage().deserialize(pp.getPrefix() + p.getName()));
            //p.playerListName(MiniMessage.miniMessage().deserialize(Globals.Prefixes.getRandomPrefix() + p.getName()));
        }
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent _e)
    {
        Player p = _e.getPlayer();
        for (int i = 0; i < Globals.Prefixes.prefixedPlayers.size(); i++)
        {
            if (Globals.Prefixes.prefixedPlayers.get(i).getPlayer().equals(p))
            {
                Globals.Prefixes.prefixedPlayers.remove(i);
                break;
            }
        }
    }

    @EventHandler
    public void onPlayerChat(AsyncChatEvent _e)
    {
        // We really prefer if that original, ugly, message didn't get out
        _e.setCancelled(true);

        Component prefix = null;
        Component name = MiniMessage.miniMessage().deserialize(_e.getPlayer().getName() + ": ");

        // I want to allow people to use MiniMessage format as well. So this workaround does a good enough job.
        String message = MiniMessage.miniMessage().serialize(_e.message());
        message = message.replace("\\<","<");

        Component build = Component.text("");

        if (Globals.All.GameAdministrators.contains(_e))
        {
            prefix = MiniMessage.miniMessage().deserialize("<color:#ff0000><b>ADMINISTRATOR</b></color> ");
        } else
        {
            for (PrefixedPlayer pp : Globals.Prefixes.prefixedPlayers)
            {
                if (pp.getPlayer().equals(_e.getPlayer()))
                {
                    prefix = MiniMessage.miniMessage().deserialize(pp.getPrefix());
                    break;
                }
            }
        }

        build = build.append(prefix).append(name).append(MiniMessage.miniMessage().deserialize(message));

        for (Audience p : _e.viewers())
        {
            p.sendMessage(build);
        }
    }
}
