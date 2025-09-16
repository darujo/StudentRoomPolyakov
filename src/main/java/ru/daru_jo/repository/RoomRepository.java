package ru.daru_jo.repository;

import ru.daru_jo.entity.FilterRoom;
import ru.daru_jo.entity.Room;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RoomRepository {

    public List<Room> findAll(FilterRoom filterRoom) {
        List<Room> rooms = new ArrayList<>();
        StringBuilder command;
        boolean first = true;
        command = new StringBuilder();
        if (filterRoom.getSex() != null) {
            command.append("sex = ? ");
            first = false;
        }
        if (filterRoom.getSpecialization() != null) {
            command.append(first ? "" : "AND ").append("specialization = ? ");
            first = false;
        }
        if (first) {
            command.insert(0, "SELECT id,name, sex, specialization, max_student FROM roomsStorage ");
        } else {
            command.insert(0, "SELECT id,name, sex, specialization, max_student FROM roomsStorage WHERE ");
        }
//        command.append("where roomsStorage.id = ? ");
        Connection connection = ConnectionCenter.getInstance().getConnection();

        try (PreparedStatement prepSelect = connection.prepareStatement(command.toString())) {
            int count = 1;
            if (filterRoom.getSex() != null) {
                prepSelect.setInt(count++, filterRoom.getSex() ? 1 : 0);
            }
            if (filterRoom.getSpecialization() != null) {
                prepSelect.setString(count, filterRoom.getSpecialization());
            }

            ResultSet resultSet = prepSelect.executeQuery();
            while (resultSet.next()) {
                Room room = new Room(resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getInt("sex"),
                        resultSet.getString("specialization"),
                        resultSet.getInt("max_student")
                );
                rooms.add(room);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionCenter.getInstance().disableConnect(connection);
        }

        return rooms;
    }

    public Room save(Room room) {
        try {
            if (room.getId() == null) {

                room = addRoom(room);
            }
            return room;
        } catch (
                SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public Room addRoom(Room room) throws SQLException {
        int id = findLast() + 1;
        Connection connection = ConnectionCenter.getInstance().getConnection();

        try (PreparedStatement prepInsert = connection.prepareStatement("INSERT INTO roomsStorage (sex, specialization, max_student, name, id) VALUES (?,?,?,?,?)")) {
            prepInsert.setInt(1, room.getSex());
            prepInsert.setString(2, room.getSpecialization());
            prepInsert.setInt(3, room.getMaxStudent());
            prepInsert.setString(4, room.getName());

            prepInsert.setInt(5, id);

            prepInsert.execute();
            room.setId(id);
            return room;
        } finally {
            ConnectionCenter.getInstance().disableConnect(connection);
        }
    }
    public int findLast() {
        Connection connection = ConnectionCenter.getInstance().getConnection();
        int id = 0;
        try (PreparedStatement prepInsert = connection.prepareStatement("SELECT id FROM roomsStorage ORDER BY id DESC LIMIT 1;")) {

            ResultSet resultSet = prepInsert.executeQuery();
            while (resultSet.next()) {
                id = resultSet.getInt("id");

            }
            return id;
        } catch (SQLException e) {
            return 0;
        } finally {
            ConnectionCenter.getInstance().disableConnect(connection);

        }

    }

}
