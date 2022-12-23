package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class Util {
    // реализуйте настройку соеденения с БД
    private static final String URL = "jdbc:mysql://localhost:3306/users";
    private static final String USER = "root";
    private static final String PASSWORD = "root";
    public static Connection connection;
    private static SessionFactory factory;


    public static Connection getConnection() {
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println(connection.isClosed());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return connection;
    }

    public static SessionFactory getSessionFactory() {
        if (factory == null) {
            try {
                Configuration config = new Configuration()
                        .setProperty(Environment.DRIVER, "com.mysql.cj.jdbc.Driver")
                        .setProperty(Environment.URL, URL)
                        .setProperty(Environment.USER, USER)
                        .setProperty(Environment.PASS, PASSWORD)
                        .setProperty(Environment.DIALECT, "org.hibernate.dialect.MySQLDialect")
                        .addAnnotatedClass(User.class);

                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                        .applySettings(config.getProperties()).build();
                factory = config.buildSessionFactory(serviceRegistry);
            } catch (HibernateException e) {
                e.printStackTrace();
            }
        }
        return factory;
    }
}