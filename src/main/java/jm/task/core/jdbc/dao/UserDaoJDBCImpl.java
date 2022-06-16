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
            String sqlQuery = "create table if not exists users" +
                    "(id int primary key not null auto_increment, name varchar(45)," +
                    " lastName varchar(45), age int not null )";
            statement.execute(sqlQuery);
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    public void dropUsersTable() {
        try (Statement statement = connection.createStatement()) {
            String sqlQuery = "drop table if exists forjdbcapp.users";
            statement.executeUpdate(sqlQuery);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (PreparedStatement preparedStatement = connection.prepareStatement("insert into forjdbcapp.users" +
                "(name, lastName, age) values (?, ?, ?)")) {

            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setInt(3, age);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        try (PreparedStatement preparedStatement = connection.prepareStatement("delete from forjdbcapp.users where id=?")) {
           // String sqlQuery = "";
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public List<User> getAllUsers() {
        List<User> list = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            String sqlQuery = "select * from forjdbcapp.users;";
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
        } /*finally {
            if (connection != null) {
                Util.disconnect();
            }


        }
        */

        return list;
    }

    public void cleanUsersTable() {
        String sqlQuery = "truncate table forjdbcapp.users";
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sqlQuery);
            System.out.println();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
