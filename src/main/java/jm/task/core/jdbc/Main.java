package jm.task.core.jdbc;


import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

import java.sql.SQLException;


public class Main {
    public static void main(String[] args) throws SQLException {
        UserService us = new UserServiceImpl();
        us.createUsersTable();

        us.saveUser("Ivan", "Ivanov", (byte) 30);
        us.saveUser("Just", "Me", (byte) 28);
        us.saveUser("Alex", "Ovechkin", (byte) 36);
        us.saveUser("Wayne", "Gretzky", (byte) 61);

        for (User users : us.getAllUsers()) {
            System.out.println(users);

        }

        us.cleanUsersTable();
        us.dropUsersTable();
    }
}
