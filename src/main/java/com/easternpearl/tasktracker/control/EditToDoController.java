package com.easternpearl.tasktracker.control;

import com.easternpearl.tasktracker.Db.Database;
import com.easternpearl.tasktracker.medel.Task;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;
import lombok.Setter;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class EditToDoController implements Initializable , TodoSetters {


    @FXML
    private JFXTextField todoTitleText;
    @FXML
    private JFXTextArea todoDescriptionText;
    @FXML
    private ComboBox<String> comboTodoCategory;
    @FXML
    private DatePicker startDate;
    @Setter
    private HomeController homeWindow;
    private Database database;
    private Task todo;

    @FXML
    void editToListAction(ActionEvent event) {
        Task todo = new Task(
                this.todo.getId(),
                todoTitleText.getText(),
                comboTodoCategory.getValue(),
                todoDescriptionText.getText(),
                this.todo.getFinished(),
                startDate.getValue(),
                this.todo.getEndDate());

        if( ControlManager.isValid(todo)){
            this.database.updateTodo(todo);
            homeWindow.reloadWindow();
            homeWindow.displayDetails(todo);
            new Alert(Alert.AlertType.INFORMATION,"You updated this todo successfully...!", ButtonType.OK);
            Stage stage = (Stage) this.todoTitleText.getScene().getWindow();
            stage.close();
        };
    }

    private void clearFields() {
        todoTitleText.setText(null);
        comboTodoCategory.setValue(null);
        todoDescriptionText.setText(null);
        startDate.setValue(null);

    }

    @FXML
    void discardAction(ActionEvent event) {
        Stage stage = (Stage) this.todoTitleText.getScene().getWindow();
        stage.close();
    }

    @FXML
    private Stage addCategoryStage = new Stage();
    @FXML
    void addToDoCategory(ActionEvent event) {

        FXMLLoader loader =  ControlManager.loardScene(addCategoryStage,"Add Category","add_category_stage");

        AddCategoryController categoryController = loader.getController();
        categoryController.setAddToDoController(this);
        categoryController.setCategoryStage(addCategoryStage);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        database = Database.getDataBase();
        this.reload();
    }

    public void reload(){
        ArrayList<String> categories = database.getCategoryAll();
        comboTodoCategory.setItems(FXCollections.observableList(categories));

    }



    public void fillValues(Task todo){
        this.todo = todo;
        this.todoTitleText.setText( todo.getTitle());
        this.todoDescriptionText.setText( todo.getDescription());
        this.startDate.setValue(todo.getStartDate());
        this.startDate.setDisable(true);

    }


}
