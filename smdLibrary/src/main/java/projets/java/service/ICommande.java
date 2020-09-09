package projets.java.service;

import projets.java.model.Commande;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface ICommande extends Remote {

    public void addCommande(Commande c) throws RemoteException;
    public void updateCommande(Commande c) throws RemoteException;
    public long getLastIdCommande() throws RemoteException;
    public List<Commande> getAllCommande() throws RemoteException;
    public List<Commande> getCommandeByIntervalle(String debut, String fin) throws RemoteException;
}
