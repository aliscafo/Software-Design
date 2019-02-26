package ru.spbhse.erokhina.commands;

import org.junit.Test;
import ru.spbhse.erokhina.Environment;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Tests the logic of the commands.
 */
public class CommandsTest {
    @Test
    public void testEcho() throws IOException {
        CommandExecutor executor = new EchoCommandExecutor();
        Environment environment = new Environment();

        List<String> args = Arrays.asList("first arg", "second arg");

        executor.execute(args, environment);

        assertEquals("first arg second arg", environment.getPrevCommandOutput());
        assertFalse(environment.getExitFlag());
    }

    @Test
    public void testEchoCommandWithPrevOutput() throws IOException {
        CommandExecutor executor = new EchoCommandExecutor();
        Environment environment = new Environment();

        String output = "first arg\nsecond arg";
        environment.setPrevCommandOutput(output);

        executor.execute(new ArrayList<>(), environment);

        assertEquals(output, environment.getPrevCommandOutput());
        assertFalse(environment.getExitFlag());
    }

    @Test
    public void testCatCommand() throws IOException {
        CommandExecutor executor = new CatCommandExecutor();
        Environment environment = new Environment();

        executor.execute(Collections.singletonList("src/test/resources/test_file.txt"), environment);

        assertEquals("Twinkle twinkle little star\nHow I wonder what you are",
                environment.getPrevCommandOutput());
        assertFalse(environment.getExitFlag());
    }

    @Test
    public void testCatCommandWithPrevOutput() throws IOException {
        CommandExecutor executor = new CatCommandExecutor();
        Environment environment = new Environment();

        String output = "first line\nsecond line";
        environment.setPrevCommandOutput(output);

        executor.execute(new ArrayList<>(), environment);

        assertEquals(output, environment.getPrevCommandOutput());
        assertFalse(environment.getExitFlag());
    }

    @Test
    public void testExitCommand() throws IOException {
        CommandExecutor executor = new ExitCommandExecutor();
        Environment environment = new Environment();

        executor.execute(Collections.emptyList(), environment);

        assertTrue(environment.getExitFlag());
    }

    @Test
    public void testWcCommandWithPrevOutput() throws IOException {
        CommandExecutor executor = new WcCommandExecutor();
        Environment environment = new Environment();

        String output = "word word\nword\n  word  \nword";
        environment.setPrevCommandOutput(output);

        executor.execute(new ArrayList<>(), environment);

        assertEquals("4 5 28", environment.getPrevCommandOutput());
        assertFalse(environment.getExitFlag());
    }

    @Test
    public void testAssignCommand() throws IOException {
        CommandExecutor executor = new AssignCommandExecutor();
        Environment environment = new Environment();

        executor.execute(Arrays.asList("a", "50"), environment);

        assertEquals("50", environment.getOrDefault("a", ""));
        assertEquals("", environment.getPrevCommandOutput());
        assertFalse(environment.getExitFlag());
    }
}