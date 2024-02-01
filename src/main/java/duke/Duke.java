package duke;

public class Duke {

    private final String LOGO = "" 
            + "\t    __    _                 \n"
            + "\t   / /   (_)___  __  _______\n"
            + "\t  / /   / / __ \\/ / / / ___/\n"
            + "\t / /___/ / / / / /_/ (__  ) \n"
            + "\t/_____/_/_/ /_/\\__,_/____/  \n";

    private final String NAME = "Linus";


    private Ui ui;

    private TaskList taskList;

    private Parser parser;

    private Storage storage;

    private final String FILE_PATH = "./data/linus.txt";


    public Duke() {
        this.taskList = new TaskList();
        this.storage = new Storage(FILE_PATH);
        this.ui = new Ui();
        this.parser = new Parser(taskList, ui);
    }

    public void start() {
        ui.print("Hello from\n" + LOGO);
        ui.greeting(NAME);
        this.loadData();
        parser.run();
    }


    public void loadData() {
        storage.loadData(taskList, ui);
    }

    // public void readInput() {
    //     while (running) {
    //         parser.readUserInput();
    //     }
    // }




    public static void main(String[] args) {

        Duke duke = new Duke();
        duke.start();

    }
}

