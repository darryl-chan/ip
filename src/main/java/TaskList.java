import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
// import java.io.BufferedWriter;

public class TaskList {
    
    ArrayList<Task> list;

    public TaskList() {
        this.list = new ArrayList<Task>();
    }

    public void printListAddNewTask(Task task, PrintList printList) {
        printList.add(String.format("Okay! added this task:"));
        printList.add(task.toString());
        printList.add(String.format("Now you have %d tasks in the list.", this.list.size()));
    }

    public void addTask(String task, String fullDescription, PrintList printList) throws DukeCeption {
        try {
            switch (task) {
                case "todo":
                    this.addTodo(fullDescription, printList);
                    break;
                case "deadline":
                    this.addDeadline(fullDescription, printList);
                    break;
                case "event":
                    this.addEvent(fullDescription, printList);
                    break;
            }
        } catch (StringIndexOutOfBoundsException e) {
            throw new DukeCeption("Make sure your /from/to/by is correct");
        }
    }

    public void addTodo(String description, PrintList printList) throws DukeCeption {
        if (description.isEmpty()) {
            throw new DukeCeption("Todo cannot be empty!");
        } else {
            Task task = new ToDo(description);
            this.list.add(task);
            this.printListAddNewTask(task, printList);
        }
    }

    public void addDeadline(String description, PrintList printList) throws DukeCeption {
        String[] descriptionList = description.split("/by", 2);
        try {
            if (description.isEmpty()) {
                throw new DukeCeption("Deadline cannot be empty!");
            } else {
                String taskDescription = descriptionList[0].trim();
                String by = descriptionList[1].trim();
                Task task = new Deadline(taskDescription, by);
                this.list.add(task);
                this.printListAddNewTask(task, printList);
            }
        } catch (IndexOutOfBoundsException e) {
            throw new DukeCeption("Make sure /by is written properly");
        }
        
    }

    public void addEvent(String description, PrintList printList) throws DukeCeption {
        try {
            if (description.isEmpty()) {
                throw new DukeCeption("Event cannot be empty!");
            } else {
                String[] descriptionList = description.split("/from", 2);
                String[] fromAndToList = descriptionList[1].split("/to", 2);
                String taskDescription = descriptionList[0].trim();
                String from = fromAndToList[0].trim();
                String to = fromAndToList[1].trim();
                Task task = new Event(taskDescription, from, to);
                this.list.add(task);
                this.printListAddNewTask(task, printList);
            }
        } catch (IndexOutOfBoundsException e) {
            throw new DukeCeption("Make sure /from and /to is written properly");
        }
        
    }

    public void markOrDelete(String command, String taskNumberString, PrintList printList) throws DukeCeption {
        try {
            int taskNumber = Integer.parseInt(taskNumberString);
            switch (command) {
                case "mark":
                    this.mark(taskNumber, printList);
                    break;
                case "unmark":
                    this.unmark(taskNumber, printList);
                    break;
                case "delete":
                    this.delete(taskNumber, printList);
                    break;
            }
        } catch (NumberFormatException e) {
            throw new DukeCeption("The number given is unrecognizable");
        } catch (IndexOutOfBoundsException e) {
            throw new DukeCeption("The number is not in this list!");
        }
    }
           
    public void delete(int taskNumber, PrintList printList) throws DukeCeption {
        Task removedTask = this.list.get(taskNumber - 1);
        this.list.remove(taskNumber - 1);
        printList.add("This task is now removed:");
        printList.add(removedTask.toString());
        printList.add(String.format("Now you have %d tasks in the list.", this.list.size()));
    }

    public void mark(int taskNumber, PrintList printList) {
        Task task = this.list.get(taskNumber - 1);
        task.markAsDone();
        printList.add("Great! I will mark this as done:");
        printList.add(task.toString());
    }

    public void unmark(int taskNumber, PrintList printList) {
        Task task = this.list.get(taskNumber - 1);
        task.markAsNotDone();
        printList.add("Alright! this task is now unmarked:");
        printList.add(task.toString());
    }

    public void getList(PrintList printList) {
        printList.add("Here are the tasks in your list:");
        for (int i = 0; i < this.list.size(); i++) {
            printList.add(String.format("%d. %s",
                    i + 1,
                    this.list.get(i)));
            System.out.println(list.get(i));

        }
    }

    public void textToTask(String line) {
        String taskType = line.substring(1,2);
        boolean isDone = (line.substring(4,5).equals("X")) ? true : false;
        String fullDescription = line.substring(7);
        String description;
        Task task;

        switch (taskType) {
            case "T":
                task = new ToDo(fullDescription, isDone);
                list.add(task);
                break;
            case "D":
                String[] splitBy = fullDescription.split(" (by: ", 2);
                description = splitBy[0];
                String byString = splitBy[1].substring(0, splitBy[1].length());
                task = new Deadline(description, byString, isDone);
                list.add(task);
                break;
            case "E":
                String[] splitFrom = fullDescription.split(" (from: ", 2);
                String[] splitTo = splitFrom[1].split(" to: ", 2);
                description = splitFrom[0];
                String fromString = splitTo[0];
                String toString = splitTo[1].substring(0, splitTo[1].length());
                task = new Event(description, fromString, toString, isDone);
                list.add(task);
                break;
            default:
                System.out.println("?");
        }

    }

    public void loadList(File file) {
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                this.textToTask(scanner.nextLine());
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }

    }

    public void saveList(File file) {
        try {
            FileWriter writer = new FileWriter(file, false);
            for (Task line : list) {
                writer.write(line.toString() + "\n");
            }
            writer.close();

        } catch (IOException e) {
            System.out.println(e.getMessage());
        } finally {
            System.out.println("donedone");
        }
    }

}
