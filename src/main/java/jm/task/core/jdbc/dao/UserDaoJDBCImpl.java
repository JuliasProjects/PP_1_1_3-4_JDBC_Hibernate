package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    // Connection connection = Util.getConnection();

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        try {
            Connection connection = Util.getConnection();

            try (Statement statement = connection.createStatement()) {
                String sqlQuery = "create table if not exists users" +
                        "(id int primary key not null auto_increment, name varchar(45)," +
                        " lastName varchar(45), age int not null )";
                statement.execute(sqlQuery);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void dropUsersTable() {
        try {
            Connection connection = Util.getConnection();
            try (Statement statement = connection.createStatement()) {
                String sqlQuery = "drop table if exists forjdbcapp.users";
                statement.executeUpdate(sqlQuery);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) throws SQLException {
        Connection connection = Util.getConnection();
        connection.setAutoCommit(false);
        try (PreparedStatement preparedStatement = connection.prepareStatement("insert into users" +
                "(name, lastName, age) values (?, ?, ?)")) {

            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setInt(3, age);
            preparedStatement.executeUpdate();
            connection.commit();

        } catch (SQLException e) {
            e.printStackTrace();
            connection.rollback();
        }
        connection.close();
    }

    public void removeUserById(long id) throws SQLException {
        Connection connection = Util.getConnection();
        connection.setAutoCommit(false);
        try (PreparedStatement preparedStatement = connection.prepareStatement("delete from users where id=?")) {
            // String sqlQuery = "";
            preparedStatement.setLong(1, id);
            preparedStatement.execute();
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            connection.rollback();
        }
        connection.close();

    }

    public List<User> getAllUsers() throws SQLException {
        Connection connection = Util.getConnection();
        List<User> list = new ArrayList<>();
        connection.setAutoCommit(false);
        String sqlQuery = "SELECT id, name, lastName, age FROM users";
        try (Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery(sqlQuery);

            while (rs.next()) {
                long id = rs.getLong("id");
                User user = new User(rs.getString("name"),
                        rs.getString("lastName"), rs.getByte("age"));
                user.setId(id);
                list.add(user);
            }

            connection.commit();

        } catch (SQLException e) {
            e.printStackTrace();
            connection.rollback();
        }
        connection.close();

        return list;
    }

    public void cleanUsersTable() throws SQLException {
        Connection connection = Util.getConnection();
        connection.setAutoCommit(false);
        String sqlQuery = "truncate table users";
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sqlQuery);
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            connection.rollback();
        }

    }
}
