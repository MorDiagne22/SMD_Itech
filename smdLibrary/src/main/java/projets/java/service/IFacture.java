package projets.java.service;

import projets.java.model.Facture;
import projets.java.model.Produit;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface IFacture extends Remote {
    public void addFacture(Facture f) throws RemoteException;
    public void updateFacture(Facture f) throws RemoteException;
    public long getLastIdFacture() throws RemoteException;
    public List<Facture> getAllFacture() throws RemoteException;
}
