package xyz.iffyspeak.groupmanager.Tools;

import dev.dejvokep.boostedyaml.YamlDocument;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import xyz.iffyspeak.groupmanager.Tools.SQL.MySQL;
import xyz.iffyspeak.groupmanager.Tools.SQL.SQLToolkit;

import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class Globals {

    public static class All
    {
        public static YamlDocument configuration = null;
        public static List<UUID> GameAdministrators = new ArrayList<UUID>() {};
    }

    public static class Database
    {
        public static boolean useDatabase = false;
        public static String dbHost = "localhost";
        public static String dbPort = "3306";
        public static String dbDatabase = "iffyspeak";
        public static String dbUsername = "plugin";
        public static String dbPassword = "examplepassword";
        public static boolean dbUseSSL = false;
        public static String useSsl()
        {
            return (dbUseSSL ? "true" : "false");
        }
        public static MySQL mySQL = null;
    }

    public static class Prefixes
    {
        public static List<PrefixedPlayer> prefixedPlayers = new ArrayList<>();
        public static List<String> getPrefixesList()
        {
            return Arrays.asList(
                    "<color:#0400ff><b>PLAYER</b></color> ",
                    "<color:#ff00bb><b>COMPETITOR</b></color> ",
                    "<color:#ff4400><b>MEMBER</b></color> ",
                    "<color:#00ffc8><b>PARTICIPANT</b></color> ",
                    "<color:#e196ff><b>ENTERTAINER</b></color> ",
                    "<color:#a6ffdd><b>PERFORMER</b></color> ",
                    "<color:#ff9a57><b>PAWN</b></color> "
                    );
        }

        public static String getRandomPrefix()
        {
            return getPrefixesList().get(Toolkit.RNG.RandomRange(getPrefixesList().size(), 0));
        }
    }

    public static class Commands {
        public static void ProcessCommand(Player sender, String command)
        {
            // Strip out inital :: and split into args
            // args[0] should be the identifier
            // Everything after 0 are args
            String[] args = command.replaceFirst("::", "").split(" ");

            // Any command from the processor here on out should only be ran by people who are qualified
            if (SQLToolkit.IsPlayerInGroup(Database.mySQL, String.valueOf(sender.getUniqueId()), "administrator"))
            {
                // They are administrator. We can do what they want

                if (args[0].equals("addadmin")) // Add another admin to the list
                {
                    for (Player p : Bukkit.getOnlinePlayers())
                    {
                        if (args[1].equals(p.getName()))
                        {
                            String retstr = "<color:#00ff00>Successfully added <yellow>" + p.getName() + "</yellow> as an administrator</color>.";
                            SQLToolkit.setPlayerGroup(Database.mySQL, String.valueOf(p.getUniqueId()), "administrator");
                            sender.sendMessage(MiniMessage.miniMessage().deserialize(retstr));
                            return;
                        }
                    }
                    sender.sendMessage(MiniMessage.miniMessage().deserialize("<red>Could not find a player by that name.</red>"));
                    return;
                }

                if (args[0].equals("remadmin")) // Remove an admin from the list
                {
                    for (Player p : Bukkit.getOnlinePlayers())
                    {
                        if (args[1].equals(p.getName()))
                        {
                            String retstr = "<color:#00ff00>Successfully removed <yellow>" + p.getName() + "</yellow> as an administrator</color>.";
                            SQLToolkit.setPlayerGroup(Database.mySQL, String.valueOf(p.getUniqueId()), "NoGroup");
                            sender.sendMessage(MiniMessage.miniMessage().deserialize(retstr));
                            return;
                        }
                    }
                    sender.sendMessage(MiniMessage.miniMessage().deserialize("<red>Could not find a player by that name.</red>"));
                    return;
                }

                if (args[0].equals("addlife")) // Add lives to a player
                {
                    if (args.length < 2)
                    {
                        sender.sendMessage(MiniMessage.miniMessage().deserialize("<red>Not enough arguments<newline>::addlife (username) [amount]</red>"));
                        return;
                    }

                    if (args.length >= 3)
                    {
                        String username;
                        int amount;
                        try {
                            username = args[1];
                            amount = Integer.parseInt(args[2]);
                        } catch (Exception e)
                        {
                            sender.sendMessage(MiniMessage.miniMessage().deserialize("<red>Invalid argument. I don't think that's a number.</red>"));
                            return;
                        }

                        for (Player p : Bukkit.getOnlinePlayers())
                        {
                            if (username.equals(p.getName()))
                            {
                                String retstr = "<color:#00ff00>Added <yellow>" + amount + "</yellow> lives <yellow>to " + p.getName() + "</yellow>.</color>";
                                int curLives = SQLToolkit.getPlayerLives(Database.mySQL, String.valueOf(p.getUniqueId()));
                                SQLToolkit.setPlayerLives(Database.mySQL, String.valueOf(p.getUniqueId()), curLives + amount);
                                p.sendMessage(MiniMessage.miniMessage().deserialize(retstr));
                                return;
                            }
                        }
                        sender.sendMessage(MiniMessage.miniMessage().deserialize("<red>Could not find a player by that name.</red>"));
                        return;
                    }

                    if (args.length == 2)
                    {
                        for (Player p : Bukkit.getOnlinePlayers())
                        {
                            if (args[1].equals(p.getName()))
                            {
                                String retstr = "<color:#00ff00>Added <yellow>1</yellow> life <yellow>to " + p.getName() + "</yellow>.</color>";
                                int curLives = SQLToolkit.getPlayerLives(Database.mySQL, String.valueOf(p.getUniqueId()));
                                SQLToolkit.setPlayerLives(Database.mySQL, String.valueOf(p.getUniqueId()), curLives + 1);
                                p.sendMessage(MiniMessage.miniMessage().deserialize(retstr));
                            }
                        }
                        sender.sendMessage(MiniMessage.miniMessage().deserialize("<red>Could not find a player by that name.</red>"));
                    }
                }
            }
        }
    }

}