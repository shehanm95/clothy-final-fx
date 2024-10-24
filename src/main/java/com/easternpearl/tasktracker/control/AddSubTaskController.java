package com.easternpearl.tasktracker.control;

import com.easternpearl.tasktracker.Db.Database;
import com.easternpearl.tasktracker.medel.SubTask;
import com.easternpearl.tasktracker.medel.Task;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import lombok.Setter;

public class AddSubTaskController {
    @Setter
    private Stage stage;
    @Setter
    private Task currentTask;
    @FXML
    private JFXTextField subTaskTitle;
    @Setter
    private HomeController homeController;

    @FXML
    void addSubTaskToList(ActionEvent event) {
        SubTask subTask = new SubTask(this.currentTask.getId(),subTaskTitle.getText(),false);
        if (ControlManager.isValid(subTask)){
                Database.getDataBase().addSubTask(subTask);
            new Alert(Alert.AlertType.INFORMATION,"You add a sub task successfully.", ButtonType.OK).show();
            subTaskTitle.setText(null);
            homeController.displaySubTasks();

        }
        else {
            new Alert(Alert.AlertType.ERROR,"Please enter your sub task correctly", ButtonType.OK).show();

        }
    }





}
