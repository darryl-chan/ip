package duke.commands;

import duke.Ui;
import duke.tasks.TaskList;

public class CommandBye extends Command {

    public CommandBye(TaskList taskList, Ui ui) {
        super(taskList, ui);
    }

    @Override
    public void execute(String description) {
        ui.print("Goodbye. See you later!");
    }
    
}