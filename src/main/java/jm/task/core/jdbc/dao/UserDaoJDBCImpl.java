package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    Connection connection = Util.connection;

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        try (Connection connection = Util.getConnection()) {
            Statement statement = connection.createStatement();
            connection.setAutoCommit(false);
            statement.executeUpdate("USE users");
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS users" +
                    "(id BIGINT AUTO_INCREMENT" +
                    ", name VARCHAR(40)" +
                    ", lastName VARCHAR(40)" +
                    ", age TINYINT UNSIGNED" +
                    ", PRIMARY KEY(id) )");
            connection.commit();

        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException exc) {
                exc.printStackTrace();
            }
            System.out.println("Create table error");
        }
    }

    public void dropUsersTable() {
        try (Connection connection = Util.getConnection()) {
            Statement statement = connection.createStatement();
            connection.setAutoCommit(false);
            statement.executeUpdate("DROP TABLE IF EXISTS users");
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException exc) {
                exc.printStackTrace();
            }
            System.out.println("Drop table error");
            ;
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (Connection connection = Util.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("INSERT users (name, lastName, age) VALUES (?,?,?)");
            connection.setAutoCommit(false);
            statement.setString(1, name);
            statement.setString(2, lastName);
            statement.setByte(3, age);
            statement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException exc) {
                exc.printStackTrace();
            }
            System.out.println("Add user error");
            ;
        }
        System.out.println("User c именем - " + name + " добавлен в базу данных.");
    }

    public void removeUserById(long id) {
        try (Connection connection = Util.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM users WHERE id = ?");
            connection.setAutoCommit(false);
            statement.setLong(1, id);
            statement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException exc) {
                exc.printStackTrace();
            }
            System.out.println("Remove user error");
            ;
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        User user;

        try (Connection connection = Util.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM users");
            while (resultSet.next()) {
                user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastName"));
                user.setAge(resultSet.getByte("age"));
                users.add(user);
            }
        } catch (SQLException e) {
            System.out.println("Select users error");
            ;
        }
        return users;
    }

    public void cleanUsersTable() {
        try (Connection connection = Util.getConnection()) {
            Statement statement = connection.createStatement();
            connection.setAutoCommit(false);
            statement.executeUpdate("DELETE FROM users");
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException exc) {
                exc.printStackTrace();
            }
            System.out.println("Clean table error");
        }
    }
}
