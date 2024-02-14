package duke;

import java.util.ArrayList;
import java.util.Arrays;

import duke.commands.Command;
import duke.commands.CommandBye;
import duke.commands.CommandDeadline;
import duke.commands.CommandDelete;
import duke.commands.CommandEvent;
import duke.commands.CommandList;
import duke.commands.CommandMark;
import duke.commands.CommandToDo;
import duke.commands.CommandUnmark;
import duke.commands.CommandSort;

import duke.exceptions.DukeCeption;
import duke.commands.CommandFind;
import duke.tasks.TaskList;

/**
 * Class that reads user input
 */
public class Parser {

    enum CommandType {LIST, BYE, MARK, UNMARK, DELETE, TODO, DEADLINE, EVENT, FIND, SORT};

    private Ui ui;
    private TaskList taskList;
    private Storage storage;
    
    /**
     * Constructor for Parser
     * @param taskList TaskList List for storing Tasks
     * @param ui UI for printing to system
     */
    public Parser(TaskList taskList, Ui ui, Storage storage) {
        this.taskList = taskList;
        this.ui = ui;
        this.storage = storage;
        storage.loadData(taskList, ui);
    }

    /**
     * splits the user command and description
     */
    public void readUserInput(String userInput) {
        System.out.println(userInput);
        String[] userInputSplit = userInput.split(" ", 2);
        ArrayList<String> userInputList = new ArrayList<>(Arrays.asList(userInputSplit));
        userInputList.add("");

        assert userInputList.size() >= 2: "There should be a user input here";

        String userCommand = userInputList.get(0);
        String description = userInputList.get(1);
        parseUserInput(userCommand, description);
    }

    /**
     * Reads and understand user input then create a command and executes it
     * @param userCommand String user command
     * @param description String user description
     */
    private void parseUserInput(String userCommand, String description) {
        try {
            Command command;
            CommandType commandType = CommandType.valueOf(userCommand.toUpperCase());
            switch (commandType) {
                case BYE:
                    storage.saveData(taskList, ui);
                    command = new CommandBye(taskList, ui);
                    break;
                case LIST:
                    command = new CommandList(taskList, ui);
                    break;
                case MARK: 
                    command = new CommandMark(taskList, ui);
                    break;
                case UNMARK: 
                    command = new CommandUnmark(taskList, ui);
                    break;
                case DELETE:
                    command = new CommandDelete(taskList, ui);
                    break;
                case TODO: 
                    command = new CommandToDo(taskList, ui);
                    break;
                case DEADLINE:
                    command = new CommandDeadline(taskList, ui);
                    break;
                case EVENT:
                    command = new CommandEvent(taskList, ui);
                    break;
                case FIND:
                    command = new CommandFind(taskList, ui);
                    break;
                case SORT:
                    command = new CommandSort(taskList, ui);
                    break;
                default: // find
                    throw new DukeCeption("Sorry I don't recognize that command :/");
            }
            command.execute(description);
        } catch (IllegalArgumentException e) {
            ui.add("Sorry I don't recognize that command :/");
        } catch (Exception e) {
            ui.add(e.getMessage());
        } 
    }
}
