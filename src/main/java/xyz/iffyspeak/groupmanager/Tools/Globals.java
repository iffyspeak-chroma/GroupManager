package xyz.iffyspeak.groupmanager.Tools;

import dev.dejvokep.boostedyaml.YamlDocument;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import xyz.iffyspeak.groupmanager.PrivateCommandKit.BasicCommand;
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
        public static List<BasicCommand> registeredCommands = new ArrayList<>();
        public static void RegisterCommand(BasicCommand command)
        {
            registeredCommands.add(command);
        }
        public static void ProcessCommand(Player sender, String command)
        {
            // Strip out inital :: and split into args
            // args[0] should be the identifier
            // Everything after 0 are args
            String[] informalArgs = command.replaceFirst("::", "").split(" ");
            List<String> formalArgs = Arrays.asList(informalArgs);

            boolean foundCommand = false;
            for (BasicCommand cmd : registeredCommands)
            {
                // Search for the command
                if (formalArgs.get(0).equals(cmd.getName()))
                {
                    // Found the command, check if it needs admin to run
                    foundCommand = true;
                    if (cmd.requiresAdmin())
                    {
                        // Check if the sender has admin
                        if (SQLToolkit.IsPlayerInGroup(Database.mySQL, String.valueOf(sender.getUniqueId()), "administrator"))
                        {
                            // Execute the command
                            formalArgs.remove(0);
                            cmd.execute(formalArgs, sender);
                            return;
                        } else
                        {
                            // No permission
                            sender.sendMessage(MiniMessage.miniMessage().deserialize("<b><red>Silly goober. You cannot run that</red></b>"));
                            return;
                        }
                    } else
                    {
                        // Execute command
                        formalArgs.remove(0);
                        cmd.execute(formalArgs, sender);
                    }
                }
            }
            if (!foundCommand)
            {
                // Command does not exist
                sender.sendMessage(MiniMessage.miniMessage().deserialize("<b><red>That command does not exist</red></b>"));
            }
        }
    }

}