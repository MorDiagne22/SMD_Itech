package projets.java.service;

import projets.java.model.Produit_Commande;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface IProduit_Commande extends Remote {
    public void addProduitCommande(Produit_Commande p) throws RemoteException;
    public List<Produit_Commande> getProduitCommande(Long id) throws RemoteException;
}
