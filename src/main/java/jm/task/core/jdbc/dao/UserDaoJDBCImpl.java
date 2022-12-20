package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        try(Statement statement =Util.getConnection().createStatement()) {
            statement.executeUpdate("USE users");
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS users" +
                    "(id BIGINT AUTO_INCREMENT" +
                    ", name VARCHAR(40)" +
                    ", lastName VARCHAR(40)" +
                    ", age TINYINT UNSIGNED" +
                    ", PRIMARY KEY(id) )");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dropUsersTable() {
        try(Statement statement = Util.getConnection().createStatement()) {
            statement.executeUpdate("DROP TABLE IF EXISTS users");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try(PreparedStatement statement = Util.getConnection().prepareStatement("INSERT users (name, lastName, age) VALUES (?,?,?)")) {
            statement.setString(1, name);
            statement.setString(2, lastName);
            statement.setByte(3, age);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("User c именем - " + name + " добавлен в базу данных.");
    }

    public void removeUserById(long id) {
        try(PreparedStatement statement = Util.getConnection().prepareStatement("DELETE FROM users WHERE id = ?")) {
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        User user;

        try(Statement statement = Util.getConnection().createStatement()) {
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
            e.printStackTrace();
        }
        return users;
    }

    public void cleanUsersTable() {
        try(Statement statement = Util.getConnection().createStatement()) {
            statement.executeUpdate("DELETE FROM  users");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
