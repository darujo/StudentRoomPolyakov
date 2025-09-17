package ru.daru_jo.dialogs;

import javafx.scene.control.Alert;
import ru.daru_jo.Application;

public class Dialogs {


    public static void showDialog(Alert.AlertType dialogType, String title, String type, String message) {
        Alert alert = new Alert(dialogType);
        alert.initOwner(Application.getBrowseStage());
        alert.setTitle(title);
        alert.setHeaderText(type);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
