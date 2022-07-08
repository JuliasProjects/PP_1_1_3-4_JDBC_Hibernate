package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserDaoJDBCImpl implements UserDao {
    private static final Logger logger = Logger.getLogger(UserDaoJDBCImpl.class.getCanonicalName());


    public UserDaoJDBCImpl() {
    }

    public void createUsersTable() {
        try (Connection connection = Util.getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute("create table if not exists users" +
                    "(id int primary key not null auto_increment, name varchar(45)," +
                    " lastName varchar(45), age int not null )");
        } catch (SQLException e) {
            logger.log(Level.SEVERE, e.getMessage());
        }

    }

    public void dropUsersTable() {
        try (Connection connection = Util.getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate("drop table if exists users");
        } catch (SQLException e) {
            logger.log(Level.SEVERE, e.getMessage());
        }

    }

    public void saveUser(String name, String lastName, byte age){
        try (Connection connection = Util.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("insert into users" +
                     "(name, lastName, age) values (?, ?, ?)")) {
            connection.setAutoCommit(false);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setInt(3, age);
            preparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, e.getMessage());
        }
    }

    public void removeUserById(long id){
        try (Connection connection = Util.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("delete from users where id=?")) {
            connection.setAutoCommit(false);
            preparedStatement.setLong(1, id);
            preparedStatement.execute();
            connection.commit();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, e.getMessage());
        }
    }

    public List<User> getAllUsers(){
        List<User> list = new ArrayList<>();
        try (Connection connection = Util.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM users")) {
            connection.setAutoCommit(false);
            while (resultSet.next()) {
                long id = resultSet.getLong("id");
                User user = new User(resultSet.getString("name"),
                        resultSet.getString("lastName"), resultSet.getByte("age"));
                user.setId(id);
                list.add(user);
            }

            connection.commit();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, e.getMessage());
        }
        return list;
    }

    public void cleanUsersTable(){
        try (Connection connection = Util.getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate("truncate table users");
            connection.setAutoCommit(false);
            connection.commit();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, e.getMessage());
        }
    }
}
