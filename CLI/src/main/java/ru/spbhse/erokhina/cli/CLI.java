package ru.spbhse.erokhina.cli;

import ru.spbhse.erokhina.Environment;
import ru.spbhse.erokhina.commands.*;
import ru.spbhse.erokhina.parser.*;

import java.util.*;

/**
 * Command language interpreter.
 */
public class CLI {
    private final CommandsPool commandsPool = new CommandsPool();
    private final Environment environment = new Environment();
    private final Parser parser = new ParserImpl();
    private final Scanner scanner = new Scanner(System.in);

    /**
     * Method for running CLI. Reads user commands, parses and executes them.
     */
    public void run() {

        loop:
        while (true) {
            environment.resetForNewCommand();

            String curLine = scanner.nextLine();

            List<ParsedCommand> parsedCommands;

            try {
                parsedCommands = parser.parse(curLine, environment);
            } catch (ParserException e) {
                System.out.println("Error while parsing command: " + e.getClass().getName() + ": " + e.getMessage());
                continue;
            }

            for (ParsedCommand parsedCommand : parsedCommands) {
                CommandExecutor commandExecutor = commandsPool.getCommandExecutor(parsedCommand.getCommand());

                try {
                    commandExecutor.execute(parsedCommand.getArgs(), environment);
                } catch (Exception e) {
                    System.out.println("Error during execution: " + e.getMessage());
                    continue;
                }

                if (environment.getExitFlag()) {
                    break loop;
                }
            }

            System.out.println(environment.getPrevCommandOutput());
        }

        scanner.close();
    }
}
