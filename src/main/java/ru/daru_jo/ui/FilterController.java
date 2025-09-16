package ru.daru_jo.ui;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import ru.daru_jo.Application;
import ru.daru_jo.entity.FilterUser;
import ru.daru_jo.service.CodeService;

public class FilterController {
    @FXML
    public Button buttonOk;
    @FXML
    public Button buttonReset;
    @FXML
    public Button buttonCancel;
    @FXML
    public ChoiceBox<String> sex;
    @FXML
    public ChoiceBox<String> specialization;
    @FXML
    public TextField fio;
    public TextField roomIdFx;

    @FXML
    public void saveFilter(ActionEvent actionEvent) {
        Integer room = null;
        try {
            room = Integer.parseInt((roomIdFx.getText()));
        } catch (NumberFormatException exception){
            System.out.println(exception.getMessage());

        }
        Integer finalRoom = room;
        Platform.runLater(() ->
        Application.getInstance().setFilter(new FilterUser(
                fio.getText(),
                CodeService.isMan(sex.getValue()),
                specialization.getValue(),
                finalRoom)
        ));
        Application.getInstance().closeFilterStage();
    }

    @FXML
    public void cancel(ActionEvent actionEvent) {
        Application.getInstance().closeFilterStage();
    }

    public void resetFilter(ActionEvent actionEvent) {
        Platform.runLater(() ->
                Application.getInstance().setFilter(null)
        );
        Application.getInstance().closeFilterStage();
    }

    @FXML
    public void initialize()  {
        CodeService.getSex().forEach(s ->  sex.getItems().add(s));
        CodeService.getSpecializations().forEach(s -> specialization.getItems().add(s));

    }
    private String oldRoomId;
    public void changeRoomIdFx() {

        System.out.println("changeRoomIdFx");
        try {
            if(roomIdFx.getText() != null && roomIdFx.getText().isEmpty()){
                return;
            }
            Integer.parseInt(roomIdFx.getText());
            oldRoomId = roomIdFx.getText();
        } catch (NumberFormatException e){
            System.out.println(e.getMessage());
            roomIdFx.setText(oldRoomId);
            roomIdFx.end();

        }
    }


    public void changeRoomIdFx(KeyEvent keyEvent) {
        Platform.runLater(this::changeRoomIdFx);
    }
}
