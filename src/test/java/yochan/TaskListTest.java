package yochan;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

import yochan.task.Deadline;
import yochan.task.Event;
import yochan.task.Todo;

/**
 * Tests the TaskList class.
 *
 * @author Michael Cheong (Reshiro)
 */
public class TaskListTest {
    @Test
    public void markTask_todoMark_success() {
        TaskList taskList = new TaskList();
        taskList.add(new Todo("test"));
        try {
            taskList.markTask(0);
        } catch (Exception e) {
            fail();
        }
        Todo todoTest = new Todo("test");
        todoTest.mark();
        assertEquals(taskList.get(0).toString().trim(),
                todoTest.toString().trim());
    }

    @Test
    public void markTask_invalidIndex_exceptionThrown() {
        TaskList taskList = new TaskList();
        try {
            taskList.markTask(0);
        } catch (Exception e) {
            assertEquals(e.getMessage(),
                    "Invalid task number! >:(");
        }
    }

    @Test
    public void markTask_eventMarkAndUnmarkAndMark_success() {
        TaskList taskList = new TaskList();
        try {
            taskList.add(new Event("test", "1000-10-10 1000", "1000-10-10 1001"));
            taskList.markTask(0);
            taskList.unmarkTask(0);
            taskList.markTask(0);
        } catch (Exception e) {
            fail();
        }
        try {
            Event eventTest = new Event("test", "1000-10-10 1000", "1000-10-10 1001");
            eventTest.mark();
            assertEquals(taskList.get(0).toString().trim(),
                    eventTest.toString().trim());
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void markTask_deadlineMark_success() {
        TaskList taskList = new TaskList();
        try {
            taskList.add(new Deadline("test", "1000-10-10 1000"));
            taskList.markTask(0);
        } catch (Exception e) {
            fail();
        }
        try {
            Deadline deadlineTest = new Deadline("test", "1000-10-10 1000");
            deadlineTest.mark();
            assertEquals(taskList.get(0).toString().trim(),
                    deadlineTest.toString().trim());
        } catch (Exception e) {
            fail();
        }
    }
}
