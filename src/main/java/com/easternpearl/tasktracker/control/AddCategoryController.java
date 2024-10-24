package com.easternpearl.tasktracker.control;


import com.easternpearl.tasktracker.Db.Database;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import lombok.Data;
import lombok.Setter;
import javafx.event.ActionEvent;

public class AddCategoryController {

    @Setter
    private TodoSetters addToDoController;

    @Setter
    private Stage categoryStage;

    @FXML
    private JFXTextField newCategoryText;

    @FXML
    void addNewCategoryAction(ActionEvent event) {
        if(Database.getDataBase().addCategory(newCategoryText.getText())){
           Alert alert = new Alert(Alert.AlertType.INFORMATION,"You successfully added a category..!", ButtonType.OK);
           alert.show();
           addToDoController.reload();
           categoryStage.close();
           return;
        }
        new Alert(Alert.AlertType.ERROR,"Your add More than 25 characters or empty category title.").show();

    }

}

