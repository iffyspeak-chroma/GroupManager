package xyz.iffyspeak.groupmanager.PrivateCommandKit;

import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import xyz.iffyspeak.groupmanager.Tools.Globals;
import xyz.iffyspeak.groupmanager.Tools.SQL.SQLToolkit;

import java.util.List;

public class AddLifeCommand extends BasicCommand {

    public AddLifeCommand() {
        super(
                "addlife",
                "<green>Restore a life (or multiple) to a player</green>",
                "<red>addlife (username)</red><yellow> (amount)</yellow>",
                true);
    }

    @Override
    public void execute(List<String> args, Player sender) {
        boolean foundPlayer = false;
        if (args.isEmpty())
        {
            sender.sendMessage(MiniMessage.miniMessage().deserialize("<red>Not enough arguments<newline>::addlife (username) [amount]</red>"));
            return;
        }

        if (args.size() >= 2)
        {
            String username;
            int amount;
            try {
                username = args.get(0);
                amount = Integer.parseInt(args.get(1));
            } catch (Exception e)
            {
                sender.sendMessage(MiniMessage.miniMessage().deserialize("<red>Invalid argument. I don't think that's a number.</red>"));
                return;
            }

            for (Player p : Bukkit.getOnlinePlayers())
            {
                if (username.equals(p.getName()))
                {
                    String retstr = "<color:#00ff00>Added <yellow>" + amount + "</yellow> lives to<yellow> " + p.getName() + "</yellow>.</color>";
                    int curLives = SQLToolkit.getPlayerLives(Globals.Database.mySQL, String.valueOf(p.getUniqueId()));
                    SQLToolkit.setPlayerLives(Globals.Database.mySQL, String.valueOf(p.getUniqueId()), curLives + amount);
                    p.sendMessage(MiniMessage.miniMessage().deserialize(retstr));
                    sender.sendMessage(MiniMessage.miniMessage().deserialize(retstr));
                    foundPlayer = true;
                    return;
                }
            }
            sender.sendMessage(MiniMessage.miniMessage().deserialize("<red>Could not find a player by that name.</red>"));
            return;
        }

        for (Player p : Bukkit.getOnlinePlayers()) {
            if (args.get(0).equals(p.getName())) {
                String retstr = "<color:#00ff00>Added <yellow>1</yellow> life to<yellow> " + p.getName() + "</yellow>.</color>";
                int curLives = SQLToolkit.getPlayerLives(Globals.Database.mySQL, String.valueOf(p.getUniqueId()));
                SQLToolkit.setPlayerLives(Globals.Database.mySQL, String.valueOf(p.getUniqueId()), curLives + 1);
                p.sendMessage(MiniMessage.miniMessage().deserialize(retstr));
                sender.sendMessage(MiniMessage.miniMessage().deserialize(retstr));
                foundPlayer = true;
                return;
            }
        }
        sender.sendMessage(MiniMessage.miniMessage().deserialize("<red>Could not find a player by that name.</red>"));
    }
}
