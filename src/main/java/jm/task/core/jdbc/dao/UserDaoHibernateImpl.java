package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.HibernateException;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private static Session session;

    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        try (Session session = Util.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.createSQLQuery("CREATE TABLE IF NOT EXISTS users" +
                            "(id BIGINT NOT NULL AUTO_INCREMENT" +
                            ", name VARCHAR(40)" +
                            ", lastName VARCHAR(40)" +
                            ", age TINYINT UNSIGNED" +
                            ", PRIMARY KEY(id) )")
                    .executeUpdate();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            System.out.println("Create table error");
            session.getTransaction().rollback();
        }
    }

    @Override
    public void dropUsersTable() {
        try (Session session = Util.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.createSQLQuery("DROP TABLE IF EXISTS users")
                    .executeUpdate();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            System.out.println("Drop table error");
            session.getTransaction().rollback();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Session session = Util.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.save(new User(name, lastName, age));
            session.getTransaction().commit();
        } catch (HibernateException e) {
            System.out.println("Save user error");
            session.getTransaction().rollback();
        }
        System.out.println("User c именем - " + name + " добавлен в базу данных.");
    }

    @Override
    public void removeUserById(long id) {
        try (Session session = Util.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.delete(session.get(User.class, id));
            session.getTransaction().commit();
        } catch (HibernateException e) {
            System.out.println("Remove user error");
            session.getTransaction().rollback();
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try (Session session = Util.getSessionFactory().openSession()) {
            session.beginTransaction();
            users = session.createSQLQuery("SELECT * FROM users")
                    .addEntity(User.class).list();
            System.out.println(users);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            System.out.println("Get users error");
            session.getTransaction().rollback();
        }
        return users;
    }

    @Override
    public void cleanUsersTable() {
        try (Session session = Util.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.createSQLQuery("DELETE FROM users")
                    .executeUpdate();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            System.out.println("Clean table error");
            session.getTransaction().rollback();
        }
    }
}
