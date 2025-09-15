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

    public Optional<User> findByFioIgnoreCase(String fio) {
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
                return Optional.ofNullable(user);
            }
        } catch (SQLException e) {
            return null;
        } finally {
            ConnectionCenter.getInstance().disableConnect(connection);
        }
        return Optional.ofNullable(user);
    }

    public Optional<User> findByFioAndIdIsNot(String fio, Long id) {
        Connection connection = ConnectionCenter.getInstance().getConnection();
        User user = null;

        try (PreparedStatement prepInsert = connection.prepareStatement("SELECT id,fio, sex, specialization, room_id FROM usersStorage WHERE  fio = ? and id is not null ")) {
            prepInsert.setString(1, fio);
            ResultSet resultSet = prepInsert.executeQuery();
            while (resultSet.next()) {
                user = new User(resultSet.getLong("id"),
                        resultSet.getString("fio"),
                        resultSet.getInt("sex"),
                        resultSet.getString("specialization"),
                        resultSet.getLong("room_id")
                );
                return Optional.ofNullable(user);
            }
        } catch (SQLException e) {
            return null;
        } finally {
            ConnectionCenter.getInstance().disableConnect(connection);
        }
        return Optional.ofNullable(user);
    }

    public Optional<User> findById(Long id) {
        Connection connection = ConnectionCenter.getInstance().getConnection();
        User user = null;

        try (PreparedStatement prepInsert = connection.prepareStatement("SELECT id,fio, sex, specialization, room_id FROM usersStorage WHERE id = ? COLLATE NOCASE")) {
            prepInsert.setLong(1, id);
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

    public User save(User user) {
        try {
            if (user.getId() == null) {

                addUser(user);
            } else {
                updateUser(user);
            }
            return user;
        } catch (
                SQLException e) {
            throw new RuntimeException(e);
        }


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


    public void updateUser(User user) throws SQLException {
        Connection connection = ConnectionCenter.getInstance().getConnection();

        try (PreparedStatement prepInsert = connection.prepareStatement("UPDATE usersStorage SET  fio = ?, sex = ?, specialization = ?,room_id = ? where usersStorage.id = ? ")) {
            prepInsert.setString(1, user.getFio());
            prepInsert.setInt(2, user.getSex());
            prepInsert.setString(3, user.getSpecialization());
            prepInsert.setLong(4, user.getRoomId());
            prepInsert.setLong(4, user.getId());
            prepInsert.execute();
        } finally {
            ConnectionCenter.getInstance().disableConnect(connection);
        }
    }


    public List<User> findAll() {
        return new ArrayList<>();
    }

    public List<User> findAll(FilterUser filterUser) {
        List<User> users = new ArrayList<>();
        StringBuilder command;
        boolean first = true;
        command = new StringBuilder();
        if (filterUser.getFio() != null) {
            command.append("UPPER(fio) LIKE UPPER('%?%')");
            first = false;
        }
        if (filterUser.getRoomId() != null) {
            command.append(first ? "" : "AND ").append("room_id = ? ");
            first = false;
        }
        if (filterUser.getSex() != null) {
            command.append(first ? "" : "AND ").append("sex = ? ");
            first = false;
        }
        if (filterUser.getSpecialization() != null) {
            command.append(first ? "" : "AND ").append("specialization = ? ");
            first = false;
        }
        if (first) {
            command.insert(0, "SELECT id,fio, sex, specialization, room_id FROM usersStorage ");
        } else {
            command.insert(0, "SELECT id,fio, sex, specialization, room_id FROM usersStorage WHERE ");
        }
        command.append("where usersStorage.id = ? ");
        Connection connection = ConnectionCenter.getInstance().getConnection();

        try (PreparedStatement prepSelect = connection.prepareStatement(command.toString())) {
            int count = 1;
            if (filterUser.getFio() != null) {
                prepSelect.setString(count++, filterUser.getFio());
            }
            if (filterUser.getRoomId() != null) {
                prepSelect.setLong(count++, filterUser.getRoomId());
            }
            if (filterUser.getSex() != null) {
                prepSelect.setInt(count++, filterUser.getSex() ? 1 : 0);
            }
            if (filterUser.getSpecialization() != null) {
                prepSelect.setString(count++, filterUser.getSpecialization());
            }

            ResultSet resultSet = prepSelect.executeQuery();
            while (resultSet.next()) {
                User user = new User(resultSet.getLong("id"),
                        resultSet.getString("fio"),
                        resultSet.getInt("sex"),
                        resultSet.getString("specialization"),
                        resultSet.getLong("room_id")
                );
                users.add(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionCenter.getInstance().disableConnect(connection);
        }

        return users;
    }
}
