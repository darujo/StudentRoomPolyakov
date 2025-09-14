package ru.daru_jo.ui;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import ru.daru_jo.Application;
import ru.daru_jo.entity.FilterUser;
import ru.daru_jo.entity.User;
import ru.daru_jo.service.UserService;

import java.io.File;
import java.util.List;


@SuppressWarnings("SpringJavaAutowiringInspection")
public class MainController {
    final FileChooser fileChooser = new FileChooser();
    FileChooser.ExtensionFilter allFiles = new FileChooser.ExtensionFilter("Все", "*.*");

    private UserService userService;

    @FXML
    private TableView<User> table;
    private ObservableList<User> data;

    /**
     * Инициализация контроллера от JavaFX.
     * Метод вызывается после того как FXML загрузчик произвел инъекции полей.
     * <p>
     * Обратите внимание, что имя метода <b>обязательно</b> должно быть "initialize",
     * в противном случае, метод не вызовется.
     * <p>
     * Также на этом этапе еще отсутствуют бины спринга
     * и для инициализации лучше использовать метод,
     * описанный аннотацией @PostConstruct,
     * который вызовется спрингом, после того, как им будут произведены все инъекции.
     * {@link MainController#init()}
     */
    @FXML
    public void initialize() {
        // Этап инициализации JavaFX
        init();
        userService =UserService.getInstance();
    }

    /**
     * На этом этапе уже произведены все возможные инъекции.
     */
    @SuppressWarnings("unchecked")
    public void init() {
        List<User> userList = userService.getUserList();
        data = FXCollections.observableArrayList(userList);


        TableColumn<User, String> fioColumn = new TableColumn<>("ФИО");
        fioColumn.setCellValueFactory(new PropertyValueFactory<>("fio"));

        TableColumn<User, String> specColumn = new TableColumn<>("Специальность");
        specColumn.setCellValueFactory(new PropertyValueFactory<>("specialization"));
        //
//        TableColumn<Strait, String> requesterColumn = new TableColumn<>("Заказчик");
//        requesterColumn.setCellValueFactory(new PropertyValueFactory<>("requester"));

        TableColumn<User, String> sexColumn = new TableColumn<>("Пол");
        sexColumn.setCellValueFactory(new PropertyValueFactory<>("sexStr"));

        TableColumn<User, String> roomColumn = new TableColumn<>("Комната");
        roomColumn.setCellValueFactory(new PropertyValueFactory<>("timeStartStr"));

        table.getColumns().setAll(fioColumn, specColumn,sexColumn,roomColumn);

        // Данные таблицы
        table.setItems(data);

    }

    /**
     * Метод, вызываемый при нажатии на кнопку "Добавить".
     * Привязан к кнопке в FXML файле представления.
     */
    public void addUser(User user) {
        data.add(user);
    }

    @FXML
    public void edit() {
        Platform.runLater(() ->
                Application.getInstance().editShow()
        );
    }

    public void setFilter(FilterUser filterStrait) {
        data.clear();
        data.setAll(UserService.findAll(filterStrait));
    }

    public void viewFilter(ActionEvent actionEvent) {
        Application.getInstance().filterShow();
    }

    public void printRep() {
        fileChooser.getExtensionFilters().clear();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Html", "*.html"),
                allFiles

        );
        File selectedFile = fileChooser.showSaveDialog(Application.getBrowseStage());
//        userService.printRep(selectedFile);
    }

    public void print(ActionEvent actionEvent) {
        printRep();
    }
}
