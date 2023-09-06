package xyz.iffyspeak.groupmanager.Tools.SQL;

import org.bukkit.Bukkit;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class SQLToolkit {

    public static void createTable(MySQL sql)
    {
        PreparedStatement ps;
        try {
            ps = sql.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS grouplist "
                    + "(NAME VARCHAR(100),UUID VARCHAR(100),GROUPNAME VARCHAR(100),PRIMARY KEY (UUID))");
            ps.executeUpdate();
        } catch (Exception e)
        {
            Bukkit.getLogger().severe(e.toString());
            //e.printStackTrace();
        }
    }

    public static void addPlayer(MySQL sql, String uuid, String name, String group)
    {
        try {
            if (!uuidExists(sql, uuid))
            {
                PreparedStatement ps = sql.getConnection().prepareStatement("INSERT IGNORE INTO grouplist(NAME,UUID,GROUPNAME) VALUES (?,?,?)");
                ps.setString(1, name);
                ps.setString(2, uuid);
                ps.setString(3, group);
                ps.executeUpdate();

                return;
            }

        } catch (Exception e)
        {
            Bukkit.getLogger().severe(e.toString());
            //e.printStackTrace();
        }
    }

    public static void setPlayerGroup(MySQL sql, String uuid, String group)
    {
        try {
            PreparedStatement ps = sql.getConnection().prepareStatement("UPDATE grouplist SET GROUPNAME=? WHERE UUID=?");
            ps.setString(1, group);
            ps.setString(2, uuid);
            ps.executeUpdate();
        } catch (Exception e)
        {
            Bukkit.getLogger().severe(e.toString());
            //e.printStackTrace();
        }
    }

    public static String getPlayerGroup(MySQL sql, String uuid, String group)
    {
        try {
            PreparedStatement ps = sql.getConnection().prepareStatement("SELECT GROUPNAME FROM grouplist WHERE UUID=?");
            ps.setString(1, uuid);
            ResultSet rs = ps.executeQuery();
            String rsgroup = "NoGroup";

            if (rs.next())
            {
                rsgroup = rs.getString("GROUPNAME");
                return rsgroup;
            }
        } catch (Exception e)
        {
            Bukkit.getLogger().severe(e.toString());
            //e.printStackTrace();
        }
        return "NoGroup";
    }

    public static List<String> getAllUUIDsInGroup(MySQL sql, String group)
    {
        List<String> uidret = new ArrayList<>();
        try {
            PreparedStatement ps = sql.getConnection().prepareStatement("SELECT * FROM grouplist WHERE GROUPNAME=?");
            ps.setString(1,group);
            ResultSet rs = ps.executeQuery();
            while (rs.next())
            {
                uidret.add(rs.getString("UUID"));
            }
            return uidret;
        } catch (Exception e)
        {
            Bukkit.getLogger().severe(e.toString());
            //e.printStackTrace();
        }
        return uidret;
    }

    public static boolean uuidExists(MySQL sql, String uuid)
    {
        try {
            PreparedStatement ps = sql.getConnection().prepareStatement("SELECT * FROM grouplist WHERE UUID=?");
            ps.setString(1, uuid);

            ResultSet results = ps.executeQuery();
            if (results.next())
            {
                // There is a player
                return true;
            }
            // No player
            return false;
        } catch (Exception e)
        {
            Bukkit.getLogger().severe(e.toString());
            //e.printStackTrace();
        }
        return false;
    }

    public static boolean groupExists(MySQL sql, String group)
    {
        try {
            PreparedStatement ps = sql.getConnection().prepareStatement("SELECT * FROM grouplist WHERE GROUP=?");
            ps.setString(1, group);

            ResultSet results = ps.executeQuery();
            if (results.next())
            {
                return true;
            }
            return false;
        } catch (Exception e)
        {
            Bukkit.getLogger().severe(e.toString());
            //e.printStackTrace();
            return false;
        }
    }

}
