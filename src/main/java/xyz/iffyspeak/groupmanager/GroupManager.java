package xyz.iffyspeak.groupmanager;

import dev.dejvokep.boostedyaml.YamlDocument;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import xyz.iffyspeak.groupmanager.PrivateCommandKit.*;
import xyz.iffyspeak.groupmanager.Tools.EventListener;
import xyz.iffyspeak.groupmanager.Tools.Globals;
import xyz.iffyspeak.groupmanager.Tools.GlowEffect;
import xyz.iffyspeak.groupmanager.Tools.SQL.MySQL;
import xyz.iffyspeak.groupmanager.Tools.SQL.SQLToolkit;
import xyz.iffyspeak.groupmanager.Tools.Toolkit;

import java.io.File;

public final class GroupManager extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        Globals.Commands.RegisterCommand(new HelpCommand());
        Globals.Commands.RegisterCommand(new RemoveAdminCommand());
        Globals.Commands.RegisterCommand(new AddAdminCommand());
        Globals.Commands.RegisterCommand(new AddLifeCommand());
        Globals.Commands.RegisterCommand(new RemoveLifeCommand());

        // Attempt load config
        try {
            Globals.All.configuration = YamlDocument.create(new File(getDataFolder(), "configuration.yml"), getResource("configuration.yml"));
        } catch (Exception e)
        {
            getLogger().severe(e.toString());
        }

        if (Globals.All.configuration.getBoolean("database.enabled"))
        {
            Globals.Database.useDatabase = true;
            try {
                Globals.Database.dbHost = Globals.All.configuration.getString("database.host");
                Globals.Database.dbPort = Globals.All.configuration.getString("database.port");
                Globals.Database.dbDatabase = Globals.All.configuration.getString("database.database");
                Globals.Database.dbUsername = Globals.All.configuration.getString("database.username");
                Globals.Database.dbPassword = Globals.All.configuration.getString("database.password");
                Globals.Database.dbUseSSL = Globals.All.configuration.getBoolean("database.useSSL");

                Globals.Database.mySQL = new MySQL();
                Globals.Database.mySQL.connect();
            } catch (Exception e)
            {
                Bukkit.getLogger().severe(e.toString());
                Bukkit.getLogger().severe("Unable to connect to database.\nDo you have the right credentials?");
            }
        } else
        {
            Bukkit.getLogger().info("Not using databases.");
            Toolkit.Configurations.InitalizeAdminsList.FromConfig(Globals.All.configuration);
        }

        if (Globals.Database.mySQL != null)
        {
            if (Globals.Database.mySQL.isConnected())
            {
                Bukkit.getLogger().info("Successfully connected to database.");
                Bukkit.getLogger().info("Checking tables");
                SQLToolkit.createTable(Globals.Database.mySQL);
                Bukkit.getLogger().info("Initializing Admin List");
                Toolkit.Configurations.InitalizeAdminsList.FromSQL(Globals.Database.mySQL);
            }
        }

        getServer().getPluginManager().registerEvents(new EventListener(), this);

        GlowEffect.clearColorTeams();
        GlowEffect.setupColorTeams();
        getServer().getScheduler().scheduleSyncRepeatingTask(this, () -> {
            for (Player p : Bukkit.getOnlinePlayers())
            {
                if (Globals.All.GameAdministrators.contains(p.getUniqueId()))
                {
                    GlowEffect.setGlow(p, NamedTextColor.DARK_RED);
                }
            }
        }, 0, 20);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        if (Globals.Database.mySQL != null)
        {
            Globals.Database.mySQL.disconnect();
        }
        GlowEffect.clearColorTeams();
    }
}
