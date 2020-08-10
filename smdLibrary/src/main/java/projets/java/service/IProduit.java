package projets.java.service;

import projets.java.model.Produit;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface IProduit extends Remote {
    public List<Produit> getAllProduit() throws RemoteException;
    public Produit getProduitByName(String libelle) throws RemoteException;
    public void add_Prod(Produit p) throws RemoteException;
    public void update_Prod(Produit p ) throws RemoteException;
    public void delete_Prod(long id) throws RemoteException;

}
