package ru.daru_jo.ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import ru.daru_jo.Application;
import ru.daru_jo.entity.User;
import ru.daru_jo.service.CodeService;
import ru.daru_jo.service.RoomService;


public class EditRoomController {
    @FXML
    public ChoiceBox<String> specialization;
    @FXML
    public TextField name;
    @FXML
    public ChoiceBox<String> sex;

    @FXML
    public Button generate;
    public TextField maxStudent;
    private RoomService roomService;

    @FXML
    public void initialize() {
        // Этап инициализации JavaFX
        roomService = RoomService.getInstance();
        CodeService.getSex().forEach(s ->  sex.getItems().add(s));
        CodeService.getSpecializations().forEach(s -> specialization.getItems().add(s));
    }


    public void reShow() {
        name.setText(null);
        specialization.setValue(null);
        sex.setValue(null);
    }

    @FXML
    public void generate(ActionEvent actionEvent) {
        if (checkAll()) {
            return;
        }
        User user = roomService.addUser(
                name.getText(),
                CodeService.isMan(sex.getValue()),
                specialization.getValue()
        );
        Application.getInstance().addUser(user);


    }





    public boolean checkAll() {
        StringBuilder stringBuilder = new StringBuilder();
        if (checkFio()) {
            addMes(stringBuilder, "Название комнаты не может быть пусты");
        }

        if (!stringBuilder.isEmpty()) {
            Application.showMes(stringBuilder.toString());
            return true;
        }
        return false;
    }


    private void addMes(StringBuilder stringBuilder, String text) {
        if (!stringBuilder.isEmpty()) {
            stringBuilder.append(", ");
        }
        stringBuilder.append(text);
    }





    public boolean checkFio() {
        if (name.getText() == null) {
            return true;
        }
        return name.getText().isEmpty();
    }


}
