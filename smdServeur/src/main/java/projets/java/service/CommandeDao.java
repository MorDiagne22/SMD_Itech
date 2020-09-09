package projets.java.service;

import org.hibernate.Session;
import org.hibernate.Transaction;
import projets.java.model.Commande;
import projets.java.utils.HibernateUtil;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class CommandeDao extends UnicastRemoteObject implements ICommande {

    private Session session;

    public CommandeDao() throws RemoteException {
        session = HibernateUtil.getSession();
    }

    @Override
    public void addCommande(Commande commande) throws RemoteException {
        Transaction t = session.getTransaction();
        try{
            t.begin();
            session.save(commande);
            t.commit();
        }catch (Exception e){
            t.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public void updateCommande(Commande commande) throws RemoteException {
        Transaction t = session.getTransaction();
        try {
            t.begin();
            session.merge(commande);
            t.commit();
        }catch (Exception e){
            t.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public long getLastIdCommande() throws RemoteException {

        Object val = session.createQuery("SELECT MAX(id) FROM Commande").uniqueResult();
        if(val == null){
            return 0;
        }else{
            return (long) session.createQuery("SELECT MAX(id) FROM Commande").uniqueResult();
        }
    }

    @Override
    public List<Commande> getAllCommande() throws RemoteException {
        return session.createQuery("SELECT c FROM Commande c",Commande.class).list();

    }

    @Override
    public List<Commande> getCommandeByIntervalle(String dateDebut, String dateFin) throws RemoteException {
        return session.createQuery("SELECT c FROM Commande c where date between :debut and :fin").
                setParameter("debut", dateDebut).
                setParameter("fin", dateFin).list();
    }
}
