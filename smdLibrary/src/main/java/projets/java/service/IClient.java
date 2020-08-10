package projets.java.service;

import projets.java.model.Client;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IClient extends Remote {
    public Client getClientByName(String nomcomplet) throws RemoteException;
    public void addClient(Client c) throws RemoteException;
    public void updateClient(Client c) throws RemoteException;
    public void deleteClient(long id) throws RemoteException;
}
