package ru.spbhse.erokhina.commands;

import ru.spbhse.erokhina.Environment;

import java.util.List;

/**
 * CommandExecutor for echo command. Echo command outputs the strings it is being passed as arguments.
 */
public class EchoCommandExecutor implements CommandExecutor {
    @Override
    public void execute(List<String> args, Environment environment) {
        if (args.isEmpty()) {
            environment.setPrevCommandOutput(environment.getPrevCommandOutput());
        } else {
            environment.setPrevCommandOutput(String.join(" ", args));
        }
    }
}
