package jm.task.core.jdbc;


import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;


public class Main {
    public static void main(String[] args) {
         final  UserService userService = new UserServiceImpl();

        //drop
        userService.createUsersTable();

        userService.saveUser("John", "Doe", (byte) 27);
        userService.saveUser("Jane", "Doe", (byte) 16);
        userService.saveUser("Adam", "Smith", (byte) 87);
        userService.saveUser("Ellen", "Smith", (byte) 99);

        for (User users : userService.getAllUsers()) {
            userService.getAllUsers();
            System.out.println(users);

        }



        userService.removeUserById(3);
        userService.cleanUsersTable();
        userService.dropUsersTable();
    }
}
