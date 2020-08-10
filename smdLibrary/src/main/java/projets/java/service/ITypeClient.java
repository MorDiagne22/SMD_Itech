package projets.java.service;

import projets.java.model.Client;
import projets.java.model.TypeClient;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface ITypeClient extends Remote {
    public List<TypeClient> getAllTypeClient() throws RemoteException;
}
