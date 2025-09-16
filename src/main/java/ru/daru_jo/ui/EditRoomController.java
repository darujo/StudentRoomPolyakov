package ru.daru_jo.ui;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import ru.daru_jo.Application;
import ru.daru_jo.entity.Room;
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
        Room room = roomService.addRoom(
                name.getText(),
                CodeService.isMan(sex.getValue()),
                specialization.getValue(),
                Integer.parseInt(maxStudent.getText())
        );
        Application.getInstance().addRoom(room);


    }





    public boolean checkAll() {
        StringBuilder stringBuilder = new StringBuilder();
        if (checkName()) {
            addMes(stringBuilder, "Название комнаты не может быть пустым");
        }
        if (sex.getValue()== null || sex.getValue().isEmpty()) {
            addMes(stringBuilder, "Пол должен быть заполнен");
        }
        if (specialization.getValue()== null || specialization.getValue().isEmpty()) {
            addMes(stringBuilder, "Спецальность  должен быть заполнена");
        }
        if (maxStudent.getText()== null || maxStudent.getText().isEmpty()) {
            addMes(stringBuilder, "Количество мест в комнате должно быть заполнено");
        }

        if (stringBuilder.length() != 0) {
            Application.showMes(stringBuilder.toString());
            return true;
        }
        return false;
    }


    private void addMes(StringBuilder stringBuilder, String text) {
        if (stringBuilder.length() != 0) {
            stringBuilder.append(", ");
        }
        stringBuilder.append(text);
    }





    public boolean checkName() {
        if (name.getText() == null) {
            return true;
        }
        return name.getText().isEmpty();
    }
    String oldMaxStudent;
    public void changeMaxStudentFx() {

        System.out.println("changeMaxStudentFx");
        try {
            if(maxStudent.getText() != null && maxStudent.getText().isEmpty()){
                return;
            }
            Integer.parseInt(maxStudent.getText());
            oldMaxStudent = maxStudent.getText();
        } catch (NumberFormatException e){
            System.out.println(e.getMessage());
            maxStudent.setText(oldMaxStudent);
            maxStudent.end();

        }
    }



    public void changeMaxStudentFx(KeyEvent keyEvent) {
        Platform.runLater(this::changeMaxStudentFx);
    }
}
