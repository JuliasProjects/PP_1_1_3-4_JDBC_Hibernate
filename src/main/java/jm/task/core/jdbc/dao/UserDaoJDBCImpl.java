package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    public UserDaoJDBCImpl() {
    }

    public void createUsersTable() throws SQLException {
        Connection connection = Util.getConnection();
        try {
            Statement statement = connection.createStatement();
            statement.execute("create table if not exists users" +
                    "(id int primary key not null auto_increment, name varchar(45)," +
                    " lastName varchar(45), age int not null )");

        } catch (SQLException e) {
            e.printStackTrace();
        }
        connection.close();

    }

    public void dropUsersTable() throws SQLException {
        Connection connection = Util.getConnection();
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate("drop table if exists users");

        } catch (SQLException e) {
            e.printStackTrace();
        }
        connection.close();

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
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM users")) {
            while (resultSet.next()) {
                long id = resultSet.getLong("id");
                User user = new User(resultSet.getString("name"),
                        resultSet.getString("lastName"), resultSet.getByte("age"));
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
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("truncate table users");
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            connection.rollback();
        }
        connection.close();
    }
}
