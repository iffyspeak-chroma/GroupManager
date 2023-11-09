package xyz.iffyspeak.groupmanager.PrivateCommandKit;

import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import xyz.iffyspeak.groupmanager.Tools.Globals;
import xyz.iffyspeak.groupmanager.Tools.SQL.SQLToolkit;

import java.util.List;

public class RemoveAdminCommand extends BasicCommand {
    public RemoveAdminCommand() {
        super(
                "remadmin",
                "<green>Remove an admin from the game</green>",
                "<red>remadmin (username)</red>",
                true);
    }

    @Override
    public void execute(List<String> args, Player sender) {
        boolean found = false;
        for (Player p : Bukkit.getOnlinePlayers())
        {
            if (args.get(0).equals(p.getName()))
            {
                String retstr = "<color:#00ff00>Successfully removed <yellow>" + p.getName() + "</yellow> as an administrator</color>.";
                SQLToolkit.setPlayerGroup(Globals.Database.mySQL, String.valueOf(p.getUniqueId()), "NoGroup");
                sender.sendMessage(MiniMessage.miniMessage().deserialize(retstr));
                found = true;
                return;
            }
        }
        if (!found)
        {
            sender.sendMessage(MiniMessage.miniMessage().deserialize("<red>Could not find a player by that name.</red>"));
        }
    }
}
