package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {

    private SessionFactory factory;
    public UserDaoHibernateImpl() {
        factory = Util.getFactory();
    }


    @Override
    public void createUsersTable() {
        String updSQL = "CREATE TABLE IF NOT EXISTS user (" +
                "id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT," +
                "first_name VARCHAR(50) ," +
                "last_name VARCHAR(50)," +
                "age TINYINT)";
        try (Session session = factory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.createSQLQuery(updSQL).addEntity(User.class).executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void dropUsersTable() {
        String updSQL = "DROP TABLE IF EXISTS user";
        try (Session session = factory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.createSQLQuery(updSQL).addEntity(User.class).executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Session session = factory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(new User(name,lastName,age));
            transaction.commit();
            System.out.printf("User с именем – %s добавлен в базу данных.\n", name);
        }
    }

    @Override
    public void removeUserById(long id) {
        String updSQL = "DELETE FROM user WHERE id = " + id;
        try (Session session = factory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.createSQLQuery(updSQL).executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String updSQL = "FROM User";
        try (Session session = factory.openSession()) {
            Transaction transaction = session.beginTransaction();
            users = session.createQuery(updSQL).getResultList();
            transaction.commit();
            users.stream().forEach(System.out::println);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public void cleanUsersTable() {
        String updSQL = "DELETE FROM user";
        try (Session session = factory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.createSQLQuery(updSQL).executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
