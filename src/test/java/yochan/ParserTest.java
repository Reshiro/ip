package yochan;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

import yochan.command.AddCommand;
import yochan.task.Event;
import yochan.task.Todo;


/**
 * Tests the Parser class.
 *
 * @author Michael Cheong (Reshiro)
 */
public class ParserTest {

    @Test
    public void parseCommand_todoProperFormat_success() {
        try {
            assertEquals(Parser.parseCommand("todo test").toString().trim(),
                new AddCommand(new Todo("test")).toString().trim());
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void parseCommand_eventProperFormat_success() {
        try {
            assertEquals(Parser.parseCommand("event test /from 1000-10-10 1000 /to 1000-10-10 1001")
                    .toString().trim(),
                    new AddCommand(new Event("test", "1000-10-10 1000", "1000-10-10 1001"))
                    .toString().trim());
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void parseCommand_eventNoArguments_exceptionThrown() {
        try {
            Parser.parseCommand("event");
            fail(); // The test should not reach this line.
        } catch (Exception e) {
            assertEquals("Ough! The description of an event cannot be empty!", e.getMessage());
        }
    }

    @Test
    public void parseCommand_eventNoEndDate_exceptionThrown() {
        try {
            Parser.parseCommand("event test /by 2000-10-30 1200");
            fail(); // The test should not reach this line.
        } catch (Exception e) {
            assertEquals("Ough! Please include both /from and /to in your event command!",
                    e.getMessage());
        }
    }

    @Test
    public void parseCommand_deadlineDateWrongFormat_exceptionThrown() {
        try {
            Parser.parseCommand("deadline /by 2000-10-40 2500");
            fail(); // The test should not reach this line.
        } catch (Exception e) {
            assertEquals(""
                    + "Ough! The format should be: deadline <description> /by <YYYY-MM-DD HHMM>",
                    e.getMessage());
        }
    }

    @Test
    public void parseCommand_deadlineNoArguments_exceptionThrown() {
        try {
            Parser.parseCommand("deadline");
            fail(); // The test should not reach this line.
        } catch (Exception e) {
            assertEquals("Ough! The description of a deadline cannot be empty!", e.getMessage());
        }
    }
}
