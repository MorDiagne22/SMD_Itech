package projets.java.service;

import projets.java.model.Produit;
import projets.java.model.User;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface IUser extends Remote {
    public List<User> getAllUsers() throws RemoteException;
    public void add_User(User u) throws RemoteException;
    public void update_User(User u ) throws RemoteException;
    public void delete_User(long id) throws RemoteException;
    public long getLastId() throws RemoteException;
    public User getEmail(String email) throws RemoteException;

}
