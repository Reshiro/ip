package yochan.command;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import yochan.Storage;
import yochan.TaskList;
import yochan.Ui;
import yochan.YoChanException;

/**
 * Represents a command given by the user.
 *
 * @author Michael Cheong (Reshiro)
 */
public abstract class Command {
    /** Indicates if the Command is an Exit command */
    protected boolean isExit;

    /**
     * Creates a Command object.
     */
    public Command() {
        this.isExit = false;
    }

    /**
     * Executes the command.
     *
     * @param tasks The list of tasks that the Command may operate on.
     * @throws YoChanException If any issue occurs during the execution of the Command.
     */
    public abstract void execute(TaskList tasks, Ui ui, Storage storage) throws YoChanException;

    /**
     * Indicates if the Command is an Exit command.
     */
    public boolean isExit() {
        return isExit;
    }

    /**
     * Execute this command and returns the response.
     *
     * @return The response from the chatbot as a String.
     * @throws YoChanException If execution of the command fails.
     */
    public String executeAndGetResult(TaskList tasks, Ui ui, Storage storage) throws YoChanException {
        // @@author Ernest Friedman-Hill-reused
        // Reused from https://stackoverflow.com/questions/8708342/redirect-console-output-to-string-in-java
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos);
        PrintStream old = System.out;
        System.setOut(ps);
        execute(tasks, ui, storage);
        System.out.flush();
        System.setOut(old);
        return baos.toString();
    }
}
