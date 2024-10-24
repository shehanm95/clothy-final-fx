package com.easternpearl.tasktracker.control;


import com.easternpearl.tasktracker.Db.Database;
import com.easternpearl.tasktracker.medel.Search;
import com.easternpearl.tasktracker.medel.SubTask;
import com.easternpearl.tasktracker.medel.Task;
import com.jfoenix.controls.JFXComboBox;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import lombok.Setter;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;


public class HomeController implements Initializable {

    public Circle closeCircle;
    @FXML
    private AnchorPane todoCards;

    @FXML
    private VBox cardPane;


    @FXML
    private TextArea mainToDoDescriptionText;

    @FXML
    private Label mainTodoTitleText;

    @FXML
    private Label endDateTxt;

    @FXML
    private Label startDateTxt;

    @FXML
    private JFXComboBox<String> comboCategory;

    @FXML
    private Button addSubTodoButton;

    @FXML
    private VBox subTodoPane;

    @FXML
    private Button deleteButton;

    @FXML
    private Button editButton, markFinishButton;


    private Stage addToDoStage = new Stage();
    @Setter
    private Task currentToDo;
    private Database database;
    private Stage editStage = new Stage();
    private ArrayList<Task> tasks;
    private TaskState taskState = TaskState.ALL;
    private Stage homeStage;
    private Search prevSearch;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        database = Database.getDataBase();
        tasks = database.getToDoAll();
        this.reloadWindow();
        afterSearchSetInitials();
        comboCategory.setValue("All");
        Platform.runLater(()->{
            homeStage = (Stage) ((Scene)this.mainTodoTitleText.getScene()).getWindow();
            ControlManager.injectTopButtons(this.homeStage);
        });

    }

    private void afterSearchSetInitials(){

        //logics for buttons
        if (!tasks.isEmpty()) {
            displayDetails(tasks.get(0));
            enableFunctionButtons();
        } else {
            loadDefaultFiller();
        }
    }

    void reloadWindow() {
        // Clear the cardPane to avoid duplicating cards
        cardPane.getChildren().clear();

        // Re-populate the comboCategory list
        this.comboCategory.setItems(FXCollections.observableList(database.getCategoryAll()));


            // Check if tasks list is not empty
            if (!tasks.isEmpty()) {
                System.out.println("todos size : " + tasks.size());
                FXMLLoader[] loaders = getLoaders(tasks.size());
                int i = 0;
                for (Task task : tasks) {
                    FXMLLoader loader = loaders[i];
                    HBox card = null;
                    try {
                        card = loader.load();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    i++;
                    // Get the controller associated with the Card.fxml
                    CardController cardController = loader.getController();
                    cardController.setTitle(task.getTitle());
                    cardController.setDescription(task.getDescription());
                    cardController.setHomeDetails(this, task);

                    // Add the card to the cardPane
                    if(cardPane.getChildren().size() < tasks.size()){
                    cardPane.getChildren().add(card);}
                }
            }

            // Call method to set initial values after search
            afterSearchSetInitials();
    }


    FXMLLoader[] getLoaders(int size){
        FXMLLoader[] loaders = new FXMLLoader[size];
        for (int i = 0; i < size; i++) {
            try {
                FXMLLoader ldr = new FXMLLoader(getClass().getResource("../../../../view/todo_card.fxml"));
                loaders[i] = ldr;
            }  catch (Exception e) {
                e.printStackTrace();
            }
        }
        return loaders;
    }


    private void loadDefaultFiller() {
        this.mainToDoDescriptionText.setText("Here will display the ToDo Desctioption you entered when you created the todo Item, \n" +
                "If you need you can edit this by clicking Edit To Do Button,\n" +
                "or you can add new todos by using \"Add new todo\" button;");
        this.mainTodoTitleText.setText("Add new To Do To Check Details...");
        this.startDateTxt.setText("ToDo assigned date will display here,");
        this.endDateTxt.setText("If todo is finished end date will display here.");
        this.disableFunctionButtons();
    }

    private void disableFunctionButtons() {
        addSubTodoButton.setDisable(true);
        deleteButton.setDisable(true);
        if(this.currentToDo != null){
            editButton.setDisable(!this.currentToDo.getFinished());
        }else {
            editButton.setDisable(true);
        }
        markFinishButton.setDisable(true);
    }

    private void enableFunctionButtons() {
        addSubTodoButton.setDisable(false);
        deleteButton.setDisable(false);
        editButton.setDisable(false);
        markFinishButton.setDisable(false);
        finishedButtonEditAndActivate();

    }

    public void finishedButtonEditAndActivate(){
        if (this.currentToDo.getFinished()) {
            markFinishButton.setText("Mark As Not Finished");
            editButton.setDisable(true);
        } else {
            editButton.setDisable(false);
            markFinishButton.setText("Mark As Finished");
        }
    }

    private Stage addSubTaskStage = new Stage();
    public void addToDoAction(ActionEvent actionEvent) {
        FXMLLoader loader = ControlManager.loardScene(addToDoStage, "Task Tracker", "add_to_do");
        AddToDoController addToDoController = loader.getController();
        addToDoController.setHomeWindow(this);
    }
    public void addSubTaskAction(ActionEvent actionEvent) {
       FXMLLoader loader = ControlManager.loardScene(addSubTaskStage,"Add Sub Task","add_sub_task");
       AddSubTaskController controller = loader.getController();
       controller.setCurrentTask(this.currentToDo);
       controller.setHomeController(this);

    }

    public void displayDetails(Task currentToDo) {
        this.currentToDo = currentToDo;
        this.mainToDoDescriptionText.setText(currentToDo.getDescription());
        this.mainTodoTitleText.setText(currentToDo.getTitle());
        this.startDateTxt.setText(currentToDo.getStartDate().toString());
        if (currentToDo.getEndDate() != null) {
            this.endDateTxt.setText(currentToDo.getEndDate().toString());
        } else {
            this.endDateTxt.setText("This ToDO Is Still No Process..");
        }
      displaySubTasks();

    }


    public void displaySubTasks(){

        ArrayList<SubTask> subtasks = database.getSubTasks(this.currentToDo.getId());
        System.out.println("subTask Size" + subtasks.size());
        subTodoPane.getChildren().clear();
        int i = 0;
        for (SubTask subTask : subtasks){
            FXMLLoader loader = ControlManager.injectCard("sub_todo_card",subTodoPane);
            SubTaskController controller = loader.getController();
            controller.setSubTask(subTask , ++i);
        }
    }

    @FXML
    void deleteMainTodoAction(ActionEvent event) {
        if (currentToDo != null) {
            database.deleteTodo(currentToDo);
            currentToDo = null;
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "ToDo Deleted Successfully...", ButtonType.OK);
            reloadWindow();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "No Selected Todos to Delete", ButtonType.OK);
            alert.show();

        }
    }

    @FXML
    void displayActiveTodosAction(ActionEvent event) {
        taskState = TaskState.ACTIVE;
        search();
    }

    @FXML
    void displayAllTodosAction(ActionEvent event) {
            taskState = TaskState.ALL;
            search();
    }

    @FXML
    void displayFinishedTodosAction(ActionEvent event) {
            taskState = TaskState.FINISHED;
            search();
    }

    @FXML
    void editMainTodoAction(ActionEvent event) {
        FXMLLoader loader = ControlManager.loardScene(editStage, "Edit Task", "edit_to_do");
        EditToDoController editToDoController = loader.getController();
        editToDoController.fillValues(this.currentToDo);
        editToDoController.setHomeWindow(this);
    }

    @FXML
    void markFullToDoAsFinishedAction(ActionEvent event) {
        if (this.currentToDo.getFinished()) {
            this.currentToDo.setFinished(false);
            this.currentToDo.setEndDate(null);
            new Alert(Alert.AlertType.INFORMATION, "You Marked this Task as not finished...!").show();
            this.editButton.setDisable(false);
        } else {
            this.currentToDo.setEndDate(LocalDate.now());
            this.editButton.setDisable(true);
            this.currentToDo.setFinished(true);
            new Alert(Alert.AlertType.INFORMATION, "You Marked this Task as finished...!").show();
        }
        finishedButtonEditAndActivate();
        database.updateTodo(this.currentToDo);
        this.reloadWindow();
        this.displayDetails(this.currentToDo);
    }

    public void selectCategory(ActionEvent actionEvent) {

        search();

    }

    private void search(){
        System.out.println(this.comboCategory.getValue());
        System.out.println(this.taskState.toString());
        Search curSearch = new Search(this.taskState, this.comboCategory.getValue());
        tasks = database.searchToDo(curSearch);
        if(tasks.isEmpty()){
            new Alert(Alert.AlertType.ERROR,"No Task to do in this category in this state,").show();
            if(prevSearch == null){
                comboCategory.setValue("All");
                taskState  = TaskState.ALL;
                return;
            }
                taskState = prevSearch.getState();
                comboCategory.setValue(prevSearch.getCategory());
                search();

            return;
        }else {
            prevSearch = curSearch;
            afterSearchSetInitials();
        }
        reloadWindow();
    }


}


