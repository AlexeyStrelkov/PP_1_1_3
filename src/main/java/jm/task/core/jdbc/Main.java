package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;

import java.sql.SQLException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws SQLException {
        // реализуйте алгоритм здесь
        UserServiceImpl userServiceImpl = new UserServiceImpl();
        userServiceImpl.createUsersTable();

        userServiceImpl.saveUser("First", "LastFirst", (byte) 18);
        userServiceImpl.saveUser("Second", "LastSecond", (byte) 20);
        userServiceImpl.saveUser("Last", "LastLast", (byte) 22);
        userServiceImpl.saveUser("AndOne", "More", (byte) 25);

        userServiceImpl.removeUserById(2);

        List<User> users = userServiceImpl.getAllUsers();
        System.out.println(users.toString());

        userServiceImpl.cleanUsersTable();
        userServiceImpl.dropUsersTable();
    }
}
