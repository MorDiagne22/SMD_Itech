package projets.java.service;

import org.hibernate.Session;
import projets.java.model.Marque;
import projets.java.model.Produit;
import projets.java.utils.HibernateUtil;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class MarqueDao extends UnicastRemoteObject implements IMarque {
    private Session session;

    public MarqueDao() throws RemoteException {
        session = HibernateUtil.getSession();
    }

    @Override
    public List<Marque> getAllMarque() throws RemoteException {
        return session.createQuery("SELECT p FROM Marque p",Marque.class).list();
    }
}
