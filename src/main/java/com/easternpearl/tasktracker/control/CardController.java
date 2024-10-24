package com.easternpearl.tasktracker.control;

import com.easternpearl.tasktracker.medel.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

public class CardController {



    @FXML
    private Label subsToDoText;

    @FXML
    private Label titleText;


    private Task todo;
    private HomeController home;

    public void setTitle(String cardTitle) {
            subsToDoText.setText(cardTitle);
    }

    public void setDescription(String cardDescription) {
        titleText.setText(cardDescription);

    }

    @FXML
    void displayDetails(MouseEvent event) {
        this.home.displayDetails(todo);
        this.home.setCurrentToDo(todo);
        this.home.finishedButtonEditAndActivate();
    }


    public void setHomeDetails(HomeController home, Task currentToDo){
        this.home = home;
        this.todo = currentToDo;
    }



}
