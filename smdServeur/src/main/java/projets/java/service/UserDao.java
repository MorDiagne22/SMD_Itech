package projets.java.service;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import projets.java.model.Produit;
import projets.java.model.User;
import projets.java.utils.HibernateUtil;

import java.math.BigInteger;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class UserDao extends UnicastRemoteObject implements IUser {
    private Session session;
    public UserDao() throws RemoteException {
        session = HibernateUtil.getSession();
    }

    @Override
    public List<User> getAllUsers() throws RemoteException {
        return session.createQuery("SELECT p FROM User p",User.class).list();
    }

    @Override
    public void add_User(User user) throws RemoteException {
        Transaction t = session.getTransaction();
        try{
            t.begin();
            session.save(user);
            t.commit();
        }catch (Exception e){
            t.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public void update_User(User user) throws RemoteException {
        Transaction t = session.getTransaction();
        try {
            t.begin();
            session.merge(user);
            t.commit();
        }catch (Exception e){
            t.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public void delete_User(long id) throws RemoteException {
        User p = session.find(User.class, id);
        Transaction t = session.getTransaction();
        try {
            t.begin();
            session.remove(p);
            t.commit();
        }catch (Exception e){
            t.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public long getLastId() throws RemoteException {
       return (long) session.createQuery("SELECT MAX(id) FROM User").uniqueResult();
    }

    @Override
    public User getEmail(String s) throws RemoteException {
        return (User) session.createQuery("select u from User u where email =:email").setParameter("email", s).uniqueResult();

    }
}
