package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private final Connection connection = Util.getConnection();

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        try (Statement statement = connection.createStatement()) {
            String sqlQuery = "create table if not exists users1" +
                    "(id int primary key auto_increment, name varchar(45), lastName varchar(45))";
            statement.executeUpdate(sqlQuery);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {

            if (connection != null) {
                Util.disconnect();
            }

        }


    }

    public void dropUsersTable() {
        try (Statement statement = connection.createStatement()) {
            String sqlQuery = "drop table if exists users1 ";
            statement.executeUpdate(sqlQuery);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {

            if (connection != null) {
                Util.disconnect();
            }

        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (PreparedStatement preparedStatement = connection.prepareStatement("insert into users1" +
                "(name, lastName, age) values (?, ?, ?)")) {

            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setInt(3, age);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {

            if (connection != null) {
                Util.disconnect();
            }

        }
    }

    public void removeUserById(long id) {
        try (Statement statement = connection.createStatement()) {
            String sqlQuery = "delete from users1 where id=?";
            statement.executeUpdate(sqlQuery);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {

            if (connection != null) {
                Util.disconnect();
            }

        }

    }

    public List<User> getAllUsers() {
        List<User> list = new ArrayList<>();


        try (Statement statement = connection.createStatement()) {
            String sqlQuery = "select * from users1;";
            ResultSet resultSet = statement.executeQuery(sqlQuery);
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastName"));
                user.setAge(resultSet.getByte("age"));
                list.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                Util.disconnect();
            }

        }

        return list;
    }

    public void cleanUsersTable() {
        String sqlQuery = "truncate table users1";
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sqlQuery);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {

            if (connection != null) {
                Util.disconnect();
            }

        }

    }
}
