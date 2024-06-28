import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class GameLog {
    private ArrayList<String> logs; // Stores log messages
    private String fileName; // Name of the file where logs will be saved

    //Constructor for the GameLog class.
    // fileName The name of the file to save the logs
    public GameLog(String fileName) {
        this.logs = new ArrayList<>();
        this.fileName = fileName;
    }

    
    //Adds a log message to the log list and prints it to the console.
    // message The log message to add
    public void log(String message) {
        logs.add(message);
        System.out.println(message); // print to console for real-time updates
    }

    
    //Writes all log messages to the specified file.
    public void writeToFile() {
        try (FileWriter writer = new FileWriter(fileName)) {
            for (String log : logs) {
                writer.write(log + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}