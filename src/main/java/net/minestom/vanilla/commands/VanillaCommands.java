package net.minestom.vanilla.commands;

import net.minestom.server.command.CommandManager;
import net.minestom.server.command.builder.Command;

import java.util.function.Supplier;

/**
 * All commands available in the vanilla reimplementation
 */
public enum VanillaCommands {

    GAMEMODE(GamemodeCommand::new),
    DIFFICULTY(DifficultyCommand::new),
    ME(MeCommand::new),
    STOP(StopCommand::new),
    HELP(HelpCommand::new),
    TELEPORT(TeleportCommand::new)
    // Completely broken, cpu drainer
    //SAVE_ALL(SaveAllCommand::new),
    ;

    private final Supplier<Command> commandCreator;

    private VanillaCommands(Supplier<Command> commandCreator) {
        this.commandCreator = commandCreator;
    }

    /**
     * Register all vanilla commands into the given manager
     * @param manager
     */
    public static void registerAll(CommandManager manager) {
        for(VanillaCommands vanillaCommand : values()) {
            Command command = vanillaCommand.commandCreator.get();
            manager.register(command);
        }
    }
}
