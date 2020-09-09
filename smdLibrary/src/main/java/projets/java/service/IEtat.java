package projets.java.service;

import projets.java.model.Etat;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IEtat extends Remote {
    public Etat getEtat(String libelle) throws RemoteException;
}
