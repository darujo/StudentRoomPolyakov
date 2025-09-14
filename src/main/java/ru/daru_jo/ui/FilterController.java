package ru.daru_jo.ui;

import jakarta.annotation.PostConstruct;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import ru.daru_jo.Application;
import ru.daru_jo.entity.FilterUser;

public class FilterController {
    @FXML
    public TextField regionFx;
    @FXML
    public TextField shopFx;
    @FXML
    public TextField cashFx;
    @FXML
    public TextField requesterFX;
    @FXML
    public DatePicker dateGe;
    @FXML
    public DatePicker dateLe;
    @FXML
    public Button buttonOk;
    @FXML
    public Button buttonReset;
    @FXML
    public Button buttonCancel;
    public ChoiceBox<Integer> sizePage;

    @FXML
    public void saveFilter(ActionEvent actionEvent) {
        Platform.runLater(() ->
        Application.getInstance().setFilter(new FilterUser(
                sizePage.getValue(),
                regionFx.getText(),
                shopFx.getText().equals("") ? null : Integer.parseInt(shopFx.getText()),
                cashFx.getText().equals("") ? null : Integer.parseInt(cashFx.getText()),
                dateGe.getValue(),
                dateLe.getValue(),
                requesterFX.getText()))
        );
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
    private String oldShop;

    @PostConstruct
    private void init() {
        sizePage.getItems().setAll(25,50,100,1000,10000);
        sizePage.setValue(25);
    }
   public void changeNumberShop() {

        System.out.println("changeNumberShop");
        try {
            Integer.parseInt(shopFx.getText());
            oldShop = shopFx.getText();
        } catch (NumberFormatException e){
            System.out.println(e.getMessage());
            shopFx.setText(oldShop);
            shopFx.end();

        }
    }
    private String oldCash;
    public void changeNumberCash() {

        System.out.println("changeNumberCash");
        try {
            Integer.parseInt(cashFx.getText());
            oldCash = cashFx.getText();
        } catch (NumberFormatException e){
            System.out.println(e.getMessage());
            cashFx.setText(oldCash);
            cashFx.end();

        }
    }

    public void changeNumberShopFX(KeyEvent keyEvent) {
        Platform.runLater(this::changeNumberShop);
    }

    public void changeNumberCashFX(KeyEvent keyEvent) {
        Platform.runLater(this::changeNumberCash);
    }
}
