package ru.spbhse.erokhina.commands;

import ru.spbhse.erokhina.Environment;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * CommandExecutor for wc command. Wc command reads either standard input or a list of files
 * and generates the following statistics: newline count, word count, and byte count.
 */
public class WcCommandExecutor implements CommandExecutor {
    @Override
    public void execute(List<String> args, Environment environment) throws IOException {

        if (args.isEmpty()) {
            String input;
            input = environment.getPrevCommandOutput();

            environment.setPrevCommandOutput(getInfo(input, null));
            return;
        }

        StringBuilder res = new StringBuilder();

        for (String arg: args) {
            String input;
            input = new String(Files.readAllBytes(Paths.get(arg)));

            res.append(getInfo(input, arg));
        }

        environment.setPrevCommandOutput(res.toString());
    }

    private String getInfo(String input, String fileName) {
        int words = 0;

        List<String> inputLines = Arrays.asList(input.split("\n"));

        for (String line : inputLines) {
            if (!line.isEmpty()) {
                String[] allWords = line.split("\\s+");
                int curCnt = 0;

                for (String str : allWords) {
                    if (!str.isEmpty()) {
                        curCnt++;
                    }
                }

                words += curCnt;
            }
        }

        long bytes;

        if (fileName == null) {
            bytes = input.getBytes().length;
            return inputLines.size() + " " + words + " " + bytes;
        } else {
            File file = new File(fileName);
            bytes = file.length();
            return inputLines.size() + " " + words + " " + bytes + " " + fileName;
        }
    }
}
