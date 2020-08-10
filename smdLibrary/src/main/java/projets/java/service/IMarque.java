package projets.java.service;

import projets.java.model.Marque;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface IMarque extends Remote {
    public List<Marque> getAllMarque() throws RemoteException;
}
