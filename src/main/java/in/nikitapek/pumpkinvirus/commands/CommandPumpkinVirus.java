package in.nikitapek.pumpkinvirus.commands;

import com.amshulman.mbapi.commands.PlayerOnlyCommand;
import com.amshulman.typesafety.TypeSafeCollections;
import com.amshulman.typesafety.TypeSafeList;
import in.nikitapek.pumpkinvirus.util.Commands;
import in.nikitapek.pumpkinvirus.util.PumpkinVirusConfigurationContext;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public final class CommandPumpkinVirus extends PlayerOnlyCommand {
    private final PumpkinVirusConfigurationContext configurationContext;

    public CommandPumpkinVirus(final PumpkinVirusConfigurationContext configurationContext) {
        super(configurationContext, Commands.PUMPKINVIRUS, 0, 1);

        this.configurationContext = configurationContext;
    }

    @Override
    protected boolean executeForPlayer(final Player player, final TypeSafeList<String> args) {
        String worldName;

        // If the player provided a world argument, use it as the world to be modified.
        switch (args.size()) {
            case 1:
                worldName = args.get(0);
                break;
            default:
                worldName = player.getWorld().getName();
        }

        boolean isWorldEnabled = configurationContext.worlds.contains(worldName);

        if (isWorldEnabled) {
            configurationContext.worlds.remove(worldName);
        } else {
            configurationContext.worlds.add(worldName);
        }

        isWorldEnabled = !isWorldEnabled;

        if (isWorldEnabled) {
            player.sendMessage("Pumpkins are now spreading!");
        } else {
            player.sendMessage("Pumpkins are no longer spreading!");
        }

        return true;
    }

    @Override
    public TypeSafeList<String> onTabComplete(final CommandSender sender, final TypeSafeList<String> args) {
        return TypeSafeCollections.emptyList();
    }
}
