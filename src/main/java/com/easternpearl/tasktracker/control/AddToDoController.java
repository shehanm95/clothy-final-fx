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
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;
import lombok.Setter;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AddToDoController implements Initializable , TodoSetters {


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
    private Database dabase;

    @FXML
    void addToListAction(ActionEvent event) {
        Task todo = new Task(
                0,
                todoTitleText.getText(),
                comboTodoCategory.getValue(),
                todoDescriptionText.getText(),
                false,
                startDate.getValue(),
                null);

        if( ControlManager.isValid(todo)){
            this.dabase.AddTodos(todo);
            homeWindow.reloadWindow();
            this.clearFields();
            //cear fields
        }
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
        dabase = Database.getDataBase();
        this.reload();
        this.startDate.setValue(LocalDate.now());
        this.comboTodoCategory.setValue("Default");
    }

    public void reload(){
        ArrayList<String> categories =dabase.getCategoryAll();
        ArrayList<String> withoutAll = new ArrayList<String>(categories);
        withoutAll.remove("All");
        comboTodoCategory.setItems(FXCollections.observableList(withoutAll));
    }


}
