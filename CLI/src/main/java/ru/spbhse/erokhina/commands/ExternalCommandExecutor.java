package ru.spbhse.erokhina.commands;

import ru.spbhse.erokhina.Environment;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * CommandExecutor for external command. Uses ProcessBuilder to execute unknown command.
 */
public class ExternalCommandExecutor implements CommandExecutor {

    private String commandName;

    /**
     * Sets the name of external command.
     * @param commandName name for external command
     */
    public void setCommandName(String commandName) {
        this.commandName = commandName;
    }

    @Override
    public void execute(List<String> args, Environment environment) throws IOException {
        List<String> commandAndArgs = new ArrayList<>();
        commandAndArgs.add(commandName);
        commandAndArgs.addAll(args);


        ProcessBuilder processBuilder = new ProcessBuilder(commandAndArgs)
                .directory(new File(System.getProperty("user.dir")));

        Process process = processBuilder.start();
        try {
            process.waitFor();
        } catch (InterruptedException e) {
            throw new RuntimeException("Interrupted while executing");
        }

        List<String> output = getOutput(process.getInputStream());
        List<String> errors = getOutput(process.getErrorStream());

        output.addAll(errors);

        environment.setPrevCommandOutput(String.join("\n", output));
    }

    private static List<String> getOutput(InputStream inputStream) throws IOException {
        List<String> output = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = br.readLine()) != null) {
                output.add(line);
            }
        }

        return output;
    }
}
