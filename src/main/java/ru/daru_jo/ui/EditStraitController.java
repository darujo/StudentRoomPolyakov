package ru.daru_jo.ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import ru.daru_jo.Application;
import ru.daru_jo.entity.User;
import ru.daru_jo.service.UserService;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashMap;

public class EditStraitController {
    @FXML
    public ChoiceBox<String> timeStart;
    @FXML
    public TextField codeStrait;
    @FXML
    public DatePicker dateStrait;
    @FXML
    public ChoiceBox<String> timeEnd;
    @FXML
    public Button generate;
    public HashMap<String, Integer> timeMap;
    @FXML
    public TextField requesterFIO;
    @FXML
    public TextField forIt;
    private UserService userService;

    @FXML
    public Button copyBuf;

    @FXML
    public void initialize() {
        // Этап инициализации JavaFX
        userService = UserService.getInstance();

    }


    public void reShow() {
        forIt.setText(null);
        codeStrait.setText(null);
    }

    @FXML
    public void generate(ActionEvent actionEvent) {
        if (checkAll()) {
            return;
        }
        User user = userService.addUser(
                dateStrait.getValue(),
                timeMap.get(timeStart.getValue()),
                timeMap.get(timeEnd.getValue()),
                requesterFIO.getText(),
                forIt.getText()
        );
        Application.getInstance().addUser(user);


    }


    @FXML
    public void copy(ActionEvent actionEvent) {
        copy(codeStrait.getText());
    }


    public static void copy(String text) {
        final Clipboard clipboard = Clipboard.getSystemClipboard();
        final ClipboardContent content = new ClipboardContent();

        content.putString(text);
        clipboard.setContent(content);
    }

    public void selectRegion(ActionEvent actionEvent) {
        selectRegion();
    }

    public void selectRegion() {
        shop.getItems().clear();
        if (region.getValue() != null) {
            codeService.getShops(region.getValue().getId()).forEach(shopObj -> shop.getItems().add(shopObj));
        }
    }

    public void selectShop(ActionEvent actionEvent) {
        cash.getItems().clear();
        if (shop.getValue() != null) {
            codeService.getCashs(shop.getValue().getId()).forEach(cashObj -> cash.getItems().add(cashObj));
        }
    }

    public boolean checkAll() {
        StringBuilder stringBuilder = new StringBuilder();
        if (checkRegion()) {
            addMes(stringBuilder, "Не выбран регион");
        }
        if (checkShop()) {
            addMes(stringBuilder, "Не выбран магазин");
        }
        if (checkCash()) {
            addMes(stringBuilder, "Не выбрана касса");
        }
        if (checkDate()) {
            addMes(stringBuilder, "Не задана дата формат даты ДД.ММ.ГГГГ");
        }
        if (checkTimeBeg()) {
            addMes(stringBuilder, "Не задано время начала");
        }
        if (checkTimeEnd()) {
            addMes(stringBuilder, "Не задано время конца");

        }
        if (checkTimeBegEnd()) {
            addMes(stringBuilder, "Время начала позже времи конца");
        }

        if (checkRequester()) {
            addMes(stringBuilder, "ФИО заказчика не может состоять только из пробелов и точек, ФИО должно состоять из букв кириллицы пробелов и точек");
        }

        if (checkForIt()) {
            addMes(stringBuilder, "Номер заявки в FOF-IT начинается с SC далее 10 цифр SC0000000000");
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

    public boolean checkRegion() {
        return region.getValue() == null || region.getValue().getName().equals("");
    }

    public boolean checkShop() {
        return shop.getValue() == null || shop.getValue().getCode() == null;
    }

    public boolean checkCash() {
        return cash.getValue() == null || cash.getValue().getNum() == null;
    }

    public boolean checkDate() {
//        System.out.println(dateStrait.getValue());
        System.out.println(dateStrait.getEditor().getText());
        try {
            LocalDate.parse(dateStrait.getEditor().getText(), DateTimeFormatter.ofPattern("dd.MM.yyyy"));
            return false;
        } catch (DateTimeParseException ex) {
            System.out.println(ex.getMessage());
            return true;
        }
    }

    public boolean checkTimeBeg() {
        return timeMap.get(timeStart.getValue()) == null;
    }

    public boolean checkTimeEnd() {
        return timeMap.get(timeEnd.getValue()) == null;
    }

    public boolean checkTimeBegEnd() {

        return timeMap.get(timeStart.getValue()) != null &&
                timeMap.get(timeEnd.getValue()) != null &&
                timeMap.get(timeStart.getValue()) > timeMap.get(timeEnd.getValue());
    }

    public boolean checkRequester() {
        if (requesterFIO.getText() == null) {
            return true;
        }
        String fio = requesterFIO.getText().replace(" ", "").replace(".", "");
        if (fio.isEmpty()) {
            return true;
        }
        return fio.chars().anyMatch(c -> !Character.UnicodeBlock.of(c).equals(Character.UnicodeBlock.CYRILLIC));
    }

    private boolean checkForIt() {
        if (forIt.getText() == null || forIt.getText().length() != 12) {
            return true;
        }
        if (!forIt.getText().substring(0, 2).equalsIgnoreCase("SC")) {
            return true;
        }
        try {
           Long.parseLong(forIt.getText().substring(2));
        }
        catch (NumberFormatException ex){
            return true;
        }
        return false;
    }

}
