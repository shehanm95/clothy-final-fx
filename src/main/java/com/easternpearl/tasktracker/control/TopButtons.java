package com.easternpearl.tasktracker.control;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import lombok.Setter;

public class TopButtons {

    @Setter
    Stage stage;
    @FXML
    private Circle closeCircle;

    public void closeOperation(MouseEvent mouseEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, stage.getTitle() + " - Do you want to close this window?");

        // Show the alert and wait for a response
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                // If the user confirmed, close the stage
                stage.close();
            }
            // Optional: Handle other responses (like ButtonType.CANCEL) if needed
        });

    }

    public void minimizeOperation(MouseEvent mouseEvent) {
       stage.setIconified(true);
    }

    public static class SubTaskController {
    }
}
