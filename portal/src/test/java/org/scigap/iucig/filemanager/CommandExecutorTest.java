package org.scigap.iucig.filemanager;

import org.junit.Before;
import org.junit.Test;

/**
 * Created by swithana on 3/24/14.
 */
public class CommandExecutorTest {
    private CommandExecutor executor;

    @Before
    public void setUp() {
        executor = new CommandExecutor();
    }

    @Test
    public void testCD() {
        executor.executeCommand("cd temp");
        executor.getResult().toString();
        executor.executeCommand("cd ..");
        executor.getResult().toString();
        executor.executeCommand("cd temp");
        executor.getResult().toString();
        executor.executeCommand("cd test");
        executor.getResult().toString();
    }
    @Test
    public void testPWD() {
        executor.pwd();
//        executor.getResult().toString();
    }
    @Test
    public void testMkdir() {
        executor.executeCommand("cd temp");
        executor.getResult().toString();
        executor.executeCommand("mkdir ssd");
        executor.getResult().toString();
    }

    @Test
    public void testRename() {
        executor.executeCommand("cd temp");
        executor.getResult().toString();
        executor.executeCommand("rename ssh ssd");
        executor.getResult().toString();
    }

    //do not use the rm test unless it is absolutely necessary
    @Test
    public void testRM() {
        executor.executeCommand("cd temp");
        executor.getResult().toString();
        executor.executeCommand("rm ssd");
        executor.getResult().toString();
    }
}
