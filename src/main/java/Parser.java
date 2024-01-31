import java.util.ArrayList;
import java.util.Arrays;

public class Parser {
    
    public Parser(){}

    public boolean readUserInput(String userInput, TaskList taskList, Ui ui) {
        String[] userInput = input.split(" ", 2);
        ArrayList<String> userInputList = new ArrayList<>(Arrays.asList(userInput));
        userInputList.add("");
        String command = userInputList.get(0).toLowerCase();
        String taskNumber;

        try {
            switch (command) {
                case "bye":
                    this.end();
                    return false;
                    break;
                case "list":
                    this.taskList.getList(this.ui);
                    break;
                case "mark": case "unmark": case "delete":
                    taskNumber = userInputList.get(1);
                    this.taskList.markOrDelete(command, taskNumber, ui);
                    break;
                case "todo": case "deadline": case "event":
                    String task = userInputList.get(1);
                    this.taskList.addTask(command, task, ui);
                    break;
                default:
                    throw new DukeCeption("Sorry I don't recognize that command :/");
            }
        } catch (Exception e) {
            ui.add(e.getMessage());
        } finally {
            ui.print();
            return true;
        }
    }

    

}
