package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    public static void main(String[] args) {

        UserServiceImpl userServiceImpl = new UserServiceImpl();
        userServiceImpl.createUsersTable();

        userServiceImpl.saveUser("First", "LastFirst", (byte) 18);
        userServiceImpl.saveUser("Second", "LastSecond", (byte) 20);
        userServiceImpl.saveUser("Last", "LastLast", (byte) 22);
        userServiceImpl.saveUser("AndOne", "More", (byte) 25);

        userServiceImpl.removeUserById(2);

        userServiceImpl.getAllUsers();

        userServiceImpl.cleanUsersTable();
        userServiceImpl.dropUsersTable();
    }
}
