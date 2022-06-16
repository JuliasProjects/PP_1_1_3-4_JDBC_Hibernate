package jm.task.core.jdbc.service;

import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;

import java.util.ArrayList;
import java.util.List;

public class UserServiceImpl implements UserService {
   UserDao userDao = new UserDaoJDBCImpl(); 
    public void createUsersTable() {
        userDao.createUsersTable();
        System.out.println("Table created");
    }

    public void dropUsersTable() {
        userDao.dropUsersTable();
        System.out.println("Table dropped");
    }

    public void saveUser(String name, String lastName, byte age) {
        userDao.saveUser(name, lastName, age);
        System.out.println("User " + name + " has been added to the table");
    }

    public void removeUserById(long id) {
        userDao.removeUserById(id);
        System.out.println("User with id " + id + " has been removed");

    }

    public List<User> getAllUsers() {
        List<User> list = new ArrayList<>();
        for (User user:list) {
            System.out.println(user);

        }
        return list;
    }

    public void cleanUsersTable() {
        userDao.cleanUsersTable();


    }
}
