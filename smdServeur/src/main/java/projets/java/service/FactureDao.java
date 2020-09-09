package projets.java.service;

import org.hibernate.Session;
import org.hibernate.Transaction;
import projets.java.model.Facture;
import projets.java.model.Produit;
import projets.java.utils.HibernateUtil;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class FactureDao extends UnicastRemoteObject implements IFacture {
    private Session session;
    public FactureDao() throws RemoteException {
        session = HibernateUtil.getSession();
    }

    @Override
    public void addFacture(Facture facture) throws RemoteException {
        Transaction t = session.getTransaction();
        try{
            t.begin();
            session.save(facture);
            t.commit();
        }catch (Exception e){
            t.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public void updateFacture(Facture facture) throws RemoteException {
        Transaction t = session.getTransaction();
        try {
            t.begin();
            session.merge(facture);
            t.commit();
        }catch (Exception e){
            t.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public long getLastIdFacture() throws RemoteException {
        Object val = session.createQuery("SELECT MAX(id) FROM Facture").uniqueResult();
        if(val == null){
            return 0;
        }else{
            return (long) session.createQuery("SELECT MAX(id) FROM Facture").uniqueResult();
        }
    }

    @Override
    public List<Facture> getAllFacture() throws RemoteException {
        return session.createQuery("SELECT p FROM Facture p",Facture.class).list();
    }
}
