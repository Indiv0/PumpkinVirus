package in.nikitapek.pumpkinvirus.commands;

import com.amshulman.mbapi.commands.DelegatingCommand;
import com.amshulman.mbapi.util.PermissionsEnum;
import in.nikitapek.pumpkinvirus.commands.pumpkinvirus.CommandToggle;
import in.nikitapek.pumpkinvirus.util.Commands;
import in.nikitapek.pumpkinvirus.util.PumpkinVirusConfigurationContext;

public final class CommandPumpkinVirus extends DelegatingCommand {
    public CommandPumpkinVirus(final PumpkinVirusConfigurationContext configurationContext) {
        super(configurationContext, Commands.PUMPKINVIRUS, 1, 2);
        registerSubcommand(new CommandToggle(configurationContext));
    }

    public enum PumpkinVirusCommands implements PermissionsEnum {
        TOGGLE;

        private static final String PREFIX;

        static {
            PREFIX = Commands.PUMPKINVIRUS.getPrefix() + Commands.PUMPKINVIRUS.name() + ".";
        }

        @Override
        public String getPrefix() {
            return PREFIX;
        }
    }
}
