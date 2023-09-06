package xyz.iffyspeak.groupmanager.Tools;

import dev.dejvokep.boostedyaml.YamlDocument;
import xyz.iffyspeak.groupmanager.Tools.SQL.MySQL;

import java.util.ArrayList;
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

    public static class Serialization
    {

    }

}