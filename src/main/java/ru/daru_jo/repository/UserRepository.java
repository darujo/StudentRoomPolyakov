package ru.daru_jo.repository;

import ru.daru_jo.entity.FilterUser;
import ru.daru_jo.entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserRepository {

    public Optional<User> findByFio(String fio) {
        Connection connection = ConnectionCenter.getInstance().getConnection();
        User user = null;

        try (PreparedStatement prepInsert = connection.prepareStatement("SELECT id,fio, sex, specialization, room_id FROM usersStorage WHERE  fio = ? ")) {
            prepInsert.setString(1, fio);
            ResultSet resultSet = prepInsert.executeQuery();
            while (resultSet.next()) {
                user = new User(resultSet.getLong("id"),
                        resultSet.getString("fio"),
                        resultSet.getInt("sex"),
                        resultSet.getString("specialization"),
                        resultSet.getLong("room_id")
                        );
            }
        } catch (SQLException e) {
            return null;
        } finally {
            ConnectionCenter.getInstance().disableConnect(connection);
        }
        return Optional.ofNullable(user);
    }

    public Optional<User> findByFioAndIdIsNot(String fio, Long id) {
        return Optional.empty();
    }

    public Optional<User> findById(Long id) {
        return Optional.empty();
    }

    public Optional<User> findByFioIgnoreCase(String fio) {
        return Optional.empty();

    }

    public User save(User user) {
        return user;
    }

    public void addUser(User user) throws SQLException {
        Connection connection = ConnectionCenter.getInstance().getConnection();

        try (PreparedStatement prepInsert = connection.prepareStatement("INSERT INTO usersStorage (fio, sex, specialization,room_id) VALUES (?,?,?,?)")) {
            prepInsert.setString(1, user.getFio());
            prepInsert.setInt(2, user.getSex());
            prepInsert.setString(3, user.getSpecialization());
            prepInsert.setLong(4, user.getRoomId());
            prepInsert.execute();
        } finally {
            ConnectionCenter.getInstance().disableConnect(connection);
        }
    }

    public List<User> findAll() {
        return new ArrayList<>();
    }

    public List<User> findAll(FilterUser filterStrait) {
        return new ArrayList<>();
    }
}
