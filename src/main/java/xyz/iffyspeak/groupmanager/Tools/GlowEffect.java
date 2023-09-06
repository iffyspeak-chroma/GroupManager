package xyz.iffyspeak.groupmanager.Tools;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.HashMap;
import java.util.Map;

public class GlowEffect {

    // Written by Qruet
    // https://github.com/qruet
    @SuppressWarnings("FieldMayBeFinal")
    private static Map<String, String> Entries = new HashMap<>();

    //called on enable
    public static void setupColorTeams() {
        Scoreboard scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();
        Team team = scoreboard.registerNewTeam("GLOW_COLOR_RED");
        //team.prefix(MiniMessage.miniMessage().deserialize("<color:#ff0000><b>ADMINISTRATOR</b></color> "));
        team.setOption(Team.Option.DEATH_MESSAGE_VISIBILITY, Team.OptionStatus.ALWAYS);
        team.setOption(Team.Option.NAME_TAG_VISIBILITY, Team.OptionStatus.ALWAYS);
        team.setOption(Team.Option.COLLISION_RULE, Team.OptionStatus.NEVER);
    }

    //called on disable
    public static void clearColorTeams() {
        Scoreboard scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();
        for (Team team : scoreboard.getTeams()) {
            if (team.getName().contains("GLOW_COLOR_")) {
                team.unregister();
            }
        }
    }

    public static void setGlow(Entity entity, NamedTextColor color) {
        Team team = Bukkit.getScoreboardManager().getMainScoreboard().getTeam("GLOW_COLOR_RED");
        assert team != null;
        team.color(color);
        team.addEntry(entity.getName());
        Entries.put(entity.getName(), team.getName());
        entity.setGlowing(true);
    }

    @SuppressWarnings("unused")
    public static void removeGlow(Entity entity) {
        Scoreboard scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();
        entity.setGlowing(false);
        if(Entries.containsKey(entity.getName())){
            Team team = scoreboard.getTeam(Entries.get(entity.getName()));
            assert team != null;
            team.removeEntry(entity.getName());
            Entries.remove(entity.getName());
        }
    }

}