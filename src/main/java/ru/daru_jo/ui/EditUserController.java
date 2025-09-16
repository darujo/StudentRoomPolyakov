package ru.daru_jo.ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.TextField;
import ru.daru_jo.Application;
import ru.daru_jo.entity.User;
import ru.daru_jo.exceptions.UsernameNotFoundException;
import ru.daru_jo.service.CodeService;
import ru.daru_jo.service.UserService;



public class EditUserController {
    @FXML
    public ChoiceBox<String> specialization;
    @FXML
    public TextField fio;
    @FXML
    public ChoiceBox<String> sex;

    @FXML
    public Button generate;
    private UserService userService;

    @FXML
    public void initialize() {
        // Этап инициализации JavaFX
        userService = UserService.getInstance();
        CodeService.getSex().forEach(s ->  sex.getItems().add(s));
        CodeService.getSpecializations().forEach(s -> specialization.getItems().add(s));
    }


    public void reShow() {
        fio.setText(null);
        specialization.setValue(null);
        sex.setValue(null);
    }

    @FXML
    public void generate(ActionEvent actionEvent) {
        if (checkAll()) {
            return;
        }
        try {


            User user = userService.addUser(
                    fio.getText(),
                    CodeService.isMan(sex.getValue()),
                    specialization.getValue()
            );

            Application.getInstance().addUser(user);
        } catch (UsernameNotFoundException ex){
            Application.showMes(ex.getMessage());
        }


    }





    public boolean checkAll() {
        StringBuilder stringBuilder = new StringBuilder();
        if (checkFio()) {
            addMes(stringBuilder, "ФИО студента не может состоять только из пробелов и точек, ФИО студента состоять из букв кириллицы пробелов и точек");
        }
        if (sex.getValue()== null || sex.getValue().isEmpty()) {
            addMes(stringBuilder, "Пол должен быть заполнен");
        }
        if (specialization.getValue()== null || specialization.getValue().isEmpty()) {
            addMes(stringBuilder, "Спецальность  должен быть заполнена");
        }

        if (stringBuilder.length() !=0) {
            Application.showMes(stringBuilder.toString());
            return true;
        }
        return false;
    }


    private void addMes(StringBuilder stringBuilder, String text) {
        if (stringBuilder.length() !=0) {
            stringBuilder.append(", ");
        }
        stringBuilder.append(text);
    }





    public boolean checkFio() {
        if (fio.getText() == null) {
            return true;
        }
        String fioText = fio.getText().replace(" ", "").replace(".", "");
        if (fioText.isEmpty()) {
            return true;
        }
        return fioText.chars().anyMatch(c -> !Character.UnicodeBlock.of(c).equals(Character.UnicodeBlock.CYRILLIC));
    }


}
