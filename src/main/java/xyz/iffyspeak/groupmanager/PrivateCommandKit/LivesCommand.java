package xyz.iffyspeak.groupmanager.PrivateCommandKit;

import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.entity.Player;
import xyz.iffyspeak.groupmanager.Tools.Globals;
import xyz.iffyspeak.groupmanager.Tools.SQL.SQLToolkit;

import java.util.List;

public class LivesCommand extends BasicCommand {

    public LivesCommand() {
        super(
                "lives",
                "Check how many lives you have",
                "<red>lives</red>",
                false);
    }

    @Override
    public void execute(List<String> args, Player sender) {
        int livesleft = SQLToolkit.getPlayerLives(Globals.Database.mySQL, String.valueOf(sender.getUniqueId()));
        sender.sendMessage(MiniMessage.miniMessage().deserialize("<color:#00ff73>You have <red>" + livesleft + "</red> lives remaining</color>"));
    }
}
