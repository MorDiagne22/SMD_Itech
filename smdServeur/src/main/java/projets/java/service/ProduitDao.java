package projets.java.service;

import org.hibernate.Session;
import org.hibernate.Transaction;
import projets.java.model.Produit;
import projets.java.utils.HibernateUtil;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class ProduitDao extends UnicastRemoteObject implements IProduit {
    private Session session;
    public ProduitDao() throws RemoteException {
        session = HibernateUtil.getSession();
    }

    @Override
    public List<Produit> getAllProduit() throws RemoteException {
        return session.createQuery("SELECT p FROM Produit p",Produit.class).list();
    }

    @Override
    public Produit getProduitByName(String lib) throws RemoteException {
        return (Produit) session.createQuery("select p from Produit p where libelle =:libelle").setParameter("libelle", lib).uniqueResult();
    }

    @Override
    public void add_Prod(Produit produit) throws RemoteException {
        Transaction t = session.getTransaction();
        try{
            t.begin();
            session.save(produit);
            t.commit();
        }catch (Exception e){
            t.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public void update_Prod(Produit produit) throws RemoteException {
        Transaction t = session.getTransaction();
        try {
            t.begin();
            session.merge(produit);
            t.commit();
        }catch (Exception e){
            t.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public void delete_Prod(long id) throws RemoteException {
        Produit p = session.find(Produit.class, id);
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
