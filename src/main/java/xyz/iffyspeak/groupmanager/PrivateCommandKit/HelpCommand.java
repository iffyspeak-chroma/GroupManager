package xyz.iffyspeak.groupmanager.PrivateCommandKit;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.entity.Player;
import xyz.iffyspeak.groupmanager.Tools.Globals;
import xyz.iffyspeak.groupmanager.Tools.SQL.SQLToolkit;

import java.util.ArrayList;
import java.util.List;

public class HelpCommand extends BasicCommand {
    public HelpCommand() {
        super(
                "help",
                "No description needed. It's the help menu!",
                "<red>help</red>",
                false);
    }

    @Override
    public void execute(List<String> args, Player sender) {
        List<BasicCommand> showInMenu = new ArrayList<>(); // Use this to display ONLY what commands a player can access
        List<Component> finishedComponents = new ArrayList<>(); // Use this for output

        String header = "<aqua>The Help Menu!<newline><red>Red is required</red> and <yellow>yellow is optional</yellow></aqua><newline>";

        for (BasicCommand cmd : Globals.Commands.registeredCommands)
        {
            if (cmd.requiresAdmin())
            {
                if (SQLToolkit.IsPlayerInGroup(Globals.Database.mySQL, String.valueOf(sender.getUniqueId()), "administrator"))
                {
                    showInMenu.add(cmd);
                }
            } else
            {
                showInMenu.add(cmd);
            }
        }

        for (BasicCommand cmd : showInMenu)
        {
            // <hover:show_text:'DESCRIPTION'><color:#00c24a><color:#427eff>COMMANDNAME</color> : HOWTO</color></hover>
            String parts = "<hover:show_text:'" + cmd.getDescription() + "'><color:#00c24a><color:#427eff>" + cmd.getName() + "</color> : " + cmd.getHowTo() + "</color></hover>";
            Component outComponent = MiniMessage.miniMessage().deserialize(parts);
            finishedComponents.add(outComponent);
        }

        Component output = Component.empty();

        for (Component comp : finishedComponents)
        {
            output = output.append(comp).appendNewline();
        }
        sender.sendMessage(header);
        sender.sendMessage(output);
    }
}
