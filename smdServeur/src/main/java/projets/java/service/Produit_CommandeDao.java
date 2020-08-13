package projets.java.service;

import org.hibernate.Session;
import org.hibernate.Transaction;
import projets.java.model.Produit;
import projets.java.model.Produit_Commande;
import projets.java.utils.HibernateUtil;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class Produit_CommandeDao extends UnicastRemoteObject implements IProduit_Commande {

    private Session session;

    public Produit_CommandeDao() throws RemoteException {
        session = HibernateUtil.getSession();
    }

    @Override
    public void addProduitCommande(Produit_Commande produit_commande) throws RemoteException {
        Transaction t = session.getTransaction();
        try{
            t.begin();
            session.save(produit_commande);
            t.commit();
        }catch (Exception e){
            t.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public List<Produit_Commande> getProduitCommande(Long id) throws RemoteException {
        return session.createQuery("select p from Produit_Commande p where id =:id").setParameter("id", id).list();

    }
}
