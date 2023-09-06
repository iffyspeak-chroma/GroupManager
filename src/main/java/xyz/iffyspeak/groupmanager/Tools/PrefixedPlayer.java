package xyz.iffyspeak.groupmanager.Tools;

import org.bukkit.entity.Player;

public class PrefixedPlayer {
    private Player player;
    private String prefix;

    public PrefixedPlayer(Player p, String pr)
    {
        this.player = p;
        this.prefix = pr;
    }

    public Player getPlayer()
    {
        return this.player;
    }

    public String getPrefix()
    {
        return this.prefix;
    }
}
