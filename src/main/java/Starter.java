import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.Objects;

public class Starter extends Application {


    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws Exception {
            stage.setScene(new Scene(FXMLLoader.load(Objects.requireNonNull(getClass().getResource("view/home.fxml")))));
            stage.initStyle(StageStyle.UNDECORATED);
            stage.show();
            stage.setTitle("Task Tracker Home");
    }


}
