package yochan;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.Test;

import yochan.task.Deadline;
import yochan.task.Event;
import yochan.task.Todo;


/**
 * Tests the Ui class.
 *
 * @author Michael Cheong (Reshiro)
 */
public class UiTest {
    @Test
    public void showTaskList_todoDeadlineEvent_success() {
        Ui ui = new Ui();
        TaskList taskList = new TaskList();
        taskList.add(new Todo("test1"));
        try {
            taskList.add(new Deadline("test2", "1200-12-12 1200"));
            taskList.add(new Event("test3", "1000-10-10 1000", "1000-10-10 1001"));
        } catch (Exception e) {
            fail();
        }
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));

        ui.showTaskList(taskList);

        System.setOut(originalOut);

        assertEquals(outputStream.toString().trim(), ("-*-*-*-*-*-*-*-*-*-*-\n"
                + "1. [T][ ] test1 (Priority: 0)\n"
                + "2. [D][ ] test2 (Priority: 0) (by: Dec 12 1200 1200)\n"
                + "3. [E][ ] test3 (Priority: 0) "
                + "(from: Oct 10 1000 1000 to: Oct 10 1000 1001)\n"
                + "-*-*-*-*-*-*-*-*-*-*-").trim());

        try {
            taskList.markTask(2);
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void showTaskList_todoDeadlineMarkEvent_success() {
        Ui ui = new Ui();
        TaskList taskList = new TaskList();
        taskList.add(new Todo("test1"));
        try {
            taskList.add(new Deadline("test2", "1200-12-12 1200"));
            taskList.add(new Event("test3", "1000-10-10 1000", "1000-10-10 1001"));
        } catch (Exception e) {
            fail();
        }
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));

        try {
            taskList.markTask(2);
        } catch (Exception e) {
            fail();
        }

        ui.showTaskList(taskList);
        System.setOut(originalOut);

        assertEquals(outputStream.toString().trim(), ("-*-*-*-*-*-*-*-*-*-*-\n"
                + "1. [T][ ] test1 (Priority: 0)\n"
                + "2. [D][ ] test2 (Priority: 0) (by: Dec 12 1200 1200)\n"
                + "3. [E][X] test3 (Priority: 0) (from: Oct 10 1000 1000 to: Oct 10 1000 1001)\n"
                + "-*-*-*-*-*-*-*-*-*-*-").trim());
    }

    @Test
    public void showTaskList_todoDeleteDeadlineEvent_success() {
        Ui ui = new Ui();
        TaskList taskList = new TaskList();
        taskList.add(new Todo("test1"));
        try {
            taskList.add(new Deadline("test2", "1200-12-12 1200"));
            taskList.add(new Event("test3", "1000-10-10 1000", "1000-10-10 1001"));
        } catch (Exception e) {
            fail();
        }
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));

        try {
            taskList.remove(1);
        } catch (Exception e) {
            fail();
        }

        ui.showTaskList(taskList);
        System.setOut(originalOut);

        assertEquals(outputStream.toString().trim(), ("-*-*-*-*-*-*-*-*-*-*-\n"
                + "1. [T][ ] test1 (Priority: 0)\n"
                + "2. [E][ ] test3 (Priority: 0) (from: Oct 10 1000 1000 to: Oct 10 1000 1001)\n"
                + "-*-*-*-*-*-*-*-*-*-*-").trim());
    }
}
