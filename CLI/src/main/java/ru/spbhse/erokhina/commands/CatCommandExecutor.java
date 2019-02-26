package ru.spbhse.erokhina.commands;

import ru.spbhse.erokhina.Environment;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

/**
 * CommandExecutor for cat command. Cat command reads files sequentially, writing them to standard output.
 */
public class CatCommandExecutor implements CommandExecutor {
    @Override
    public void execute(List<String> args, Environment environment) throws IOException {
        if (args.isEmpty()) {
            environment.setPrevCommandOutput(environment.getPrevCommandOutput());
        } else {
            StringBuilder res = new StringBuilder();
            for (String arg : args) {
                Path path = Paths.get(arg);
                try {
                    byte[] bytes = Files.readAllBytes(path);
                    res.append(new String(bytes));
                } catch (IOException e) {
                    throw new IOException("cannot read from file or file doesn't exist: " + arg);
                }
            }

            environment.setPrevCommandOutput(res.toString());
        }
    }
}
