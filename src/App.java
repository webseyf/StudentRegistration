import javafx.application.Application;
import javafx.stage.Stage;
import view.MainView;

/**
 * MainApp is the entry point of the Student Registration System
 */
public class App extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Launch the main GUI
        new MainView(primaryStage);
    }

    public static void main(String[] args) {
        launch(args); // Launches JavaFX application
    }
}
