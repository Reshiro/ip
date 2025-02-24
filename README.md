# YoChan User Guide

YoChan is a **desktop app for managing your tasks with a GUI**.

## Getting Started

1. Download the executable JAR file from [here](https://github.com/Reshiro/ip/releases/tag/A-Release)

2. Run the following command in the JAR file's directory
   ```terminal
   java -jar YoChan.jar
   ```

3. Begin adding tasks:
   
   - To add a **ToDo** task, enter `todo <task_name>`
   - To add a **Deadline** task, enter `deadline <task_name> /by <yyyy-MM-dd HHmm>`
   - To add an **Event** task, enter `event <task_name> /from <yyyy-MM-dd HHmm> /to <yyyy-MM-dd HHmm>`
   
4. To list all of your tasks, simply enter `list`

## Other Features

1. **Mark** and **Unmark** a task as complete using `mark <task_number>` or `unmark <task_number>`

2. **Delete** a task using `delete <task_number>`

3. **Find** a task using `find <search_term>`

4. Set a **Priority** for a task using `priority <task_number> <priority>`
   It's up to you to determine a priority scale!

5. Once you're done using the app, just enter `bye`
   and YoChan will save all of the tasks in your list! The next time you open up the app, everything will be there.

