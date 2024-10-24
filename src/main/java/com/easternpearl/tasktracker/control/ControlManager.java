package com.easternpearl.tasktracker.control;

import com.easternpearl.tasktracker.medel.SubTask;
import com.easternpearl.tasktracker.medel.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.Objects;

public class ControlManager {

    public static FXMLLoader loardScene(Stage stage,String title, String justFileName){
            FXMLLoader loader ;
            try {
                loader = new FXMLLoader(Objects.requireNonNull(ControlManager.class.getResource("../../../../view/"+justFileName+".fxml")));
                Scene scene = new Scene(loader.load());
                stage.setScene(scene);
                stage.setTitle(title);

                if (!stage.getStyle().equals(StageStyle.UNDECORATED)) {
                    stage.initStyle(StageStyle.UNDECORATED);  // Set style before showing the stage
                }
                    injectTopButtons(stage);

            } catch (IOException e) {
                throw new RuntimeException(e);
            }


        stage.show();
        stage.requestFocus();
        return loader;
    }

    public static boolean isValid(Task todo) {
        return true;
    }

    public static boolean isValid(SubTask todo) {
        return true;
    }

    public static void injectTopButtons(Stage stage){
        AnchorPane anchorPane = ((AnchorPane) ((Scene)stage.getScene()).getRoot());
        FXMLLoader loader = new FXMLLoader(ControlManager.class.getResource("../../../../view/top_buttons.fxml"));
        HBox hbox = null;
        try {
            hbox = loader.load();
            System.out.println("got hbox");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        TopButtons buttons = loader.getController();
        buttons.setStage(stage);
        anchorPane.getChildren().add(hbox);
        anchorPane.setTopAnchor(hbox, 23.0);
        anchorPane.setRightAnchor(hbox,10.0);
        //TopButtons controller = loader.getController();
        //controller.setStage(stage);

    }


    public static FXMLLoader injectCard(String justCardFileName, VBox parent){
        FXMLLoader loader = new FXMLLoader(ControlManager.class.getResource("../../../../view/"+justCardFileName+".fxml"));
        HBox card = null;
        try {
            card = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        parent.getChildren().add(card);

        return loader;
    }
}
