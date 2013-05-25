package in.nikitapek.pumpkinvirus.commands;

import in.nikitapek.pumpkinvirus.util.Commands;
import in.nikitapek.pumpkinvirus.util.PumpkinVirusConfigurationContext;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.amshulman.mbapi.commands.PlayerOnlyCommand;
import com.amshulman.typesafety.TypeSafeCollections;
import com.amshulman.typesafety.TypeSafeList;

public final class CommandPumpkinVirus extends PlayerOnlyCommand {
    private final PumpkinVirusConfigurationContext configurationContext;

    public CommandPumpkinVirus(final PumpkinVirusConfigurationContext configurationContext) {
        super(configurationContext, Commands.PUMPKINVIRUS, 0, 0);

        this.configurationContext = configurationContext;
    }

    @Override
    protected boolean executeForPlayer(final Player player, final TypeSafeList<String> args) {
        configurationContext.isPumpkinSpreadEnabled = !configurationContext.isPumpkinSpreadEnabled;

        if (configurationContext.isPumpkinSpreadEnabled) {
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
