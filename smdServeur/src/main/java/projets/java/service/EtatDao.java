package projets.java.service;

import org.hibernate.Session;
import org.hibernate.SharedSessionContract;
import org.hibernate.Transaction;
import projets.java.model.Commande;
import projets.java.model.Etat;
import projets.java.model.Produit;
import projets.java.utils.HibernateUtil;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class EtatDao extends UnicastRemoteObject implements IEtat{

    private Session session;

    public EtatDao() throws RemoteException {
        session = HibernateUtil.getSession();
    }

    @Override
    public Etat getEtat(String s) throws RemoteException {
        return (Etat) session.createQuery("select p from Etat p where libelle =:libelle").setParameter("libelle", s).uniqueResult();
    }
}