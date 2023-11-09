package xyz.iffyspeak.groupmanager.PrivateCommandKit;

import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import xyz.iffyspeak.groupmanager.Tools.Globals;
import xyz.iffyspeak.groupmanager.Tools.SQL.SQLToolkit;

import java.util.List;

public class AddAdminCommand extends BasicCommand {

    public AddAdminCommand() {
        super(
                "addadmin",
                "<green>Add an admin to the game</green>",
                "<red>addadmin (username)</red>",
                true);
    }

    @Override
    public void execute(List<String> args, Player sender) {
        boolean found = false;
        for (Player p : Bukkit.getOnlinePlayers())
        {
            if (args.get(0).equals(p.getName()))
            {
                String retstr = "<color:#00ff00>Successfully added <yellow>" + p.getName() + "</yellow> as an administrator</color>.";
                SQLToolkit.setPlayerGroup(Globals.Database.mySQL, String.valueOf(p.getUniqueId()), "administrator");
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
