package duke.commands;

import duke.Ui;

import duke.exceptions.DukeCeption;
import duke.exceptions.EmptyDescriptionException;
import duke.exceptions.IncorrectFormatException;

import duke.tasks.Event;
import duke.tasks.Task;
import duke.tasks.TaskList;

/**
 * The command class when user wants to create an event task
 */
public class CommandEvent extends Command {

    public CommandEvent(TaskList taskList, Ui ui) {
        super(taskList, ui);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void execute(String description) {
        try {
            Task task = this.cleanUserInput(description);
            taskList.addNewTask(task);
            String taskType = task.getTaskType();
            ui.addNewTask(taskType, task.toString(), taskList.getSize());
        } catch (DukeCeption e) {
            ui.add(e.getMessage());
        }
    }
    
    /**
     * Cleans the raw user input and creates an Event Task after cleaning user input
     * Returns a task after cleaning the user input
     * @param description String of raw user input
     * @return Task after cleaning user input
     * @throws DukeCeption when description is empty or /from and /to is written incorrectly
     */
    private Task cleanUserInput(String description) throws DukeCeption {
        try {
            if (description.isEmpty()) {
                throw new EmptyDescriptionException("Event cannot be empty!");
            }
            String[] descriptionList = description.split("/from", 2);
            String[] fromAndToList = descriptionList[1].split("/to", 2);
            String taskDescription = descriptionList[0].trim();
            String from = fromAndToList[0].trim();
            String to = fromAndToList[1].trim();
            Task task = new Event(taskDescription, from, to);
            return task;
        } catch (IndexOutOfBoundsException e) {
            throw new IncorrectFormatException("Make sure /from and /to is written properly");
        }
    }
}
