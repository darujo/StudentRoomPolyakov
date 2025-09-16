package ru.daru_jo.ui;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import ru.daru_jo.Application;
import ru.daru_jo.entity.Room;
import ru.daru_jo.entity.RoomMax;
import ru.daru_jo.service.RoomService;

import java.util.List;


public class BrowseRoomController {

    private RoomService roomService;

    @FXML
    private TableView<RoomMax> table;
    private ObservableList<RoomMax> data;

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
     * {@link BrowseRoomController#init()}
     */
    @FXML
    public void initialize() {
        // Этап инициализации JavaFX
        roomService = RoomService.getInstance();
        init();

    }

    /**
     * На этом этапе уже произведены все возможные инъекции.
     */
    public void init() {
        List<RoomMax> rooms = roomService.getRoomMaxList(null, null);
        data = FXCollections.observableArrayList(rooms);

        TableColumn<RoomMax, String> idColumn = new TableColumn<>("Номер");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<RoomMax, String> nameColumn = new TableColumn<>("Название");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<RoomMax, String> specColumn = new TableColumn<>("Специальность");
        specColumn.setCellValueFactory(new PropertyValueFactory<>("specialization"));

        TableColumn<RoomMax, String> sexColumn = new TableColumn<>("Пол");
        sexColumn.setCellValueFactory(new PropertyValueFactory<>("sexStr"));

        TableColumn<RoomMax, String> countStudentColumn = new TableColumn<>("Заселено студентов");
        countStudentColumn.setCellValueFactory(new PropertyValueFactory<>("countStudent"));

        TableColumn<RoomMax, String> maxStudentColumn = new TableColumn<>("Заселено студентов");
        maxStudentColumn.setCellValueFactory(new PropertyValueFactory<>("maxStudent"));


        table.getColumns().setAll(idColumn, nameColumn, specColumn, sexColumn, countStudentColumn, maxStudentColumn);

        // Данные таблицы
        table.setItems(data);

    }

    public void reShow() {
        data.clear();
        init();
    }

    /**
     * Метод, вызываемый при нажатии на кнопку "Добавить".
     * Привязан к кнопке в FXML файле представления.
     */
    public void addRoom(Room room) {
        data.add(roomService.roomToRoomMax(room));
    }

    @FXML
    public void edit() {
        Platform.runLater(() ->
                Application.getInstance().roomEditShow()
        );
    }

}
