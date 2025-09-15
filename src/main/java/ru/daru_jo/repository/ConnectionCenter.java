package ru.daru_jo.repository;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class ConnectionCenter {
    private static ConnectionCenter Instance;
    private final String URL = "jdbc:sqlite:db\\storage.db";
    private final Map<Connection, Boolean> connectDBs = new HashMap<>();

    private ConnectionCenter() {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public Connection getConnection() {
        for (Map.Entry<Connection, Boolean> entry : connectDBs.entrySet()) {
            if (!entry.getValue()) {
                entry.setValue(true);
                return entry.getKey();
            }
        }
        try {
            Connection connection = DriverManager.getConnection(URL);
            connectDBs.put(connection, true);
            return connection;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void disableConnect(Connection connection) {
        connectDBs.put(connection, false);
    }

    public void addUser(String userName, String login, String password) throws SQLException {
        Connection connection = getConnection();

        try (PreparedStatement prepInsert = connection.prepareStatement("INSERT INTO usersStorage (name, login, password) VALUES (?,?,?)")) {
            prepInsert.setString(1, userName);
            prepInsert.setString(2, login);
            prepInsert.setString(3, password);
            prepInsert.execute();
        } finally {
            disableConnect(connection);
        }
    }


//    public User getUser(String login) {
//        Connection connection = getConnection();
//        User user = null;
//
//        try (PreparedStatement prepInsert = connection.prepareStatement("SELECT id,name, password, token FROM usersStorage WHERE  login = ? ")) {
//            prepInsert.setString(1, login);
//            ResultSet resultSet = prepInsert.executeQuery();
//            while (resultSet.next()) {
//                user = new User(resultSet.getInt("id"),
//                        resultSet.getString("name"),
//                        login,
//                        resultSet.getString("password"),
//                        resultSet.getString("token"));
//            }
//        } catch (SQLException e) {
//            return null;
//        } finally {
//            disableConnect(connection);
//        }
//        return user;
//    }

//    private User getUser(int userId) throws SQLException {
//        Connection connection = getConnection();
//        User user = null;
//
//        try (PreparedStatement prepInsert = connection.prepareStatement("SELECT login, name, password, token FROM usersStorage WHERE  id = ? ")) {
//            prepInsert.setInt(1, userId);
//            ResultSet resultSet = prepInsert.executeQuery();
//            while (resultSet.next()) {
//                user = new User(userId,
//                        resultSet.getString("name"),
//                        resultSet.getString("login"),
//                        resultSet.getString("password"),
//                        resultSet.getString("token"));
//            }
//
//        } finally {
//            disableConnect(connection);
//        }
//        return user;
//
//    }


    public static ConnectionCenter getInstance() {
        if (Instance == null) {
            Instance = new ConnectionCenter();
        }
        return Instance;
    }

    public static void close() {
        if (Instance == null) {
            return;
        }
        try {
            for (Map.Entry<Connection, Boolean> entry : Instance.connectDBs.entrySet()) {

                entry.getKey().close();

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


//    private AuthMessage setUserData(int id, String loginNew, String userNameNew, String passwordNew) {
//        StringBuilder command;
//        boolean first = true;
//        command = new StringBuilder("UPDATE usersStorage SET ");
//        if (userNameNew != null) {
//            command.append("name = ? ");
//            first = false;
//        }
//        if (loginNew != null) {
//            command.append(first ? "" : ", ").append("login = ? ");
//            first = false;
//        }
//        if (passwordNew != null) {
//            command.append(first ? "" : ", ").append("password = ? ");
//            first = false;
//        }
//        if (first) {
//            return AuthMessage.USER_DATA_NOT_CHANGE;
//        } else {
//            command.append("where usersStorage.id = ? ");
//            Connection connection = getConnection();
//
//            try (PreparedStatement prepInsert = connection.prepareStatement(command.toString())) {
//                int count = 1;
//                if (userNameNew != null) {
//                    prepInsert.setString(count++, userNameNew);
//                }
//                if (loginNew != null) {
//                    prepInsert.setString(count++, loginNew);
//                }
//                if (passwordNew != null) {
//                    prepInsert.setString(count++, passwordNew);
//                }
//                prepInsert.setInt(count, id);
//
//                prepInsert.execute();
//            } catch (SQLException e) {
//                return AuthMessage.AUTH_SQL_ERROR;
//            } finally {
//                disableConnect(connection);
//            }
//            return AuthMessage.USER_DATA_CHANGE_OK;
//        }
//    }

//    public void setToken(int userId) throws SQLException {
//        Connection connection = getConnection();
//
//        try {
//            String uuid = getNewUUID(connection);
//            try (PreparedStatement prepUpdate = connection.prepareStatement("UPDATE usersStorage SET token = ?, tokenTime = ? where usersStorage.id = ? ")) {
//
//                prepUpdate.setString(1, uuid);
//                prepUpdate.setString(2, LocalDateTime.now().toString());
//                prepUpdate.setInt(3, userId);
//                prepUpdate.execute();
//            }
//        } finally {
//            disableConnect(connection);
//        }
//    }
//
//    public void setToken(String login) throws SQLException {
//        Connection connection = getConnection();
//
//        try {
//            String uuid = getNewUUID(connection);
//            try (PreparedStatement prepUpdate = connection.prepareStatement("UPDATE usersStorage SET token = ?, tokenTime = ? where usersStorage.login = ? ")) {
//
//                prepUpdate.setString(1, uuid);
//                prepUpdate.setString(2, LocalDateTime.now().toString());
//                prepUpdate.setString(3, login);
//                prepUpdate.execute();
//
//            }
//        } finally {
//            disableConnect(connection);
//        }
//
//    }
//
//    private String getNewUUID(Connection connection) throws SQLException {
//        String uuid = null;
//        boolean guidAvail = true;
//        while (guidAvail) {
//            uuid = UUID.randomUUID().toString();
//            try (PreparedStatement prepSelect = connection.prepareStatement("SELECT token  FROM usersStorage WHERE  token = ? ")) {
//                prepSelect.setString(1, uuid);
//                ResultSet resultSet = prepSelect.executeQuery();
//                guidAvail = resultSet.next();
//
//            }
//        }
//        return uuid;
//    }
//
//    public void setTokenTime(String token) throws SQLException {
//        Connection connection = getConnection();
//
//        try (PreparedStatement prepUpdate = connection.prepareStatement("UPDATE usersStorage SET tokenTime = ? where token = ? ")) {
//
//            prepUpdate.setString(1, LocalDateTime.now().toString());
//            prepUpdate.setString(2, token);
//
//            prepUpdate.execute();
//
//        } finally {
//            disableConnect(connection);
//        }
//    }
//
//    public boolean checkToken(String token) {
//        Connection connection = getConnection();
//        try (PreparedStatement prepSelect = connection.prepareStatement("SELECT tokentime  FROM usersStorage WHERE  token = ? ")) {
//            prepSelect.setString(1, token);
//            ResultSet resultSet = prepSelect.executeQuery();
//            LocalDateTime localDateTime = null;
//            while (resultSet.next()) {
//                localDateTime = LocalDateTime.parse(resultSet.getString("tokentime"));
//            }
//            if (localDateTime != null) {
//
//                boolean tokenOK = LocalDateTime.now().isBefore(localDateTime.plusMinutes(AVAIL_TOKEN_MIN));
//                if (tokenOK) {
//                    setTokenTime(token);
//                }
//                return tokenOK;
//            } else {
//                return false;
//            }
//
//        } catch (SQLException e) {
//            return false;
//        } finally {
//            disableConnect(connection);
//        }
//
//    }
//
//    public Integer getUserId(String token) {
//        Connection connection = getConnection();
//        try (PreparedStatement prepInsert = connection.prepareStatement("SELECT id FROM usersStorage WHERE  token = ? ")) {
//            prepInsert.setString(1, token);
//            ResultSet resultSet = prepInsert.executeQuery();
//            while (resultSet.next()) {
//                return resultSet.getInt("id");
//            }
//            return null;
//        } catch (SQLException e) {
//            return null;
//        } finally {
//            disableConnect(connection);
//        }
//
//    }
//
//    public String getShareGuid(Integer ownerId, String file) {
//
//        Connection connection = getConnection();
//        String query;
//        if (ownerId == null) {
//            query = "SELECT shareGuid FROM shareStorage WHERE  shareFile = ?";
//        } else {
//            query = "SELECT shareGuid FROM shareStorage WHERE  shareFile = ?  and ownerId = ?";
//        }
//        try (PreparedStatement prepInsert = connection.prepareStatement(query)) {
//
//            prepInsert.setString(1, file);
//            if (ownerId != null) {
//                prepInsert.setInt(2, ownerId);
//            }
//            ResultSet resultSet = prepInsert.executeQuery();
//            while (resultSet.next()) {
//                return resultSet.getString("shareGuid");
//            }
//            return null;
//        } catch (SQLException e) {
//            return "";
//        } finally {
//            disableConnect(connection);
//        }
//
//    }
//
//    public String getShareFile(Integer ownerId, String guid) throws SQLException {
//        Connection connection = getConnection();
//        String query;
//        if (ownerId == null) {
//            query = "SELECT shareFile FROM shareStorage WHERE  shareGuid = ?";
//        } else {
//            query = "SELECT shareFile FROM shareStorage WHERE  shareGuid = ?  and ownerId = ?";
//        }
//        try (PreparedStatement prepInsert = connection.prepareStatement(query)) {
//            prepInsert.setString(1, guid);
//            if (ownerId != null) {
//                prepInsert.setInt(2, ownerId);
//            }
//
//            ResultSet resultSet = prepInsert.executeQuery();
//            while (resultSet.next()) {
//                return resultSet.getString("shareFile");
//            }
//            delShareLink(guid);
//            return null;
//        } finally {
//            disableConnect(connection);
//        }
//
//    }
//
//    private String getNewShareGuid(Connection connection) throws SQLException {
//        String uuid = null;
//        boolean guidAvail = true;
//        while (guidAvail) {
//            uuid = UUID.randomUUID().toString();
//            try (PreparedStatement prepSelect = connection.prepareStatement("SELECT shareGuid FROM shareStorage WHERE  shareGuid = ? ")) {
//                prepSelect.setString(1, uuid);
//                ResultSet resultSet = prepSelect.executeQuery();
//                guidAvail = resultSet.next();
//
//            }
//        }
//        return uuid;
//    }
//
//
//    public String addShareFile(int ownerId, String shareFile) throws SQLException {
//        Connection connection = getConnection();
//        try {
//            String newShareGuid = getNewShareGuid(connection);
//            try (PreparedStatement prepInsert = connection.prepareStatement("INSERT INTO shareStorage (ownerId, shareGuid, shareFile) VALUES (?,?,?)")) {
//                prepInsert.setInt(1, ownerId);
//                prepInsert.setString(2, newShareGuid);
//                prepInsert.setString(3, shareFile);
//                prepInsert.execute();
//                return newShareGuid;
//            }
//        } finally {
//            disableConnect(connection);
//        }
//    }
//
//    public String updateShareGuid(int ownerId, String shareFile) throws SQLException {
//        if (getShareGuid(ownerId, shareFile) == null) {
//            return addShareFile(ownerId, shareFile);
//        } else {
//            Connection connection = getConnection();
//            try {
//                String newShareGuid = getNewShareGuid(connection);
//                try (PreparedStatement prepInsert = connection.prepareStatement("UPDATE shareStorage SET  shareGuid = ? where ownerId = ? and shareFile = ? ")) {
//                    prepInsert.setString(1, newShareGuid);
//                    prepInsert.setInt(2, ownerId);
//                    prepInsert.setString(3, shareFile);
//                    prepInsert.execute();
//                    return newShareGuid;
//                }
//            } finally {
//                disableConnect(connection);
//            }
//        }
//    }
//
//    public boolean getShareLink(int userId, String guid) {
//        Connection connection = getConnection();
//        try (PreparedStatement prepInsert = connection.prepareStatement("SELECT shareGuid FROM linkShareStorage WHERE  recipientId = ?  and shareGuid = ?")) {
//            prepInsert.setInt(1, userId);
//            prepInsert.setString(2, guid);
//
//
//            ResultSet resultSet = prepInsert.executeQuery();
//            while (resultSet.next()) {
//                return true;
//            }
//            return false;
//        } catch (SQLException e) {
//            return false;
//        } finally {
//            disableConnect(connection);
//        }
//
//    }
//
//    public void delShareLink(String guid) {
//        Connection connection = getConnection();
//        try (PreparedStatement prepInsert = connection.prepareStatement("DELETE FROM linkShareStorage WHERE shareGuid = ?")) {
//            prepInsert.setString(1, guid);
//            prepInsert.execute();
//        } catch (SQLException ignore) {
//        } finally {
//            disableConnect(connection);
//        }
//
//    }
//
//    public void updateShareLink(int userId, String guid) throws SQLException {
//        if (!getShareLink(userId, guid)) {
//            addShareLink(userId, guid);
//        }
//    }
//
//    private void addShareLink(int userId, String guid) throws SQLException {
//        Connection connection = getConnection();
//        try (PreparedStatement prepInsert = connection.prepareStatement("INSERT INTO linkShareStorage (recipientId, shareGuid) VALUES (?,?)")) {
//            prepInsert.setInt(1, userId);
//            prepInsert.setString(2, guid);
//            prepInsert.execute();
//        } finally {
//            disableConnect(connection);
//        }
//    }
//
//    public Map<String, String> getShareLinkDir(int userId) {
//        Map<String, String> dirList = new HashMap<>();
//        Connection connection = getConnection();
//        try (PreparedStatement prepInsert = connection.prepareStatement(
//                "SELECT shareStorage.shareGuid shareGuid, shareFile FROM linkShareStorage,shareStorage where linkShareStorage.recipientId = ? and shareStorage.shareGuid = linkShareStorage.shareGuid")) {
//            prepInsert.setInt(1, userId);
//
//            ResultSet resultSet = prepInsert.executeQuery();
//            while (resultSet.next()) {
//                dirList.put(resultSet.getString("shareGuid"), Path.of(resultSet.getString("shareFile")).toFile().getName());
//            }
//            return dirList;
//        } catch (SQLException e) {
//            return null;
//        } finally {
//            disableConnect(connection);
//        }
//
//    }
//
//    public String getShareFilePath(int userId, String guid) throws SQLException {
//        if (getShareLink(userId, guid)) {
//            return getShareFile(null, guid);
//        } else {
//            return getShareFile(userId, guid);
//        }
//
//    }

}