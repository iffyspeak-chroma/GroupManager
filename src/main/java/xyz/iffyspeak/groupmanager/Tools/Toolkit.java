package xyz.iffyspeak.groupmanager.Tools;

import dev.dejvokep.boostedyaml.YamlDocument;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import xyz.iffyspeak.groupmanager.Tools.SQL.MySQL;
import xyz.iffyspeak.groupmanager.Tools.SQL.SQLToolkit;

import java.util.*;

public class Toolkit {
    public static class Configurations {

        public static class InitalizeAdminsList
        {
            public static void FromConfig(YamlDocument config)
            {
            /*
            Globals.GameAdministrators.add(UUID.fromString("2f081834-bfb9-4374-a0bc-eab8fb2514eb")); // hmmAlyssiaa
            Globals.GameAdministrators.add(UUID.fromString("df7da26b-f9da-4758-8250-b4f0c9bebfe3")); // iffyspeak
             */
                for (Object suid : config.getList("administrators"))
                {
                    Globals.All.GameAdministrators.add(UUID.fromString(suid.toString()));
                    //Toolkit.Logger.LogErr(Toolkit.Logger.DetermineSoftware(), suid.toString());
                }
            }

            public static void FromSQL(MySQL sql)
            {
                for (String uid : SQLToolkit.getAllUUIDsInGroup(sql, "administrator"))
                {
                    Globals.All.GameAdministrators.add(UUID.fromString(uid));
                    //Toolkit.Logger.LogErr(Toolkit.Logger.DetermineSoftware(), uid);
                }
            }
        }

        public static boolean IsRegisteredAdministrator(Player player)
        {
            // Look through the list of already loaded administrators
            if (Globals.All.GameAdministrators.contains(player.getUniqueId()))
            {
                return true;
            }

            if (Globals.Database.mySQL != null)
            {
                for (String uid : SQLToolkit.getAllUUIDsInGroup(Globals.Database.mySQL, "administrator"))
                {
                    if (player.getUniqueId() == UUID.fromString(uid))
                    {
                        // Player is not already on the loaded list, so we'll put them on there.
                        
                        return true;
                    }
                }
            }
            return false;
        }
    }

    public static class RNG {
        public static int RandomRange(int high, int low)
        {
            return (new Random().nextInt(high-low) + low);
        }
    }
}
