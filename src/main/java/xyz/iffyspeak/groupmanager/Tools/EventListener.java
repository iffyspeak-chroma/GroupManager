package xyz.iffyspeak.groupmanager.Tools;

import io.papermc.paper.event.player.AsyncChatEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class EventListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent _e)
    {
        if (Globals.All.GameAdministrators.contains(_e.getPlayer().getUniqueId()))
        {

        }
    }

    @EventHandler
    public void onPlayerChat(AsyncChatEvent _e)
    {

    }
}
