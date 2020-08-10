package projets.java.service;

import projets.java.model.Categorie;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface ICategorie extends Remote {
    public List<Categorie> getAllCategorie() throws RemoteException;
    public void add_Cat(Categorie c) throws RemoteException;
    public void update_Cat(Categorie c) throws RemoteException;
    public void delete_Cat(long id) throws RemoteException;
}
