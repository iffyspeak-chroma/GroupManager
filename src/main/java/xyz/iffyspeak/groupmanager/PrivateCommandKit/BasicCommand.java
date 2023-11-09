package xyz.iffyspeak.groupmanager.PrivateCommandKit;

import org.bukkit.entity.Player;

import java.util.List;

public abstract class BasicCommand {

    private final String name;
    private final String description;
    private final String howto;
    private final boolean requiresAdmin;
    public abstract void execute(List<String> args, Player sender);

    public BasicCommand(String _name, String _description, String _howto, boolean requiresAdmin) {
        this.name = _name;
        this.description = _description;
        this.howto = _howto;
        this.requiresAdmin = requiresAdmin;
    }

    public String getName()
    {
        return name;
    }

    public String getDescription()
    {
        return description;
    }

    public String getHowTo()
    {
        return howto;
    }

    public boolean requiresAdmin()
    {
        return requiresAdmin;
    }

}
