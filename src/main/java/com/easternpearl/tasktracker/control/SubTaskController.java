package com.easternpearl.tasktracker.control;

import com.easternpearl.tasktracker.medel.SubTask;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import lombok.Setter;

import java.net.URL;
import java.util.ResourceBundle;

public class SubTaskController implements Initializable {

    private SubTask subTask;
    @FXML
    private Label subTaskTitle;
    private int no;

    @FXML
    void deleteSubTask(ActionEvent event) {

    }

    @FXML
    void editSubTask(ActionEvent event) {

    }

    @FXML
    void subTaskFinishToggle(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        reload();
    }

    public void setSubTask(SubTask subTask, int no){
        this.subTask = subTask;
        this.no = no;
    }

    private void reload() {
        Platform.runLater(()->{
            this.subTaskTitle.setText(String.format("%02d.",no) +" " +subTask.getSubTaskTitle());
        });
    }


}
