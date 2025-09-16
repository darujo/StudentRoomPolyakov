package ru.daru_jo;

import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ru.daru_jo.config.AbstractJavaFxApplicationSupport;
import ru.daru_jo.config.ControllersConfiguration;
import ru.daru_jo.dialogs.Dialogs;
import ru.daru_jo.entity.FilterUser;
import ru.daru_jo.entity.Room;
import ru.daru_jo.entity.User;
import ru.daru_jo.ui.BrowseRoomController;
import ru.daru_jo.ui.EditRoomController;
import ru.daru_jo.ui.EditUserController;
import ru.daru_jo.ui.MainController;

import java.io.IOException;

public class Application extends AbstractJavaFxApplicationSupport {
    private static Stage browseStage;

    private static Stage editStage;
    private Stage filterStage;
    private static Stage browseRoomStage;
    private static Stage editRoomStage;

    private final String windowTitle = "Студенты";

    private ControllersConfiguration.ViewHolder viewBrowse;

    public void setFilter(FilterUser filterStrait) {
        ((MainController) viewBrowse.getController()).setFilter(filterStrait);
    }

    private ControllersConfiguration.ViewHolder browseRoom;
    private ControllersConfiguration.ViewHolder editRoom;

    private ControllersConfiguration.ViewHolder viewEdit;
    private ControllersConfiguration.ViewHolder viewFilter;

    public void closeFilterStage() {
        filterStage.close();
    }

    public void openBrowseStage() {
        browseStage.show();

    }

    public static Stage getBrowseStage() {
        return browseStage;
    }

    @Override
    public void start(Stage stagePrimary) {
        ControllersConfiguration controllersConfiguration = new ControllersConfiguration();
        try {
            viewBrowse = controllersConfiguration.getMainView();
            viewEdit = controllersConfiguration.getEditView();
            browseRoom = controllersConfiguration.getBrowseRoomView();
            editRoom = controllersConfiguration.getEditRoomView();
            viewFilter = controllersConfiguration.getFilterView();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        browseStage = stagePrimary;
        initBrowseDialog();
        openBrowseStage();
    }

    private void initEditDialog() {
        editStage = new Stage();
        editStage.initOwner(browseStage);
        editStage.initModality(Modality.WINDOW_MODAL);

        editStage.setTitle(windowTitle);
        editStage.setScene(new Scene(viewEdit.getView()));
        editStage.setResizable(true);
        editStage.centerOnScreen();


    }

    private void initRoomBrowseDialog() {
        browseRoomStage = new Stage();
        browseRoomStage.initOwner(browseStage);
        browseRoomStage.initModality(Modality.WINDOW_MODAL);

        browseRoomStage.setTitle("Комнаты");
        browseRoomStage.setScene(new Scene(browseRoom.getView()));
        browseRoomStage.setResizable(true);
        browseRoomStage.centerOnScreen();
    }
    private void initRoomEditDialog() {
        editRoomStage = new Stage();
        editRoomStage.initOwner(browseRoomStage);
        editRoomStage.initModality(Modality.WINDOW_MODAL);

        editRoomStage.setTitle("Добавление комнаты");
        editRoomStage.setScene(new Scene(editRoom.getView()));
        editRoomStage.setResizable(true);
        editRoomStage.centerOnScreen();
    }

    private Stage initDialog(String title, Parent parent) {
        Stage stage = new Stage();
        stage.initOwner(browseStage);
        stage.initModality(Modality.WINDOW_MODAL);

        stage.setTitle(title);
        stage.setScene(new Scene(parent));
        stage.setResizable(true);
        stage.centerOnScreen();
        return stage;
    }

    private void initFilterDialog() {
        filterStage = initDialog("Фильтр", viewFilter.getView());

    }

    private void initBrowseDialog() {
        browseStage.setTitle(windowTitle);
        browseStage.setScene(new Scene(viewBrowse.getView()));
        browseStage.setResizable(true);
        browseStage.centerOnScreen();
    }

    public void editShow() {
        if (editStage == null) {
            initEditDialog();
        }
        ((EditUserController) viewEdit.getController()).reShow();
        editStage.show();
    }

    public void roomBrowseShow() {
        if (browseRoomStage == null) {
            initRoomBrowseDialog();
        }
        ((BrowseRoomController) browseRoom.getController()).reShow();
        browseRoomStage.show();
    }
    public void roomEditShow() {
        if (editRoomStage == null) {
            initRoomEditDialog();
        }
        ((EditRoomController) editRoom.getController()).reShow();
        editRoomStage.show();
    }

    public void filterShow() {
        if (filterStage == null) {
            initFilterDialog();
        }
//        ((EditStraitController) viewEdit.getController()).reShow();
        filterStage.show();
    }

    public static void showMes(String text) {
        Platform.runLater(() -> Dialogs.showDialog(Alert.AlertType.ERROR, "Ошибка", "Ошибка", text));
    }

    private static Application INSTANCE;

    @Override
    public void init() throws Exception {
        super.init();
        INSTANCE = this;

    }

    public static Application getInstance() {
        return INSTANCE;
    }


    public static void main(String[] args) {
        launchApp(Application.class, args);
    }

    public void addUser(User user) {
        ((MainController) viewBrowse.getController()).addUser(user);
    }

    public void addRoom(Room room) {
        ((BrowseRoomController) browseRoom.getController()).addRoom(room);
    }
}
