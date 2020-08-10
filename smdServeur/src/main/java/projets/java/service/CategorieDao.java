package projets.java.service;

import org.hibernate.Transaction;
import projets.java.model.Categorie;
import projets.java.utils.HibernateUtil;
import org.hibernate.Session;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class CategorieDao extends UnicastRemoteObject implements ICategorie {
    private Session session;

    public CategorieDao() throws RemoteException {
        session = HibernateUtil.getSession();
    }


    @Override
    public List<Categorie> getAllCategorie() throws RemoteException {
        return session.createQuery("SELECT c FROM Categorie c",Categorie.class).list();
    }

    @Override
    public void add_Cat(Categorie categorie) throws RemoteException {
        Transaction t = session.getTransaction();
        try {
            t.begin();
            session.save(categorie);
            t.commit();
        }catch (Exception e){
            t.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public void update_Cat(Categorie categorie) throws RemoteException {
        Transaction t = session.getTransaction();
        try {
            t.begin();
            session.merge(categorie);
            t.commit();
        }catch (Exception e){
            t.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public void delete_Cat(long id) throws RemoteException {
        Categorie p = session.find(Categorie.class, id);
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
}
