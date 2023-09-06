package xyz.iffyspeak.groupmanager.Tools;

import dev.dejvokep.boostedyaml.YamlDocument;
import xyz.iffyspeak.groupmanager.Tools.SQL.MySQL;

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

}